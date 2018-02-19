package nu.superserver.timereport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import nu.superserver.timereport.db.DB;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

public class MonthTable extends TableView {

    private static nu.superserver.timereport.Month currentMonth;
    private ObservableList<Workday> days;

    private MonthTable(List<Workday> workdays) {
        days = FXCollections.observableArrayList(workdays);
    }

    public static MonthTable create(Month month) {
        currentMonth = month;
        List<Workday> workdays = DB.get(currentMonth);

        MonthTable table = new MonthTable(workdays);
        table.addColumns();

        table.setItems(table.days);

        table.setRowFactory(tv -> {
            TableRow<Workday> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (!row.isEmpty()) {
                    Workday day = row.getItem();
                    DayEditPane.update(day);
                    table.days.set(row.getIndex(), day);
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

    public void close() {
        DB.save(currentMonth, days);
    }

    public Month getMonth() {
        return currentMonth;
    }
}
