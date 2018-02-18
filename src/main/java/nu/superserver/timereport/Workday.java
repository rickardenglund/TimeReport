package nu.superserver.timereport;

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

    public LocalDate getLocalDate() {
        return date;
    }

    public String getStart() {
        return start.isPresent()? start.get().format(FORMATTER): EMPTY;
    }

    public String getStop() {
        return stop.isPresent()? stop.get().format(FORMATTER): EMPTY;
    }

    public String getWorkedTime() {
        if (start.isPresent() && stop.isPresent() && pauses.isPresent()) {
            Duration time = Duration.between(start.get(), stop.get()).minus(pauses.get());
            if (!time.isNegative()) {
                return formatDuration(time);
            }
        }
        return EMPTY;
    }

    public String getPauses() {
        return pauses.isPresent()?
                formatDuration(pauses.get())
                : EMPTY;
    }

    public static String formatDuration(Duration duration) {
        return String.format("%d:%02d", duration.toHours() , (duration.getSeconds() / 60) % 60);
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

    public Optional<LocalTime> getStartLocalTime() {
        return start;
    }

    public Optional<LocalTime> getStopLocalTime() {
        return stop;
    }

    public Optional<Duration> getPauseDuration() {
        return pauses;
    }
}
