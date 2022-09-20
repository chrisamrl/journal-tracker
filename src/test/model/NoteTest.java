package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    Note testNote;

    @BeforeEach
    void runBefore() {
        testNote = new Note("content test");
    }

    @Test
    void testConstructor() {
        assertEquals("content test", testNote.getContent());
    }

    @Test
    void testSetContentOnce() {
        testNote.setContent("This is a test!");
        assertEquals("This is a test!", testNote.getContent());
    }

    @Test
    void testSetContentMultipleTimes() {
        testNote.setContent("This is a test!  1");
        assertEquals("This is a test!  1", testNote.getContent());

        testNote.setContent("This is a test!  2");
        assertEquals("This is a test!  2", testNote.getContent());

        testNote.setContent("This is a test!  0");
        assertEquals("This is a test!  0", testNote.getContent());
    }
}