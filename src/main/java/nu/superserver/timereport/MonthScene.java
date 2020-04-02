package nu.superserver.timereport;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MonthScene extends Scene{

    private static VBox vbox;
    private MonthTable currentTable;
    private Stage stage;

    private MonthScene(VBox vBox) {
        super(vBox);
    }

    public void close() {
        currentTable.close();
    }

    public static MonthScene createMonthScene(Stage stage) {
        LocalDate now = LocalDate.now();
        MonthTable table = MonthTable.create(new Month(now.getYear(), now.getMonth()));
        vbox = new VBox(table);
        MonthScene scene = new MonthScene(vbox);
        scene.currentTable = table;

        HBox hbox = new HBox();
        Button previousBtn = new Button("Previous");
        previousBtn.setOnMouseClicked(event -> {
            scene.previousMonth(event);
            stage.setTitle(scene.currentTable.getMonth().toString());
        });
        hbox.getChildren().add(previousBtn);

        Button nextBtn = new Button("Next");
        nextBtn.setOnMouseClicked(e -> {
            scene.nextMonth(e);
            stage.setTitle(scene.currentTable.getMonth().toString());
        });
        hbox.getChildren().add(nextBtn);

        vbox.getChildren().add(hbox);
        stage.setTitle(scene.currentTable.getMonth().toString());
        return scene;
    }

    private void nextMonth(Event event) {
        setMonth(currentTable.getMonth().next());
    }

    private void previousMonth(Event event) {
        setMonth(currentTable.getMonth().previous());
    }

    private void setMonth(Month month) {
        currentTable.close();
        currentTable = MonthTable.create(month);
        vbox.getChildren().set(0, currentTable);
    }

}
