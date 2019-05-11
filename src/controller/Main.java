package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Trottinette;
import view.ConnectionController;
import view.InfoTrottiController;
import view.MainWindowController;
import view.RegisterController;

import java.io.IOException;

public class Main extends Application {

    private LoginPage loginPage;
    private MainWindowPage mainWindowPage;
    private RegisterPage registerPage;
    private Stage window;
    private Stage subWindow;
    private String username;
    private String userType;

    private Scene loginScene;
    private Scene registerScene;
    private Scene infoTrottiScene;

    @FXML
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));
    @FXML
    FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
    @FXML
    FXMLLoader infoTrottiLoader = new FXMLLoader(getClass().getResource("../view/InfoTrotti.fxml"));
    @FXML
    FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));

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

    public void openInfoTrotti(String number, int battery, int complaint){
        InfoTrottiController controller = infoTrottiLoader.getController();
        controller.setBattery(battery);
        controller.setComplaint(complaint);
        controller.setNumber(number);
        controller.setUserType(userType);

        subWindow.setScene(infoTrottiScene);
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
