package view;

import controller.ComplaintPage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ComplaintController {

    private ComplaintPage complaintPage;

    private int TID;

    @FXML
    private TextArea note;

    @FXML
    private void handleOKAction(){
        complaintPage.resolveComplaint(TID, note.getText());
    }

    public void setComplaintPage(ComplaintPage complaintPage) {
        this.complaintPage = complaintPage;
    }

    public void setTID(int TID) {
        this.TID = TID;
    }
}
