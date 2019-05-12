package view;

import controller.NewTrottiPage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewTrottiController {

    private NewTrottiPage newTrottiPage;

    @FXML
    private TextField TIDTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField posXTextField;

    @FXML
    private TextField posYTextField;

    @FXML
    private void initialize(){

    }

    @FXML
    private void handleOKAction(){
        newTrottiPage.addTrotti(
                Integer.parseInt(TIDTextField.getText()),
                modelTextField.getText(),
                Double.parseDouble(posXTextField.getText()),
                Double.parseDouble(posYTextField.getText()));
    }

    public void setNewTrottiPage(NewTrottiPage newTrottiPage) {
        this.newTrottiPage = newTrottiPage;
    }
}
