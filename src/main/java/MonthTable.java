import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        TableColumn startcol = new TableColumn("Start");
        startcol.setCellValueFactory(new PropertyValueFactory<Workday, String>("start"));
        TableColumn pausCol = new TableColumn("Pauser");
        pausCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("pauses"));
        TableColumn stopCol = new TableColumn("Stop");
        stopCol.setCellValueFactory(new PropertyValueFactory<Workday, String>("stop"));
        getColumns().addAll(dayCol, startcol, pausCol, stopCol);
    }


}
