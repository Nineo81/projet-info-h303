package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Path;
import model.Trottinette;
import view.*;

import java.io.IOException;

public class Main extends Application {

    private LoginPage loginPage;
    private MainWindowPage mainWindowPage;
    private RegisterPage registerPage;
    private InfoTrottiPage infoTrottiPage;
    private Stage window;
    private Stage subWindow;
    private String username;
    private String userType;

    private Scene loginScene;
    private Scene registerScene;
    private Scene infoTrottiScene;
    private Scene historyScene;

    @FXML
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));
    @FXML
    FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
    @FXML
    FXMLLoader infoTrottiLoader = new FXMLLoader(getClass().getResource("../view/InfoTrotti.fxml"));
    @FXML
    FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
    @FXML
    FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("../view/history.fxml"));

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        subWindow = new Stage();

        infoTrottiScene = createInfoTrottiScene();
        loginScene = createLoginScene();
        registerScene = createRegisterScene();
        historyScene = createHIstory();

        window.setScene(loginScene);
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

    private Scene createRegisterScene(){
        registerPage = new RegisterPage();
        registerPage.setMain(this);

        AnchorPane content = null;
        try {
            content = registerLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterController controller = registerLoader.getController();
        controller.setRegisterPage(registerPage);

        return new Scene(content);
    }

    private Scene createHIstory(){
        AnchorPane content = null;

        try {
            content = historyLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(content);
    }

    private Scene createInfoTrottiScene(){

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

        AnchorPane content = null;
        try {
            content = mainWindowLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainWindowController controller = mainWindowLoader.getController();
        controller.setMainWindowPage(mainWindowPage);
        controller.setTrottinettes(trottinettes);
        controller.setUsername(username);

        return new Scene(content);
    }

    public void openMainWindow(ObservableList<Trottinette> trottinettes) {
        window.setScene(createMainWindowScene(trottinettes));
    }

    public void openInfoTrotti(String number, int battery, int complaint, String state){
        infoTrottiPage = new InfoTrottiPage();
        infoTrottiPage.setMain(this);

        InfoTrottiController controller = infoTrottiLoader.getController();
        controller.setBattery(battery);
        controller.setComplaint(complaint);
        controller.setNumber(number);
        controller.setUserType(userType);
        controller.setPos(state);
        controller.setInfoTrottiPage(infoTrottiPage);

        subWindow.setScene(infoTrottiScene);
        subWindow.show();
    }

    public void openHistory(ObservableList<Path> pathList){
        HistoryController controller = historyLoader.getController();
        controller.setPathList(pathList);

        subWindow.setScene(historyScene);
        subWindow.show();
    }

    public void openRegister(){
        window.setScene(registerScene);
    }

    public void openLogin(){
        window.setScene(loginScene);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }
}
