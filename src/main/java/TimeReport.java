import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TimeReport extends Application {


    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        TableView table = MonthTable.create();


        Scene scene = new Scene(table);

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.show();
    }


}
