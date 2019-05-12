package view;

import controller.RegisterPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class RegisterController {

    private RegisterPage registerPage;

    @FXML
    private CheckBox userCB;

    @FXML
    private CheckBox reloaderCB;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField accountTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField communeTextField;

    @FXML
    private TextField zipCodeTextField;

    @FXML
    private Button cancel;

    @FXML
    private void initialize(){

        //Setting a listener on userCB
        userCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                //allow only userCB to be selected
                reloaderCB.setSelected(!newValue);
                //Disable use of following textField
                if(newValue){
                    surnameTextField.setDisable(true);
                    nameTextField.setDisable(true);
                    phoneTextField.setDisable(true);
                    streetTextField.setDisable(true);
                    numberTextField.setDisable(true);
                    communeTextField.setDisable(true);
                    zipCodeTextField.setDisable(true);
                }
            }
        });
        //Setting a listener on reloaderCB
        reloaderCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                //allow only reloader to be selected
                userCB.setSelected(!newValue);
                //allow use of following textField
                if(newValue){
                    surnameTextField.setDisable(false);
                    nameTextField.setDisable(false);
                    phoneTextField.setDisable(false);
                    streetTextField.setDisable(false);
                    numberTextField.setDisable(false);
                    communeTextField.setDisable(false);
                    zipCodeTextField.setDisable(false);
                }
            }
        });
    }

    @FXML
    private void handleOKAction(){
        HashMap<String, String> newUser = new HashMap<>();

        if(userCB.isSelected()){
            newUser.put("ID",usernameTextField.getText());
            newUser.put("password",passwordTextField.getText());
            newUser.put("bankaccount",accountTextField.getText());
        }
        else if(reloaderCB.isSelected()){
            newUser.put("ID",usernameTextField.getText());
            newUser.put("password",passwordTextField.getText());
            newUser.put("bankaccount",accountTextField.getText());
            newUser.put("lastname",surnameTextField.getText());
            newUser.put("firstname",nameTextField.getText());
            newUser.put("phone",phoneTextField.getText());
            newUser.put("city",communeTextField.getText());
            newUser.put("cp",zipCodeTextField.getText());
            newUser.put("street",streetTextField.getText());
            newUser.put("number",numberTextField.getText());
        }
        else{
            //TO DO: alert message no checkBox selected
        }
        registerPage.register(newUser);
    }

    @FXML
    private void handleCancelAction(){
        registerPage.cancel();
    }

    public void setRegisterPage(RegisterPage registerPage){
        this.registerPage = registerPage;
    }

    public void presettingUser(int username, int password, long account){
        usernameTextField.setText(Integer.toString(username));
        passwordTextField.setText(Integer.toString(password));
        accountTextField.setText(Long.toString(account));
        cancel.setVisible(false);
        userCB.setVisible(false);
        reloaderCB.setVisible(false);
    }

}
