package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MonthlyPagesTest {
    MonthlyPages testMonthlyPages1;
    MonthlyPages testMonthlyPages2;
    MonthlyPages testMonthlyPages3;


    @BeforeEach
    void runBefore() {
        testMonthlyPages1 = new MonthlyPages(3, 31);
        testMonthlyPages2 = new MonthlyPages(2, 28);
        testMonthlyPages3 = new MonthlyPages(11, 30);
    }

    @Test
    void testConstructor() {
        // Also tests getPages and getMonthNumber
        assertEquals(3, testMonthlyPages1.getMonthNumber());
        assertEquals(2, testMonthlyPages2.getMonthNumber());
        assertEquals(11, testMonthlyPages3.getMonthNumber());


        assertEquals(31, testMonthlyPages1.getPages().size());
        assertEquals(28, testMonthlyPages2.getPages().size());
        assertEquals(30, testMonthlyPages3.getPages().size());
    }

    @Test
    void testGetPage() {
        // Also indirectly test the constructor
        assertEquals(1, testMonthlyPages1.getPage(1).getDay());
        assertEquals(3, testMonthlyPages1.getPage(1).getMonth());

        assertEquals(28, testMonthlyPages2.getPage(28).getDay());
        assertEquals(2, testMonthlyPages2.getPage(28).getMonth());


        assertEquals(30, testMonthlyPages3.getPage(30).getDay());
        assertEquals(11, testMonthlyPages3.getPage(30).getMonth());
    }

}
