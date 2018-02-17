import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeReport extends Application {


    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        TableView table = MonthTable.create(createDays());


        Scene scene = new Scene(table);

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.setOnCloseRequest(e -> {
            System.out.println("Shutting down");
        });
        stage.show();
    }

    private static List<Workday> createDays() {
        LocalDate today = LocalDate.now();
        List<Workday> days = new ArrayList<>();
        for (int i = 1; i <= today.lengthOfMonth(); i++) {
            LocalDate day = LocalDate.now().withDayOfMonth(i);
            days.add(new Workday(day));
        }
        return days;
    }
}
