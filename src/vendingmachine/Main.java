package vendingmachine;

import vendingmachine.gui.TestGUI;

public class Main {
    public static void main(String[] args) {
        // Launch the graphical interface immediately
        java.awt.EventQueue.invokeLater(() -> {
            new TestGUI().setVisible(true);
        });
    }
}