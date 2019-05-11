package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class InfoTrottiController {

    @FXML
    private Label number;

    @FXML
    private Label battery;

    @FXML
    private Label complaint;

    @FXML
    private Button recharge;

    @FXML
    private void handleComplaintAction(){

    }

    @FXML
    private void handleRechargeAction(){

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
}
