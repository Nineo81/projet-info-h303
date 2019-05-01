package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Trottinette;
import model.XmlParserMech;
import view.ConnectionController;
import view.MainWindowController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private LoginPage loginPage;
    private Scene loginScene;
    private Stage window;

    @FXML
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));
    @FXML
    FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;

        window.setScene(createLoginScene());
        window.show();
    }

    public Scene createLoginScene() throws IOException {
        loginPage = new LoginPage();
        loginPage.setMain(this);

        AnchorPane content = loginLoader.load();
        ConnectionController controller = loginLoader.getController();
        controller.setLoginPage(loginPage);

        return new Scene(content);
    }

    public Scene createMainWindowScene(ObservableList<Trottinette> trottinettes) throws IOException {
        MainWindowController mainWindowController = new MainWindowController(trottinettes);
        mainWindowLoader.setController(mainWindowController);
        AnchorPane content = mainWindowLoader.load();

        return new Scene(content);
    }

    public void openMainWindow(ObservableList<Trottinette> trottinettes) throws IOException {
        window.setScene(createMainWindowScene(trottinettes));
    }
}
