package ui.gui;

import model.Event;
import model.EventLog;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


// Represents the main frame where journal application is run;
// this frame contains the Home Panel and Page Panel and a journal
public class MainFrame extends JFrame implements ActionListener {
    private JMenuBar menuBar;

    private Journal journal;
    private static final String JSON_STORAGE = "./data/journal.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: constructs a frame of the journal application where
    //          a new empty Journal is initialized
    public MainFrame() {
        super("Journal App");
        init();

        setMainPanel();


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event + "\n");
                }
                System.exit(0);
            }
        });
        setSize(1200,800);
        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: clears this frame, then initializes this frame's content pane
    //          according to current state of Journal; This frame will show
    //          Home Panel first by default
    private void setMainPanel() {
        // Remove all component first (including Menu bar)
        this.getContentPane().removeAll();
        menuBar = null;
        setJMenuBar(null);

        // Initialize this frame's content pane

        // Set this frame's pane to CardLayout
        CardLayout mainFrameLayout = new CardLayout();
        this.getContentPane().setLayout(mainFrameLayout);

        HomePanel homePanel = new HomePanel(mainFrameLayout, journal);
        PagePanel pagePanel = new PagePanel(mainFrameLayout, journal, 1, 1);
        JScrollPane pageScroll = new JScrollPane(pagePanel);

        add(homePanel, "home");
        add(pageScroll, "page");

        addMenuBar();


        mainFrameLayout.show(this.getContentPane(), "home");
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: initializes journal
    private void init() {
        journal = new Journal();
        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
    }

    // MODIFIES: this
    // EFFECTS: adds Menu Bar with the option to save and load
    //          file to this frame
    private void addMenuBar() {
        menuBar = new JMenuBar();

        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");

        saveMenu.setActionCommand("saveFile");
        loadMenu.setActionCommand("loadFile");

        saveMenu.addActionListener(this);
        loadMenu.addActionListener(this);

        fileMenu.add(saveMenu);
        fileMenu.add(loadMenu);

        menuBar.add(fileMenu);
    }

    // MODIFIES: this
    // EFFECTS: processes which operation to perform based
    //          on Action triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("saveFile")) {
            saveJournal();
        } else if (e.getActionCommand().equals("loadFile")) {
            loadJournal();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves journal to file at JSON_STORAGE
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            showConfirmationDialog("Journal saved to " + JSON_STORAGE);
        } catch (FileNotFoundException e) {
            showConfirmationDialog("Unable to write journal data to file: " + JSON_STORAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file at JSON_STORAGE
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            setMainPanel();
            showConfirmationDialog("Loaded journal from: " + JSON_STORAGE);
        } catch (IOException e) {
            showConfirmationDialog("Unable to read journal data from file: " + JSON_STORAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays confirmation dialog with given message
    private void showConfirmationDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
