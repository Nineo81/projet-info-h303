package view;

import controller.LoginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController {

    private LoginPage loginPage;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    void initialize() {


    }

    /**
     * Handle Login action and gives both textField as parameter of the controller
     */

    @FXML
    private void handleLoginAction() throws IOException {
        loginPage.login(username.getText(),password.getText());
    }

    /**
     * Handle Register action and gives both textField as parameter of the controller
     */

    @FXML
    private void handleRegisterAction() {
        loginPage.register(username.getText(),password.getText());
    }

    /**
     * Set loginPage as the controller of the connection window
     * @param loginPage controller of the window
     */

    public void setLoginPage(LoginPage loginPage){
        this.loginPage =loginPage;
    }

}

