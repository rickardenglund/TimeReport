import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Workday {
    public static final String EMPTY = "-";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final LocalDate date;
    private Optional<LocalTime> start;
    private Optional<LocalTime> stop = Optional.empty();
    private Optional<Duration> pauses = Optional.empty();

    public Workday(LocalDate day) {
        date = day;
        start = Optional.empty();
    }

    public String getDate() {
        return date.toString();
    }

    public String getStart() {
        return start.isPresent()? start.get().format(FORMATTER): EMPTY;
    }

    public String getStop() {
        return stop.isPresent()? stop.get().format(FORMATTER): EMPTY;
    }

    public String getPauses() {
        return pauses.isPresent()?
                String.format("%d:%02d", pauses.get().toHours() , (pauses.get().getSeconds() / 60) % 60)
                : EMPTY;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", getDate(), getStart(), getStop());
    }

    public void setStart(LocalTime start) {
        this.start = Optional.of(start);
    }

    public void setStop(LocalTime stop) {
        this.stop = Optional.of(stop);
    }

    public void setPause(Duration pause) {
        this.pauses = Optional.of(pause);
    }
}
