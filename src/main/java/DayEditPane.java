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
        startPicker.setMinuteStep(30);
        stopPicker.setMinuteStep(30);

        Slider pauseSlider = new Slider(0, 4, 0.5);
        pauseSlider.setMajorTickUnit(0.5);
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

    private static long hoursToMinutes(double hours) {
        return Math.round(hours * 60);
    }
}
