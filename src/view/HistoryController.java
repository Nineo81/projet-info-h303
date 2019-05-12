package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Path;

public class HistoryController {

    @FXML
    TableView historyTable;

    @FXML
    TableColumn<Path, String> startTime;

    @FXML
    TableColumn<Path, String> endTime;

    @FXML
    TableColumn<Path, String> sourceX;

    @FXML
    TableColumn<Path, String> sourceY;

    @FXML
    TableColumn<Path, String> destinationY;

    @FXML
    TableColumn<Path, String> destinationX;

    @FXML
    private void initialize(){

        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        sourceX.setCellValueFactory(new PropertyValueFactory<>("sourceX"));
        sourceY.setCellValueFactory(new PropertyValueFactory<>("sourceY"));
        destinationX.setCellValueFactory(new PropertyValueFactory<>("destinationX"));
        destinationY.setCellValueFactory(new PropertyValueFactory<>("destinationY"));
    }

    public void setPathList(ObservableList<Path> pathList){
        ObservableList<Path> pathList1 = pathList;
        //Specify table object
        this.historyTable.setItems(pathList1);
        this.historyTable.refresh();
    }
}
