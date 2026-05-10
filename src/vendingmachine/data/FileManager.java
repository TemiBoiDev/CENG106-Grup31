package vendingmachine.data;

import vendingmachine.products.*;

import java.io.*;
import java.util.*;

public class FileManager {
    private final String PRODUCT_FILE = "products.txt";
    private final String VAULT_FILE = "vault.txt";
    private final String HISTORY_FILE = "history.txt";
    private final String ADMIN_FILE = "admin.txt";

    // Satış işlemi sonrası tüm dosyaları tek seferde güncelleme
    public void saveTransaction(Map<String, AbstractProduct> inventory, String itemName, double price) {
        logSale(itemName, price);
        saveInventory(inventory);
        updateVault(price);
    }
    
       // Envanteri dosyadan yükleme fonksiyonu*
    public Map<String, AbstractProduct> loadInventory() {
        Map<String, AbstractProduct> inventory = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split("\\|");
                String type = p[0];
                int id = Integer.parseInt(p[1]);
                String slot = p[2];
                String name = p[3];
                double price = Double.parseDouble(p[4]);
                int stock = Integer.parseInt(p[5]);
                String extra = p[6];

                switch (type) {
                    case "FOOD":
                        inventory.put(slot, new ProductFood(id, slot, name, price, stock, Integer.parseInt(extra)));
                        break;
                    case "BEVERAGES":
                        String[] bevParts = extra.split(",");
                        boolean isCooled = Boolean.parseBoolean(bevParts[0]);
                        int volumeMl = (bevParts.length > 1) ? Integer.parseInt(bevParts[1]) : 500;
                        inventory.put(slot, new ProductBeverage(id, slot, name, price, stock, isCooled, volumeMl));
                        break;
                    case "TECHNOLOGICAL":
                        inventory.put(slot, new ProductTechnology(id, slot, name, price, stock, extra));
                        break;
                    case "PET":
                        inventory.put(slot, new ProductPet(id, slot, name, price, stock, extra));
                        break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not read inventory file.");
        }
        return inventory;
    }

    // Envanteri dosyaya kaydetme fonksiyonu
    public void saveInventory(Map<String, AbstractProduct> inventory) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
            for (AbstractProduct p : inventory.values()) {
                String type = "TECHNOLOGICAL";
                String extra = "";

                if (p instanceof ProductFood) {
                    type = "FOOD";
                    extra = String.valueOf(((ProductFood) p).getCalorieCount());
                } else if (p instanceof ProductBeverage) {
                    type = "BEVERAGES";
                    ProductBeverage bev = (ProductBeverage) p;
                    extra = bev.isCooled() + "," + bev.getVolumeMl();
                } else if (p instanceof ProductPet) {
                    type = "PET";
                    extra = ((ProductPet) p).getPetType();
                } else if (p instanceof ProductTechnology) {
                    type = "TECHNOLOGICAL";
                    extra = ((ProductTechnology) p).getDeviceType();
                }

                pw.println(type + "|" + p.getId() + "|" + p.getSlotId() + "|" + 
                           p.getName() + "|" + p.getPrice() + "|" + p.getStockQuantity() + "|" + extra);
            }
        } catch (IOException e) {
            System.out.println("Could not save inventory!");
        }
    }

    // Kasa bakiyesi fonksiyonu
    public double getVaultBalance() {
        try (Scanner sc = new Scanner(new File(VAULT_FILE))) {
            if (sc.hasNextDouble()) return sc.nextDouble();
        } catch (FileNotFoundException e) {
            return 0;
        }
        return 0;
    }

    // Kasa guncelleme 
    public void updateVault(double amount) {
        double current = getVaultBalance();
        try (PrintWriter pw = new PrintWriter(new FileWriter(VAULT_FILE))) {
            pw.print(current + amount);
        } catch (IOException e) {
            System.err.println("Vault update error!");
        }
    }

    // Satis gecmisi fonksiyonu
    public void logSale(String name, double price) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
            pw.println(date + "Sale: " + name + " - Amount: $" + price);
        } catch (IOException e) {
            System.err.println("Could not write sale history.");
        }
    }
    
    //YÖNETİCİ sifre dosyası kontrol fonksiyonu
    public boolean hasAdminFile(){
        return new File(ADMIN_FILE).exists();
    }
    public void setAdminPassword(String newPass){
        try (PrintWriter pw = new PrintWriter(new FileWriter(ADMIN_FILE))){
            pw.print(newPass);
        } catch(IOException e){
            System.err.println("Could not save password!");
        }
    }
        
    // YÖNETİCİ sifre kontrol fonksiyonu
    public boolean checkAdminPassword(String pass) {
        try (Scanner sc = new Scanner(new File(ADMIN_FILE))) {
            if (sc.hasNext()) return sc.next().equals(pass);
        } catch (FileNotFoundException e) {
            System.err.println("Admin password file not found!");
        }
        return false;
    }
}