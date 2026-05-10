package vendingmachine.logic;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChangeCalculator {


    public enum PaymentMethod {
        CASH,
        CREDIT_CARD,
        BANK_CARD
    }

    // ── US denominations (largest first) ─────────────────────────────
    private static final double[] DENOMINATIONS = {
        100.00, 50.00, 20.00, 10.00, 5.00, 1.00,
        0.25, 0.10, 0.05, 0.01
    };
    private static final String[] LABELS = {
        "$100", "$50", "$20", "$10", "$5", "$1",
        "25c", "10c", "5c", "1c"
    };

    public static PaymentResult processPayment(double itemPrice,
                                               double amountTendered,
                                               PaymentMethod method) {
        if (itemPrice <= 0)
            return PaymentResult.failure("Item price must be greater than zero.", method);

        switch (method) {
            case CASH:
                return processCash(itemPrice, amountTendered);
            case CREDIT_CARD:
                return processCreditCard(itemPrice);
            case BANK_CARD:
                return processBankCard(itemPrice);
            default:
                return PaymentResult.failure("Unknown payment method.", method);
        }
    }

    private static PaymentResult processCash(double price, double inserted) {
        if (inserted < price) {
            double shortfall = price - inserted;
            return PaymentResult.failure(
                String.format("Insufficient cash. Need $%.2f more.", shortfall),
                PaymentMethod.CASH);
        }

        double changeAmount = inserted - price;
        Map<String, Integer> breakdown = computeBreakdown(changeAmount);

        return PaymentResult.cashSuccess(price, inserted, changeAmount, breakdown);
    }

    private static PaymentResult processCreditCard(double price) {
        // --- Simulated authorisation (always approved for now) ---
        boolean approved = simulateCardAuthorisation("CREDIT", price);

        if (!approved) {
            return PaymentResult.failure(
                "Credit card declined. Please try another payment method.",
                PaymentMethod.CREDIT_CARD);
        }

        return PaymentResult.cardSuccess(price, PaymentMethod.CREDIT_CARD,
                "Credit card charged $" + String.format("%.2f", price) + ".");
    }

    private static PaymentResult processBankCard(double price) {
        // --- Simulated authorisation (always approved for now) ---
        boolean approved = simulateCardAuthorisation("BANK", price);

        if (!approved) {
            return PaymentResult.failure(
                "Bank card declined. Please try another payment method.",
                PaymentMethod.BANK_CARD);
        }

        return PaymentResult.cardSuccess(price, PaymentMethod.BANK_CARD,
                "Bank card (debit) charged $" + String.format("%.2f", price) + ".");
    }

    private static Map<String, Integer> computeBreakdown(double amount) {
        Map<String, Integer> breakdown = new LinkedHashMap<>();
        long remaining = Math.round(amount * 100);

        for (int i = 0; i < DENOMINATIONS.length; i++) {
            long denom = Math.round(DENOMINATIONS[i] * 100);
            if (remaining <= 0) break;
            int count = (int) (remaining / denom);
            if (count > 0) {
                breakdown.put(LABELS[i], count);
                remaining -= (long) count * denom;
            }
        }
        return breakdown;
    }

    private static boolean simulateCardAuthorisation(String cardType, double amount) {
        // Placeholder: always approve.
        // TODO: integrate real payment gateway here.
        System.out.printf("  [%s CARD] Contacting terminal... Authorised $%.2f%n",
                cardType, amount);
        return true;
    }

    public static class PaymentResult {

        private final boolean             success;
        private final String              message;
        private final PaymentMethod       method;
        private final double              amountPaid;
        private final double              amountInserted;   // cash only
        private final double              changeAmount;     // cash only
        private final Map<String, Integer> changeBreakdown; // cash only

        // ── Private constructors (use static factories below) ─────────
        private PaymentResult(boolean success, String message, PaymentMethod method,
                              double amountPaid, double amountInserted,
                              double changeAmount, Map<String, Integer> changeBreakdown) {
            this.success         = success;
            this.message         = message;
            this.method          = method;
            this.amountPaid      = amountPaid;
            this.amountInserted  = amountInserted;
            this.changeAmount    = changeAmount;
            this.changeBreakdown = changeBreakdown;
        }

        // ── Static factories ──────────────────────────────────────────
        static PaymentResult cashSuccess(double price, double inserted,
                                         double change,
                                         Map<String, Integer> breakdown) {
            return new PaymentResult(true,
                    String.format("Cash payment accepted. Change: $%.2f", change),
                    PaymentMethod.CASH, price, inserted, change, breakdown);
        }

        static PaymentResult cardSuccess(double price, PaymentMethod method,
                                          String message) {
            return new PaymentResult(true, message,
                    method, price, price, 0.0, new LinkedHashMap<>());
        }

        static PaymentResult failure(String message, PaymentMethod method) {
            return new PaymentResult(false, message,
                    method, 0.0, 0.0, 0.0, new LinkedHashMap<>());
        }

        // ── Getters ───────────────────────────────────────────────────
        public boolean             isSuccess()         { return success; }
        public String              getMessage()        { return message; }
        public PaymentMethod       getMethod()         { return method; }
        public double              getAmountPaid()     { return amountPaid; }
        public double              getAmountInserted() { return amountInserted; }
        public double              getChangeAmount()   { return changeAmount; }
        public Map<String, Integer> getChangeBreakdown() {
            return new LinkedHashMap<>(changeBreakdown);
        }

        // ── Console receipt printer ───────────────────────────────────
        /**
         * Prints a formatted receipt to stdout.
         * Call this after confirming the result is a success.
         */
        public void printReceipt() {
            System.out.println("\n  ======================================");
            System.out.println("  [OK]  PAYMENT SUCCESSFUL");
            System.out.println("  ======================================");
            System.out.printf ("  Method   : %s%n", method);
            System.out.printf ("  Charged  : $%.2f%n", amountPaid);

            if (method == PaymentMethod.CASH) {
                System.out.printf("  Inserted : $%.2f%n", amountInserted);
                if (changeAmount > 0.005) {
                    System.out.printf("  Change   : $%.2f%n", changeAmount);
                    System.out.println("  ----- Dispensing Change -----");
                    for (Map.Entry<String, Integer> entry : changeBreakdown.entrySet()) {
                        System.out.printf("    %d x %s%n",
                                entry.getValue(), entry.getKey());
                    }
                } else {
                    System.out.println("  Change   : None (exact amount)");
                }
            } else {
                System.out.println("  " + message);
            }
            System.out.println("  ======================================");
        }

        /** Short one-line summary for logging.
         * @return  */
        @Override
        public String toString() {
            return String.format("PaymentResult[%s | %s | paid=$%.2f | change=$%.2f]",
                    success ? "OK" : "FAIL", method, amountPaid, changeAmount);
        }
    }
}
