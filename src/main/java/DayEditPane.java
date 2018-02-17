import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalTimePicker;

import java.time.Duration;
import java.time.LocalTime;

public class DayEditPane extends VBox{

    private final LocalTimePicker startPicker;
    private final LocalTimePicker stopPicker;
    private final Slider pauseSlider;

    public static void update(Workday day) {
        DayEditPane pane = new DayEditPane(day);

        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setTitle(day.getDate());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        day.setStart(pane.startPicker.getLocalTime());
        day.setStop(pane.stopPicker.getLocalTime());
        day.setPause(Duration.ofMinutes(hoursToMinutes(pane.pauseSlider.getValue())));
    }

    private DayEditPane(Workday day) {
        getChildren().add(new Label(day.getDate()));

        HBox hBox = new HBox();
        startPicker = new LocalTimePicker(LocalTime.of(7,30));
        stopPicker = new LocalTimePicker(LocalTime.of(16, 0));

        if (day.getStartLocalTime().isPresent()) startPicker.setLocalTime(day.getStartLocalTime().get());
        startPicker.setMinuteStep(30);

        if (day.getStopLocalTime().isPresent()) stopPicker.setLocalTime(day.getStopLocalTime().get());
        stopPicker.setMinuteStep(30);

        pauseSlider = new Slider(0, 3, 0.5);
        if (day.getPauseDuration().isPresent()) pauseSlider.setValue(day.getPauseDuration().get().toMinutes()/60d);
        pauseSlider.setMajorTickUnit(0.5);
        pauseSlider.valueProperty().addListener((obs, old, newval) -> pauseSlider.setValue(roundToHalf(newval.doubleValue())) );
        pauseSlider.setShowTickLabels(true);
        pauseSlider.setSnapToTicks(true);

        hBox.getChildren().addAll(startPicker, pauseSlider, stopPicker);
        getChildren().add(hBox);
    }

    private static double roundToHalf(double value) {
        return Math.round(value*2)/2.0;
    }

    private static long hoursToMinutes(double hours) {
        return Math.round(hours * 60);
    }
}
