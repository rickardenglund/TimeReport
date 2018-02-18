package nu.superserver.timereport.db;

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
}
