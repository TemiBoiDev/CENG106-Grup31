#Multi-Purpose Vending Machine (Group 31)

## Project Overview
This is the demo version for the CENG106 course's project that works on the Console. It features a 3-layer architecture: User and Admin interfaces, business logic, and file-based data management.

## How to Run and First-Time Setup
1. Open the project in a Java IDE (NetBeans is recommended).
2. Run the Main.java file.
3. Important: Since the text files are auto-generated, the system will start with a First-Time Setup screen. It will ask you to create a secure Admin password.
4. Once logged into the Admin menu, use the Add New Product feature to stock the machine, as the initial inventory will be empty.
5. The system will automatically create and update products.txt, vault.txt, history.txt, and admin.txt in your project directory.

## Developer Notes and Error Handling
We built custom safety nets into the code so the console does not crash during testing
* Invalid Inputs: If you type a letter when asked for a number (like price or stock), the system catches the NumberFormatException and asks you to try again.
* Missing Products: If you search for a slot that does not exist, our custom ProductNotFoundException prevents a crash and safely returns you to the menu.
* Out of Stock: Buying an empty item triggers our custom OutOfStockException to block the sale.

* ##TODO:
* Will add GUI
* The admin menu will be extended to create the extended subclassses
* 
