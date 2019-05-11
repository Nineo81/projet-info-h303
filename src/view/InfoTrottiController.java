package view;

import controller.InfoTrottiPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class InfoTrottiController {

    private InfoTrottiPage infoTrottiPage;

    @FXML
    private Label number;

    @FXML
    private Label battery;

    @FXML
    private Label complaint;

    @FXML
    private TextField posXTextField;

    @FXML
    private TextField posYTextField;

    @FXML
    private Text posXText;

    @FXML
    private Text posYText;

    @FXML
    private Button recharge;

    @FXML
    private void handleComplaintAction(){
        infoTrottiPage.complaint(Integer.parseInt(number.getText()));
    }

    @FXML
    private void handleRechargeAction(){
        infoTrottiPage.charging(Integer.parseInt(number.getText()),posXTextField.getText(),posYTextField.getText());
    }

    public void setNumber(String number){
        this.number.setText(number);
    }

    public void setBattery(int battery){
        this.battery.setText(String.valueOf(battery));
    }

    public void setComplaint(int complaint){
        this.complaint.setText(String.valueOf(complaint));
    }

    public void setUserType(String userType){
        if(userType.equals("rechargeur")){
            this.recharge.setVisible(true);
        }
        else{
            this.recharge.setVisible(false);
        }
    }

    public void setPos(String state){
        if(state.equals("libre")){
            posXText.setVisible(false);
            posYText.setVisible(false);
            posXTextField.setVisible(false);
            posYTextField.setVisible(false);
        } else if(state.equals("en charge")){
            posXText.setVisible(true);
            posYText.setVisible(true);
            posXTextField.setVisible(true);
            posYTextField.setVisible(true);
        }
    }

    public void setInfoTrottiPage(InfoTrottiPage infoTrottiPage){
        this.infoTrottiPage = infoTrottiPage;
    }
}
