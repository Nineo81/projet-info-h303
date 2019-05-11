package view;

import controller.MainWindowPage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Trottinette, Integer> trottinette;

    @FXML
    private TableColumn<Trottinette, Integer> position;

    /**
     * Initialize window and specify column content
     */

    @FXML
    private void initialize(){
        //Specify column content
        trottinette.setCellValueFactory(new PropertyValueFactory<>("TID"));
        position.setCellValueFactory(new PropertyValueFactory<>("posX"));

        trottinetteTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    clickTrotti(trottinetteTable.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    @FXML
    private void handleHistoryAction(){
        mainWindowPage.historyAccess();
    }

    /**
     * Handle click on a specified row of the table
     */

    private void clickTrotti(Trottinette trotti){
        mainWindowPage.trottiAccess(trotti);
    }

    public void setUsername(String username){
        this.username.setText(username);
    }

    public void setTrottinettes(ObservableList<Trottinette> trottinettes){
        this.trottinettes = trottinettes;
        //Specify table object
        trottinetteTable.setItems(this.trottinettes);
        this.trottinetteTable.refresh();
    }

    public void setMainWindowPage(MainWindowPage mainWindowPage) {
        this.mainWindowPage = mainWindowPage;
    }
}
