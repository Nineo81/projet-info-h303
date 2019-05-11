package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import view.*;

import java.io.IOException;

public class Main extends Application {

    private LoginPage loginPage;
    private MainWindowPage mainWindowPage;
    private RegisterPage registerPage;
    private InfoTrottiPage infoTrottiPage;
    private UserPage userPage;
    private ManageTrottiPage manageTrottiPage;

    private Stage window;
    private Stage subWindow;

    private String username;
    private String userType;

    private Scene loginScene;
    private Scene registerScene;
    private Scene infoTrottiScene;
    private Scene historyScene;
    private Scene userListScene;
    private Scene manageTrottiScene;

    @FXML
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));
    @FXML
    FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
    @FXML
    FXMLLoader infoTrottiLoader = new FXMLLoader(getClass().getResource("../view/InfoTrotti.fxml"));
    @FXML
    FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
    @FXML
    FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("../view/History.fxml"));
    @FXML
    FXMLLoader userListLoader = new FXMLLoader(getClass().getResource("../view/UserList.fxml"));
    @FXML
    FXMLLoader manageTrottiLoader = new FXMLLoader(getClass().getResource("../view/ManageTrotti.fxml"));

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
        userListScene = createUserScene();
        manageTrottiScene = createManageTrottiScene();

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

    private Scene createUserScene(){
        userPage = new UserPage();
        userPage.setMain(this);

        AnchorPane content = null;
        try {
            content = userListLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserListController controller = userListLoader.getController();
        controller.setUserPage(userPage);

        return new Scene(content);
    }

    private Scene createManageTrottiScene(){
        manageTrottiPage = new ManageTrottiPage();
        manageTrottiPage.setMain(this);

        AnchorPane content = null;
        try {
            content = manageTrottiLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ManageTrottiController controller = manageTrottiLoader.getController();
        controller.setManageTrottiPage(manageTrottiPage);

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
        controller.setUserType(userType);
        controller.showStateColumn();

        return new Scene(content);
    }

    public void openManageTrotti(String state, int TID){
        ManageTrottiController controller = manageTrottiLoader.getController();
        controller.setInitialCB(state);
        controller.setNumber(TID);

        subWindow.setScene(manageTrottiScene);
        subWindow.show();
    }

    public void openMainWindow(ObservableList<Trottinette> trottinettes) {
        window.setScene(createMainWindowScene(trottinettes));
    }

    public void openUserList(ObservableList<User> users){
        UserListController controller = userListLoader.getController();
        controller.setUserList(users);

        subWindow.setScene(userListScene);
        subWindow.show();
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

    public void openUser(int username, int password, long account){
        RegisterController controller = registerLoader.getController();
        controller.presettingUser(username, password, account);

        subWindow.setScene(registerScene);
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
