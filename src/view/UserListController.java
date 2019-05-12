package view;

import controller.UserPage;
import model.User;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UserListController {

    private UserPage userPage;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> user;

    @FXML
    private void initialize(){
        user.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    clickUser(userTable.getSelectionModel().getSelectedItem().getUsername(),
                            userTable.getSelectionModel().getSelectedItem().getPassword(),
                            userTable.getSelectionModel().getSelectedItem().getAccount());
                }
            }
        });
    }

    public void setUserList(ObservableList<User> userList) {
        ObservableList<User> users = userList;
        //specify tableobject
        this.userTable.setItems(users);
        this.userTable.refresh();
    }

    public void clickUser(int username, int password, long account){
        userPage.upgradeUser(username, password, account);
    }

    public void setUserPage(UserPage userPage){
        this.userPage = userPage;
    }
}
