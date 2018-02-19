package nu.superserver.timereport;

import java.time.LocalDate;

public class Month {
    private final int year;
    private final java.time.Month month;

    public Month(int year, java.time.Month month) {
        this.year = year;
        this.month = month;
    }

    public java.time.Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Month next() {
        LocalDate newMonth = LocalDate.of(year, month,2).plusMonths(1);
        return new Month(newMonth.getYear(), newMonth.getMonth());
    }

    public Month previous() {
        LocalDate newMonth = LocalDate.of(year, month,1).plusMonths(-1);
        return new Month(newMonth.getYear(), newMonth.getMonth());
    }
}
