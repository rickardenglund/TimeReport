import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

public class MonthTable extends TableView {

    private MonthTable() {

    }

    public static MonthTable create(List<Workday> workdays) {
        MonthTable table = new MonthTable();
        table.addColumns();

        ObservableList<Workday> days = FXCollections.observableArrayList(workdays);
        table.setItems(days);

        table.setRowFactory(tv -> {
            TableRow<Workday> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (!row.isEmpty()) {
                    Workday day = row.getItem();
                    DayEditPane.update(day);
                    days.set(row.getIndex(), day);
                }
            });
            return row;
        });

        return table;
    }

    private void addColumns() {
        TableColumn dayCol = new TableColumn("Dag");
        dayCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("date"));
        dayCol.setCellFactory(columne -> {
            return new TableCell<Workday, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && !isWeekday(getTableView().getItems().get(getIndex()).getLocalDate().getDayOfWeek())) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                    setText(item);
                }
            };
        });

        TableColumn startCol = new TableColumn("In");
        startCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("start"));
        TableColumn pausCol = new TableColumn("Pauser");
        pausCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("pauses"));
        TableColumn stopCol = new TableColumn("Ut");
        stopCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("stop"));
        TableColumn totalCol = new TableColumn("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("workedTime"));
        getColumns().addAll(dayCol, startCol, pausCol, stopCol, totalCol);
    }

    public static boolean isWeekday(DayOfWeek day) {
        List<DayOfWeek> weekend = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return !weekend.contains(day);
    }
}
