package nu.superserver.timereport;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimeReport extends Application {
    private MonthTable currentTable;

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        Scene scene = MonthScene.createMonthScene();

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.setOnCloseRequest(e -> {
            System.out.println("Shutting down");
            currentTable.close();
        });
        stage.show();
    }


}
