package view;

import controller.QueriesPage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class QueriesController {

    private QueriesPage queriesPage;

    @FXML
    private TextArea result;

    @FXML
    private void handleR1Action(){
        result.setText(queriesPage.R1().toString());
    }

    @FXML
    private void handleR2Action(){
        result.setText(queriesPage.R2().toString());
    }

    @FXML
    private void handleR3Action(){
        result.setText(Integer.toString(queriesPage.R3()));
    }

    @FXML
    private void handleR4Action(){
        result.setText(queriesPage.R4().toString());
    }

    @FXML
    private void handleR5Action(){
        result.setText(queriesPage.R5().toString());
    }

    public void setQueriesPage(QueriesPage queriesPage) {
        this.queriesPage = queriesPage;
    }
}
