package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ConnectionController;

import java.io.IOException;

public class Main extends Application {

    private LoginPage loginPage;
    private Scene loginScene;
    private Stage window;

    @FXML
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        loginPage = new LoginPage();

        AnchorPane content = loginLoader.load();
        ConnectionController controller = loginLoader.getController();
        controller.setLoginPage(loginPage);

        window.setScene(new Scene(content));
        window.show();
    }
}
