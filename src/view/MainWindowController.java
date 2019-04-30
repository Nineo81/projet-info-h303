package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Trottinette;

public class MainWindowController {

    private ObservableList<Trottinette> trottinettes;

    @FXML
    private TableView<Trottinette> trottinetteTable;

    @FXML
    private TableColumn<Trottinette, String> trottinette;

    @FXML
    private TableColumn<Trottinette, String> position;

    /**
     * Initialize window and specify column content
     */

    @FXML
    private void initialize(){
        //Specify table object
        trottinetteTable.setItems(trottinettes);
        //Specify column content
        trottinette.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        position.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
    }

    /**
     * Gives list of trottinette to the window
     * @param trottinettes specified list
     */

    public MainWindowController(ObservableList<Trottinette> trottinettes){
        this.trottinettes = trottinettes;
    }
}
