package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PageTest {
    Page testPage;
    Note testNote1;
    Note testNote2;
    Note testNote3;

    @BeforeEach
    void runBefore() {
        testPage = new Page(1, 2);
        testNote1 = new Note("just go");
        testNote2 = new Note("also a content");
        testNote3 = new Note("Hello there");
    }

    @Test
    void testConstructor() {
        assertEquals(1, testPage.getDay());
        assertEquals(2, testPage.getMonth());
        assertEquals(0, testPage.getNotes().size());
        assertEquals("", testPage.getMood());
    }

    @Test
    void testSetMood() {
        testPage.setMood("HAPPY");
        assertEquals("HAPPY", testPage.getMood());

        testPage.setMood("SAD");
        assertEquals("SAD", testPage.getMood());
    }

    @Test
    void testAddNoteOnce() {
        testPage.addNote(testNote1);
        assertTrue(testPage.getNotes().contains(testNote1));
        assertEquals(1, testPage.getNotes().size());
    }


    @Test
    void testAddNoteMultipleTimes() {
        // Also tests getNote()
        testPage.addNote(testNote1);
        testPage.addNote(testNote2);
        testPage.addNote(testNote3);


        assertTrue(testPage.getNotes().contains(testNote1));
        assertTrue(testPage.getNotes().contains(testNote2));
        assertTrue(testPage.getNotes().contains(testNote3));


        assertEquals(3, testPage.getNotes().size());
    }



}
