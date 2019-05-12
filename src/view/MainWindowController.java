package view;

import controller.MainWindowPage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Trottinette;

public class MainWindowController {

    private MainWindowPage mainWindowPage;

    private String userType;

    @FXML
    private Label username;

    @FXML
    private Button button;

    @FXML
    private Button addTrotti;

    @FXML
    private Button requests;

    @FXML
    private TableView<Trottinette> trottinetteTable;

    @FXML
    private TableColumn<Trottinette, Integer> trottinette;

    @FXML
    private TableColumn<Trottinette, Double> posX;

    @FXML
    private TableColumn<Trottinette, Double> posY;

    @FXML
    private TableColumn<Trottinette, String> state;

    /**
     * Initialize window and specify column content
     */

    @FXML
    private void initialize(){
        //Specify column content
        trottinette.setCellValueFactory(new PropertyValueFactory<>("TID"));
        posX.setCellValueFactory(new PropertyValueFactory<>("posX"));
        posY.setCellValueFactory(new PropertyValueFactory<>("posY"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));

        trottinetteTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    clickTrotti(trottinetteTable.getSelectionModel().getSelectedItem().getTID());
                }
            }
        });
    }

    @FXML
    private void handleButtonAction(){
        if(userType.equals("technicien")){
            button.setText("Utilisateur");
            mainWindowPage.userAccess();
        } else {
            button.setText("Historique");
            mainWindowPage.historyAccess();
        }
    }

    @FXML
    private void handleRequestAction(){
        mainWindowPage.request();
    }

    @FXML
    private void handleAddAction(){
        mainWindowPage.addTrotti();
    }

    /**
     * Handle click on a specified row of the table
     */

    private void clickTrotti(int tID){
        mainWindowPage.trottiAccess(tID, userType);
    }

    public void setUsername(String username){
        this.username.setText(username);
    }

    public void setUserType(String userType){
        this.userType = userType;
    }

    public void showStateColumn(){
        if(userType.equals("technicien")){
            state.setVisible(true);
            addTrotti.setVisible(true);
            requests.setVisible(true);
        } else{
            state.setVisible(false);
            addTrotti.setVisible(false);
            requests.setVisible(false);
        }
    }

    public void setTrottinettes(ObservableList<Trottinette> trottinettes){
        ObservableList<Trottinette> trottinettes1 = trottinettes;
        //Specify table object
        trottinetteTable.setItems(trottinettes1);
        this.trottinetteTable.refresh();
    }

    public void setMainWindowPage(MainWindowPage mainWindowPage) {
        this.mainWindowPage = mainWindowPage;
    }
}
