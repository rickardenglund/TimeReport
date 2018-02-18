package nu.superserver.timereport;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.Month;

public class MonthScene {

    private MonthTable currentTable;

    private MonthScene() {

    }

    public Scene create() {

    }

    private Scene createMonthScene() {
        currentTable = MonthTable.create(2018, Month.FEBRUARY);
        VBox vbox = new VBox(currentTable);

        HBox hbox = new HBox();
        Button btn = new Button("Next");
        btn.setOnMouseClicked(e -> System.out.println("Click"));
        hbox.getChildren().add(btn);

        vbox.getChildren().add(hbox);
        return new Scene(vbox);
    }
}
