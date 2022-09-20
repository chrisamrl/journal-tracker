package ui.gui;

import model.Journal;
import model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents a Page Panel corresponding to a certain date on a journal,
// displaying the mood and list of note on that date
public class PagePanel extends JPanel implements ActionListener {
    private CardLayout parentCardLayout;

    private int day;
    private int month;
    private Journal journal;
    private JPanel noteContentPanel;


    // EFFECTS: constructs a new Page Panel with given journal, day, month, and
    //          parent's Card Layout
    public PagePanel(CardLayout parentCardLayout, Journal journal, int day, int month) {
        this.day = day;
        this.month = month;
        this.journal = journal;
        this.parentCardLayout = parentCardLayout;


        setLayout(new FlowLayout());

        this.noteContentPanel = new JPanel();
        noteContentPanel.setLayout(new BoxLayout(noteContentPanel, BoxLayout.Y_AXIS));


        setNoteContentPanel();
        setBackground(Color.black);
        add(noteContentPanel);
    }

    // MODIFIES: this
    // EFFECTS: clears note Content Panel, then
    //          reinitialize it using the current state of journal
    private void setNoteContentPanel() {
        noteContentPanel.removeAll();

        addPageSummary();
        addNoteHeader();
        addNotes();

        revalidate();
        repaint();
    }


    // MODIFIES: this
    // EFFECTS: adds page summary (containing mood, date, and Home Button)
    //          to noteContentPanel
    private void addPageSummary() {
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BorderLayout());

        JButton setMoodButton = new JButton("Mood");
        setMoodButton.setPreferredSize(new Dimension(95,95));
        setMoodButton.setActionCommand("setMood");
        setMoodButton.addActionListener(this);


        JButton homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(95,95));
        homeButton.setActionCommand("backHome");
        homeButton.addActionListener(this);


        JLabel moodAndDate = new JLabel("Mood: " + journal.getMoodOnPage(day, month)
                + ", Date: " + day + "-" + month + "-" + "2022", SwingConstants.CENTER);
        moodAndDate.setPreferredSize(new Dimension(500,95));

        summaryPanel.add(setMoodButton, BorderLayout.WEST);
        summaryPanel.add(moodAndDate, BorderLayout.CENTER);
        summaryPanel.add(homeButton, BorderLayout.EAST);

        setPageSummaryIcon(setMoodButton, homeButton);
        summaryPanel.setBackground(new Color(7, 97, 209));

        noteContentPanel.add(summaryPanel);
    }

    // MODIFIES: moodButton, homeButton
    // EFFECTS: sets the icon of the 2 buttons passed in
    private void setPageSummaryIcon(JButton moodButton, JButton homeButton) {
        homeButton.setIcon(new ImageIcon("./data/icon/homeButton.png"));
        moodButton.setIcon(new ImageIcon("./data/icon/moodButton.png"));
    }

    // MODIFIES: this
    // EFFECTS: add a row on noteContentPanel corresponding to
    //          note with given content and index
    private void addNoteRow(String noteContent, int index) {
        JPanel noteRowPanel = new JPanel();
        noteRowPanel.setLayout(new BorderLayout());
        noteRowPanel.setBackground(Color.white);


        JButton noteDeleteButton = new JButton("Delete");
        noteDeleteButton.setPreferredSize(new Dimension(95,100));
        noteDeleteButton.addActionListener(e -> {
            if (wantDeleted()) {
                journal.deleteNoteOnPage(day, month, index);
                setNoteContentPanel();
            }
        });
        noteDeleteButton.setIcon(new ImageIcon("./data/icon/deleteButton.png"));


        JTextArea noteContentArea = new JTextArea(noteContent);
        noteContentArea.setEditable(false);
        noteContentArea.setLineWrap(true);
        noteContentArea.setWrapStyleWord(true);
        JScrollPane scrollNoteContent = new JScrollPane(noteContentArea);
        scrollNoteContent.setPreferredSize(new Dimension(100,95));


        noteRowPanel.add(noteDeleteButton, BorderLayout.WEST);
        noteRowPanel.add(scrollNoteContent, BorderLayout.CENTER);

        noteContentPanel.add(noteRowPanel);

        revalidate();
        repaint();
    }


    // EFFECTS: confirms whether the user wants the note to be deleted
    private boolean wantDeleted() {
        int optionSelected = JOptionPane.showConfirmDialog(noteContentPanel,
                "Delete note?", "Confirm deletion", JOptionPane.YES_NO_OPTION);
        return optionSelected == JOptionPane.YES_OPTION;
    }


    // MODIFIES: this
    // EFFECTS: adds note header (containing add note button and
    //          a title) to noteContentPanel
    private void addNoteHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        JButton addNoteButton = new JButton("Add");
        addNoteButton.setPreferredSize(new Dimension(95,95));
        addNoteButton.setPreferredSize(addNoteButton.getPreferredSize());
        addNoteButton.setActionCommand("addNote");
        addNoteButton.addActionListener(this);
        addNoteButton.setIcon(new ImageIcon("./data/icon/addButton.png"));


        JLabel noteLabel =  new JLabel("Notes", SwingConstants.CENTER);

        header.setBackground(new Color(234, 85, 141));
        header.add(addNoteButton, BorderLayout.WEST);
        header.add(noteLabel, BorderLayout.CENTER);


        noteContentPanel.add(header);
    }

    // MODIFIES: this
    // EFFECTS: add all notes on journal to noteContentPanel
    private void addNotes() {
        int count = 0;
        ArrayList<Note> notes = journal.getNotesOnPage(day, month);
        for (Note note : notes) {
            addNoteRow(note.getContent(), count);
            count += 1;
        }
    }


    // MODIFIES: this
    // EFFECTS: processes which operation to perform based
    //          on Action triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addNote")) {
            askForNoteContent();
        } else if (e.getActionCommand().equals("setMood")) {
            askForMood();
        } else if (e.getActionCommand().equals("backHome")) {
            backHome();
        }
    }


    // MODIFIES: this
    // EFFECTS: asks user for new Note's content and add it
    //          to journal, then reset noteContentPanel
    private void askForNoteContent() {
        JTextArea newNote = new JTextArea();
        newNote.setLineWrap(true);
        newNote.setWrapStyleWord(true);

        JScrollPane noteScroll = new JScrollPane(newNote);
        noteScroll.setPreferredSize(new Dimension(500,500));

        int optionClicked = JOptionPane.showConfirmDialog(noteContentPanel, noteScroll, "Add note",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (optionClicked == JOptionPane.OK_OPTION) {
            String newNoteContent = newNote.getText();

            journal.addNoteOnPage(day, month, new Note(newNoteContent));
            setNoteContentPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user for mood, and set it
    //          on journal, then reset noteContentPanel
    private void askForMood() {
        String mood = (String) JOptionPane.showInputDialog(
                noteContentPanel, "How are you feeling today?", "Setting mood...",
                JOptionPane.PLAIN_MESSAGE);

        if ((mood != null) && (mood.length() > 0)) {
            journal.setMoodOnPage(day, month, mood);
            setNoteContentPanel();
        }

    }

    // EFFECTS: shows HomePanel and hides this panel
    private void backHome() {
        // Get MainFrame content pane.
        // Need 3 getParent() because:
        // PagePanel -> JScrollPane -> View Port -> MainFrame panel
        parentCardLayout.show(getParent().getParent().getParent(), "home");
    }
}
