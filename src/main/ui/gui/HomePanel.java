package ui.gui;

import model.Journal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


// Represents the home panel in the Journal Application,
// displaying a date picker and an Image
public class HomePanel extends JPanel implements ItemListener, ActionListener {
    private JComboBox<String> monthBox;
    private JComboBox<String> dayBox;
    private CardLayout parentCardLayout;

    private Journal journal;
    private String[] months = new String[] {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};


    // EFFECTS: constructs a new Home Panel with journal and
    //          parent's Card Layout given
    public HomePanel(CardLayout parentCardLayout, Journal journal) {
        setLayout(new GridLayout(1,2,0,0));
        setBackground(Color.black);

        this.parentCardLayout = parentCardLayout;
        this.journal = journal;

        JPanel datePanel = createDatePanel();
        add(datePanel);

        JLabel photoLabel = createPhotoLabel();
        add(photoLabel);
    }


    // EFFECTS: returns a JLabel containing a Photo chosen
    private JLabel createPhotoLabel() {
        JLabel photo = new JLabel("");
        photo.setIcon(new ImageIcon("./data/journalPhoto.gif"));

        return photo;
    }

    // MODIFIES: this
    // EFFECTS: returns a JPanel containing a Date Picker
    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBackground(Color.black);

        monthBox = new JComboBox<>(months);
        dayBox = new JComboBox<>();
        setDayComboBox("January");
        JButton submitDate = new JButton("Submit");
        submitDate.setActionCommand("dateButton");

        monthBox.addItemListener(this);
        submitDate.addActionListener(this);

        JLabel journalTitle = new JLabel("Journal");
        journalTitle.setForeground(Color.WHITE);
        journalTitle.setFont(new Font("Serif", Font.BOLD, 30));


        addDatePanelComponent(datePanel, 200, journalTitle);
        addDatePanelComponent(datePanel,50,monthBox);
        addDatePanelComponent(datePanel,10,dayBox);
        addDatePanelComponent(datePanel,15,submitDate);



        // Wrapper panel for display formatting
        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.setBackground(Color.black);
        flowPanel.add(datePanel);

        return flowPanel;
    }

    // MODIFIES: datePanel
    // EFFECTS: add component to datePanel and add vertical space given
    private void addDatePanelComponent(JPanel datePanel, int verticalGap, Component component) {
        datePanel.add(Box.createRigidArea(new Dimension(0, verticalGap)));
        datePanel.add(component);
    }


    // MODIFIES: getParent()
    // EFFECTS: replaces current Page Panel with a new one
    //          corresponding to day, month selected on
    //          Date Picker
    private void replaceOldPagePanel() {
        for (Component c : getParent().getComponents()) {
            if (c != this) {
                getParent().remove(c);
            }
        }

        int day = Integer.parseInt((String) dayBox.getSelectedItem());
        int month = enumerateMonth((String) monthBox.getSelectedItem());

        PagePanel newPagePanel = new PagePanel(parentCardLayout, journal, day, month);
        JScrollPane newPageScroll = new JScrollPane(newPagePanel);


        getParent().add(newPageScroll, "page");
        parentCardLayout.show(getParent(), "page");
    }


    // MODIFIES: this
    // EFFECTS: sets the Day Combo Box corresponding to
    //          month chosen in Month Combo Box
    private void setDayComboBox(String month) {
        dayBox.removeAllItems();
        int numOfDays = numOfDays(month);
        for (int i = 1; i < numOfDays + 1; i++) {
            dayBox.addItem(Integer.toString(i));
        }
    }


    // EFFECTS: returns the number of days in the given month
    private int numOfDays(String month) {
        if (month.equals("February")) {
            return 28;
        } else if (month.equals("April") || month.equals("June")
                || month.equals("September") || month.equals("November")) {
            return 30;
        } else {
            return 31;
        }
    }


    // EFFECTS: returns the number representation of the
    //          given month (e.g. January -> 1)
    private int enumerateMonth(String month) {
        int monthNumber = 1;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(month)) {
                monthNumber = i + 1;
                break;
            }
        }
        return monthNumber;
    }


    // MODIFIES: getParent()
    // EFFECTS: processes which operation to perform based
    //          on Action triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("dateButton")) {
            replaceOldPagePanel();
            parentCardLayout.show(getParent(), "page");
        }
    }


    // MODIFIES: this
    // EFFECTS: processes which operation to perform based
    //          on Action triggered
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String month = (String) e.getItem();
            setDayComboBox(month);
        }
    }
}
