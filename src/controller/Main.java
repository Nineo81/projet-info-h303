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
    private Scene newTrottiScene;
    private Scene queriesScene;
    private Scene complaintScene;

    @FXML
    private
    FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Connection.fxml"));
    @FXML
    private
    FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../view/MainWindow.fxml"));
    @FXML
    private
    FXMLLoader infoTrottiLoader = new FXMLLoader(getClass().getResource("../view/InfoTrotti.fxml"));
    @FXML
    private
    FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
    @FXML
    private
    FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("../view/History.fxml"));
    @FXML
    private
    FXMLLoader userListLoader = new FXMLLoader(getClass().getResource("../view/UserList.fxml"));
    @FXML
    private
    FXMLLoader manageTrottiLoader = new FXMLLoader(getClass().getResource("../view/ManageTrotti.fxml"));
    @FXML
    private
    FXMLLoader newTrottiLoader = new FXMLLoader(getClass().getResource("../view/NewTrotti.fxml"));
    @FXML
    private
    FXMLLoader queriesLoader = new FXMLLoader(getClass().getResource("../view/Queries.fxml"));
    @FXML
    private
    FXMLLoader complaintLoader = new FXMLLoader(getClass().getResource("../view/Complaint.fxml"));

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        subWindow = new Stage();

        infoTrottiScene = createInfoTrottiScene();
        loginScene = createLoginScene();
        registerScene = createRegisterScene();
        historyScene = createHIstory();
        userListScene = createUserScene();
        manageTrottiScene = createManageTrottiScene();
        newTrottiScene = createNewTrottiScene();
        queriesScene = createQueriesScene();
        complaintScene = createComplaintScene();

        window.setScene(loginScene);
        window.show();
    }

    private Scene createLoginScene() {
        LoginPage loginPage = new LoginPage();
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
        RegisterPage registerPage = new RegisterPage();
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
        UserPage userPage = new UserPage();
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

    private Scene createNewTrottiScene(){
        NewTrottiPage newTrottiPage = new NewTrottiPage();
        newTrottiPage.setMain(this);

        AnchorPane content = null;
        try {
            content = newTrottiLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NewTrottiController controller = newTrottiLoader.getController();
        controller.setNewTrottiPage(newTrottiPage);

        return new Scene(content);
    }

    private Scene createComplaintScene(){
        ComplaintPage complaintPage = new ComplaintPage();
        complaintPage.setMain(this);

        AnchorPane content = null;
        try {
            content = complaintLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComplaintController controller = complaintLoader.getController();
        controller.setComplaintPage(complaintPage);

        return new Scene(content);
    }

    private Scene createQueriesScene(){
        QueriesPage queriesPage = new QueriesPage();
        queriesPage.setMain(this);

        AnchorPane content = null;
        try {
            content = queriesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        QueriesController controller = queriesLoader.getController();
        controller.setQueriesPage(queriesPage);

        return new Scene(content);
    }

    private Scene createManageTrottiScene(){
        ManageTrottiPage manageTrottiPage = new ManageTrottiPage();
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
        MainWindowPage mainWindowPage = new MainWindowPage();
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

    public void openComplaint(int TID){
        ComplaintController controller = complaintLoader.getController();
        controller.setTID(TID);

        subWindow.setScene(complaintScene);
    }

    public void openUserList(ObservableList<User> users){
        UserListController controller = userListLoader.getController();
        controller.setUserList(users);

        subWindow.setScene(userListScene);
        subWindow.show();
    }

    public void openInfoTrotti(String number, int battery, int complaint, String state){
        InfoTrottiPage infoTrottiPage = new InfoTrottiPage();
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

    public void openNewTrotti(){
        subWindow.setScene(newTrottiScene);
        subWindow.show();
    }

    public void openQueries(){
        subWindow.setScene(queriesScene);
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
