import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeReport extends Application {


    public static final String FILE_NAME = "days.json";

    public static void main(String... args) {
        launch(args);
    }


    @Override
    public void start(Stage stage){

        List<Workday> readWorkdays;
        try (FileReader reader = new FileReader(FILE_NAME)) {
             readWorkdays = new GsonBuilder().create().fromJson(reader,
                    new TypeToken<List<Workday>>() {
                    }.getType());
        } catch (IOException e) {
            System.out.println("Failed to read file: " + FILE_NAME);
            e.printStackTrace();
            readWorkdays = createDays();
        }


        final List<Workday> workdays = readWorkdays;
        TableView table = MonthTable.create(workdays);


        Scene scene = new Scene(table);

        stage.setScene(scene);
        stage.setTitle("Title");
        stage.setOnCloseRequest(e -> {
            System.out.println("Shutting down");
            try (PrintWriter writer = new PrintWriter(FILE_NAME, "UTF-8")){
                new GsonBuilder().setPrettyPrinting().create().toJson(workdays, writer);
            } catch (FileNotFoundException | UnsupportedEncodingException e1) {
                System.out.println("Failed to Save data: " + workdays);
                e1.printStackTrace();
            }
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
