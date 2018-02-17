import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalTimePicker;

import java.time.Duration;
import java.time.LocalDate;

public class DayEditPane {
    private Workday day = new Workday(LocalDate.now());

    public DayEditPane(Workday day) {
        this.day = day;
    }

    public static void update(Workday day) {
        Pane pane = new VBox();
        pane.getChildren().add(new Label(day.getDate()));

        HBox hBox = new HBox();
        LocalTimePicker startPicker = new LocalTimePicker();
        LocalTimePicker stopPicker = new LocalTimePicker();

        if (day.getStartLocalTime().isPresent()) startPicker.setLocalTime(day.getStartLocalTime().get());
        startPicker.setMinuteStep(30);

        if (day.getStopLocalTime().isPresent()) stopPicker.setLocalTime(day.getStopLocalTime().get());
        stopPicker.setMinuteStep(30);

        Slider pauseSlider = new Slider(0, 4, 0.5);
        if (day.getPauseDuration().isPresent()) pauseSlider.setValue(day.getPauseDuration().get().toMinutes()/60d);
        pauseSlider.setMajorTickUnit(0.5);
        pauseSlider.valueProperty().addListener((obs, old, newval) -> pauseSlider.setValue(roundToHalf(newval.doubleValue())) );
        pauseSlider.setShowTickLabels(true);
        pauseSlider.setSnapToTicks(true);

        hBox.getChildren().addAll(startPicker, pauseSlider, stopPicker);
        pane.getChildren().add(hBox);

        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setTitle(day.getDate());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        day.setStart(startPicker.getLocalTime());
        day.setStop(stopPicker.getLocalTime());
        day.setPause(Duration.ofMinutes(hoursToMinutes(pauseSlider.getValue())));
    }

    private static double roundToHalf(double value) {
        return Math.round(value*2)/2.0;
    }

    private static long hoursToMinutes(double hours) {
        return Math.round(hours * 60);
    }
}
