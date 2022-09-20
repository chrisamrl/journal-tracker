// I learned to make this application
// Mostly through the official oracle's Swing docs
// https://docs.oracle.com/javase/tutorial/uiswing/index.html


package ui.gui;

import javax.swing.UIManager;

// Journal App GUI
public class JournalAppGUI {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Do nothing, just use system's look and feel
        }
        new MainFrame();
    }
}
