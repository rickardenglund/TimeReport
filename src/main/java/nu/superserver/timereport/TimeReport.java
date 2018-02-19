package nu.superserver.timereport;

import javafx.application.Application;
import javafx.stage.Stage;

public class TimeReport extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){
        MonthScene scene = MonthScene.createMonthScene();

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.setOnCloseRequest(e -> {
            System.out.println("Shutting down");
            scene.close();
        });
        stage.show();
    }


}
