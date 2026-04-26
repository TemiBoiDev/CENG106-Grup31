package vendingmachine.data;

import vendingmachine.products.*;

import java.io.*;
import java.util.*;

public class FileManager {
    private final String PRODUCT_FILE = "products.txt";
    private final String VAULT_FILE = "vault.txt";
    private final String HISTORY_FILE = "history.txt";
    private final String ADMIN_FILE = "admin.txt";

       //Function to download inventory from file
    public Map<String, AbstractProduct> loadInventory() {
        Map<String, AbstractProduct> inventory = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
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
                        inventory.put(slot, new ProductBeverage(id, slot, name, price, stock, Boolean.parseBoolean(extra)));
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
            System.out.println("VERİ OKUNAMADI");
        }
        return inventory;
    }

    //Function to save the inventory to the file
    public void saveInventory(Map<String, AbstractProduct> inventory) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
            for (AbstractProduct p : inventory.values()) {
                String type = "TECH";
                String extra = "";

                if (p instanceof ProductFood) {
                    type = "FOOD";
                    extra = String.valueOf(((ProductFood) p).getCalorieCount());
                } else if (p instanceof ProductBeverage) {
                    type = "BEVERAGES";
                    extra = String.valueOf(((ProductBeverage) p).isCooled());
                } else if (p instanceof ProductPet) {
                    type = "PET";
                    extra = ((ProductPet) p).getPetType();
                } else if (p instanceof ProductTechnology) {
                    type = "TECHNOLOGICAL";
                    extra = ((ProductTechnology) p).getDeviceType();
                }

                pw.println(type + "," + p.getId() + "," + p.getSlotId() + "," + 
                           p.getName() + "," + p.getPrice() + "," + p.getStockQuantity() + "," + extra);
            }
        } catch (IOException e) {
            System.out.println("ÜRÜN KAYDEDİLEMEDİ!!!");
        }
    }

    // Vault balance function
    public double getVaultBalance() {
        try (Scanner sc = new Scanner(new File(VAULT_FILE))) {
            if (sc.hasNextDouble()) return sc.nextDouble();
        } catch (FileNotFoundException e) {
            return 0;
        }
        return 0;
    }

    // Update vault
    public void updateVault(double amount) {
        double current = getVaultBalance();
        try (PrintWriter pw = new PrintWriter(new FileWriter(VAULT_FILE))) {
            pw.print(current + amount);
        } catch (IOException e) {
            System.err.println("Kasa hatasi!");
        }
    }

    //fFunction to see Sale history
    public void logSale(String name, double price) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
            pw.println(date + " - Satis: " + name + " - Tutar: $" + price);
        } catch (IOException e) {
            System.err.println("KAYIT YÜKLEMEDE HATA OLUSTU");
        }
    }
    
    //Function to check Admin Password File
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
        
    //Function to check Admin Password
    public boolean checkAdminPassword(String pass) {
        try (Scanner sc = new Scanner(new File(ADMIN_FILE))) {
            if (sc.hasNext()) return sc.next().equals(pass);
        } catch (FileNotFoundException e) {
            System.err.println("Sifre dosyasi yok!");
        }
        return false;
    }
}
