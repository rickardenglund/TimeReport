package nu.superserver.timereport;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.Month;

public class TimeReport extends Application {
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        MonthTable table = MonthTable.create(2018, Month.FEBRUARY);


        Scene scene = new Scene(table);

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.setOnCloseRequest(e -> {
            System.out.println("Shutting down");
            table.close();

        });
        stage.show();
    }


}
