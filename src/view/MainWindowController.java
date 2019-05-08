package view;

import controller.MainWindowPage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.Trottinette;

public class MainWindowController {

    private ObservableList<Trottinette> trottinettes;

    private MainWindowPage mainWindowPage;

    @FXML
    private Label username;

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
        trottinette.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getTID())));
        position.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getModel())));
    }

    @FXML
    private void handleHistoryAction(){
        mainWindowPage.historyAccess();
    }

    /**
     * Handle click on a specified row of the table
     * @param e click event
     */

    @FXML
    private void clickItem(MouseEvent e){
        Trottinette trottinette = trottinetteTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Gives list of trottinette to the window
     * @param trottinettes specified list
     */

    public MainWindowController(String username,ObservableList<Trottinette> trottinettes){
        this.username.setText(username);
        this.trottinettes = trottinettes;
    }

    public void setMainWindowPage(MainWindowPage mainWindowPage) {
        this.mainWindowPage = mainWindowPage;
    }
}
