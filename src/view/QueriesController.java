package view;

import controller.QueriesPage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class QueriesController {

    private QueriesPage queriesPage;

    @FXML
    private Label answer;

    @FXML
    private void handleR2Action(){
        answer.setText(queriesPage.R2().toString());
    }

    @FXML
    private void handleR3Action(){
        answer.setText(Integer.toString(queriesPage.R3()));
    }

    @FXML
    private void handleR4Action(){
        answer.setText(queriesPage.R4().toString());
    }

    @FXML
    private void handleR5Action(){
        answer.setText(queriesPage.R5().toString());
    }

    public void setQueriesPage(QueriesPage queriesPage) {
        this.queriesPage = queriesPage;
    }
}
