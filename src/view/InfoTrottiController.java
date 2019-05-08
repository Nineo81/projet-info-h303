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

    public InfoTrottiController(String number, int battery, int complaint, String userType){
        this.number.setText(number);
        this.battery.setText(String.valueOf(battery));
        this.complaint.setText(String.valueOf(complaint));
        if(userType.equals("rechargeur")){
            this.recharge.setVisible(true);
        }
        else{
            this.recharge.setVisible(false);
        }
    }

    @FXML
    private void handleComplaintAction(){

    }

    @FXML
    private void handleRechargeAction(){

    }
}
