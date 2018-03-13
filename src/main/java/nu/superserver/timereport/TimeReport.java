package nu.superserver.timereport;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeReport extends Application {
    public static Logger LOGGER = LogManager.getLogger(TimeReport.class);

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MonthScene scene = MonthScene.createMonthScene();

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.setOnCloseRequest(e -> {
            LOGGER.info("Shutting down");
            scene.close();
        });
        stage.show();
    }


}
