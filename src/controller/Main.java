package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CSVParser;
import model.Database;
import model.Trottinette;
import view.ConnectionController;
import view.InfoTrottiController;
import view.MainWindowController;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private LoginPage loginPage;
    private MainWindowPage mainWindowPage;
    private Stage window;
    private Stage subWindow;
    private String username;
    private String userType;

    @FXML
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));
    @FXML
    FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
    @FXML
    FXMLLoader infoTrottiLoader = new FXMLLoader(getClass().getResource("../view/InfoTrotti.fxml"));

    public static void main(String[] args){
        //ArrayList<HashMap<String,String>> list = CSVParser.parsing("Database_Data/reloads.csv");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Database database = Database.getInstance();
        database.open();
        try {
            database.init();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        window.setScene(createLoginScene());
        window.show();

    }

    private Scene createLoginScene() {
        loginPage = new LoginPage();
        loginPage.setMain(this);

        AnchorPane content = null;
        try {
            content = loginLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectionController controller = loginLoader.getController();
        controller.setLoginPage(loginPage);

        return new Scene(content);
    }

    private Scene createInfoTrottiScene(String number, int battery, int complaint){
        InfoTrottiController infoTrottiController = new InfoTrottiController(number, battery, complaint,userType);
        infoTrottiLoader.setController(infoTrottiController);

        AnchorPane content = null;
        try {
            content = infoTrottiLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(content);
    }

    private Scene createMainWindowScene(ObservableList<Trottinette> trottinettes) {
        mainWindowPage = new MainWindowPage();
        mainWindowPage.setMain(this);

        MainWindowController mainWindowController = new MainWindowController(username, trottinettes);
        mainWindowController.setMainWindowPage(mainWindowPage);
        mainWindowLoader.setController(mainWindowController);

        AnchorPane content = null;
        try {
            content = mainWindowLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(content);
    }

    public void openMainWindow(ObservableList<Trottinette> trottinettes) {
        window.setScene(createMainWindowScene(trottinettes));
    }

    public void openInfoTrotti(int battery, int complaint){
        subWindow.setScene(createInfoTrottiScene(username, battery, complaint));
        subWindow.show();
    }

    public String getUsername() {
        return username;
    }
}
