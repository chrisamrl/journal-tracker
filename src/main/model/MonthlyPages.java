package model;

import java.util.ArrayList;


// Represents a collection of pages in a month, having a month number and a collection of pages
// with number of pages equals to the number of day in that month
public class MonthlyPages {
    private int monthNumber;
    private ArrayList<Page> pages;

    // REQUIRES: 1 <= month <= 12, 1 <= day <= number of days in the given month
    // EFFECTS: constructs a new Monthly Pages which has <numberOfDays> pages and a month number
    protected MonthlyPages(int monthNumber, int numberOfDays) {
        pages = new ArrayList<Page>();
        this.monthNumber = monthNumber;

        for (int dayNumber = 1; dayNumber <= numberOfDays; dayNumber++) {
            pages.add(new Page(dayNumber, monthNumber));
        }
    }

    // REQUIRES: 1 <= day <= pages.size(), i.e. number of days in this month
    // EFFECTS: returns page on the given date
    protected Page getPage(int day) {
        return pages.get(day - 1);
    }

    // EFFECTS: returns this month's number
    protected int getMonthNumber() {
        return monthNumber;
    }

    // EFFECTS: returns this month's list of pages
    public ArrayList<Page> getPages() {
        return pages;
    }
}
