package view;

import controller.ManageTrottiPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


public class ManageTrottiController {

    private int TID;

    private ManageTrottiPage manageTrottiPage;

    @FXML
    private Label numberLabel;

    @FXML
    private CheckBox freeCB;

    @FXML
    private CheckBox chargingCB;

    @FXML
    private CheckBox brokenCB;

    @FXML
    private void initialize(){
        freeCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    chargingCB.setSelected(false);
                    brokenCB.setSelected(false);
                    manageTrottiPage.updateState(TID,"libre");
                }
            }
        });
        chargingCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    freeCB.setSelected(false);
                    brokenCB.setSelected(false);
                    manageTrottiPage.updateState(TID,"en charge");
                }
            }
        });
        brokenCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    chargingCB.setSelected(false);
                    freeCB.setSelected(false);
                    manageTrottiPage.updateState(TID, "defectueuse");
                }
            }
        });
    }

    @FXML
    private void handleDeleteAction(){
        manageTrottiPage.deleteTrotti(TID);
    }

    public void setInitialCB(String state){
        if(state.equals("libre")){
            freeCB.setSelected(true);
        }else if(state.equals("en charge")){
            chargingCB.setSelected(true);
        }else{
            brokenCB.setSelected(true);
        }
    }

    public void setNumber(int number){
        this.TID = number;
        this.numberLabel.setText(Integer.toString(this.TID));
    }

    public void setManageTrottiPage(ManageTrottiPage manageTrottiPage){
        this.manageTrottiPage = manageTrottiPage;
    }
}
