package nu.superserver.timereport;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalTime;

public class TimePicker extends Pane {
    private final Slider slider;
    private int hours;
    private int minutes;

    public void setLocalTime(LocalTime localTime) {
        slider.setValue(timeToDouble(localTime));
    }

    private static double timeToDouble(LocalTime localTime) {
        return localTime.getHour() + localTime.getMinute()/60.0;
    }

    public LocalTime getLocalTime() {
        return LocalTime.of(hours, minutes);
    }

    public TimePicker(LocalTime defaultTime) {
        VBox vBox = new VBox();
        getChildren().add(vBox);

        Label timeLabel = new Label(getLocalTime().toString());
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.setFont(Font.font(30));
        slider = new Slider(0, 23, 0);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener((obs, old, newVal) -> {
            double value = roundToHalf(newVal.floatValue());
            hours = Math.round((float)Math.floor(value));
            minutes = (int)Math.round((value - hours) * 60) % 60;
            timeLabel.setText(getLocalTime().toString());
            slider.setValue(value);
        });
        slider.setValue(timeToDouble(defaultTime));
        vBox.getChildren().addAll(timeLabel, slider);
    }

    public static double roundToHalf(double value) {
        return Math.round(value*2)/2.0;
    }
}
