package nu.superserver.timereport.db;

public class Month {
    private final long year;
    private final java.time.Month month;

    public Month(long year, java.time.Month month) {
        this.year = year;
        this.month = month;
    }

    public java.time.Month getMonth() {
        return month;
    }

    public long getYear() {
        return year;
    }
}
