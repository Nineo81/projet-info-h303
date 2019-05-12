package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import util.AlertMessage;
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

    /**
     * Launch the application
     * @param args not used in this application (default)
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Called by launch in constructor and instanciate subWindow scene as well as show the loginScene
     * @param primaryStage main Stage on which is shown loginScene and mainWindowScene
     */
    @Override
    public void start(Stage primaryStage) {
        //Rename stage for clarity
        window = primaryStage;
        //Create a substage for showing side information
        subWindow = new Stage();
        //Instantiation of Scene shown on subWindow
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

    /**
     * Create the loginScene from the fxml and associate loginPage to it
     * @return Scene
     */
    private Scene createLoginScene() {
        LoginPage loginPage = new LoginPage();
        loginPage.setMain(this);

        AnchorPane content = null;
        try {
            content = loginLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }
        ConnectionController controller = loginLoader.getController();
        controller.setLoginPage(loginPage);

        return new Scene(content);
    }

    /**
     * Create the registerScene from the fxml and associate registerPage to it
     * @return Scene
     */
    private Scene createRegisterScene(){
        RegisterPage registerPage = new RegisterPage();
        registerPage.setMain(this);

        AnchorPane content = null;
        try {
            content = registerLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }
        RegisterController controller = registerLoader.getController();
        controller.setRegisterPage(registerPage);

        return new Scene(content);
    }

    /**
     * Create HistoryScene from fxml
     * @return Scene
     */
    private Scene createHIstory(){
        AnchorPane content = null;

        try {
            content = historyLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        return new Scene(content);
    }

    /**
     * Create infoTrottiScene from fxml
     * @return Scene
     */
    private Scene createInfoTrottiScene(){

        AnchorPane content = null;
        try {
            content = infoTrottiLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        return new Scene(content);
    }

    /**
     * Create userScene from fxml and associate userPage to it
     * @return Scene
     */
    private Scene createUserScene(){
        UserPage userPage = new UserPage();
        userPage.setMain(this);

        AnchorPane content = null;
        try {
            content = userListLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }
        UserListController controller = userListLoader.getController();
        controller.setUserPage(userPage);

        return new Scene(content);
    }

    /**
     * Create newTrottiScene from fxml and associate newTrottiPage to it
     * @return Scene
     */
    private Scene createNewTrottiScene(){
        NewTrottiPage newTrottiPage = new NewTrottiPage();

        AnchorPane content = null;
        try {
            content = newTrottiLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        NewTrottiController controller = newTrottiLoader.getController();
        controller.setNewTrottiPage(newTrottiPage);

        return new Scene(content);
    }

    /**
     * Create complaintScene from fxml and associate complaintPage to it
     * @return Scene
     */
    private Scene createComplaintScene(){
        ComplaintPage complaintPage = new ComplaintPage();

        AnchorPane content = null;
        try {
            content = complaintLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        ComplaintController controller = complaintLoader.getController();
        controller.setComplaintPage(complaintPage);

        return new Scene(content);
    }

    /**
     * Create queriesScene from fxml and associate queriesPage to it
     * @return Scene
     */
    private Scene createQueriesScene(){
        QueriesPage queriesPage = new QueriesPage();

        AnchorPane content = null;
        try {
            content = queriesLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        QueriesController controller = queriesLoader.getController();
        controller.setQueriesPage(queriesPage);

        return new Scene(content);
    }

    /**
     * Create manageTrottiScene from fxml and associate manageTrottiPage to it
     * @return Scene
     */
    private Scene createManageTrottiScene(){
        ManageTrottiPage manageTrottiPage = new ManageTrottiPage();
        manageTrottiPage.setMain(this);

        AnchorPane content = null;
        try {
            content = manageTrottiLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        ManageTrottiController controller = manageTrottiLoader.getController();
        controller.setManageTrottiPage(manageTrottiPage);

        return new Scene(content);
    }

    /**
     * Create mainWindowScene from fxml and associate mainWindowPage to it
     * @param trottinettes List of trotinette free for a user
     * @return Scene
     */
    private Scene createMainWindowScene(ObservableList<Trottinette> trottinettes) {
        MainWindowPage mainWindowPage = new MainWindowPage();
        mainWindowPage.setMain(this);

        AnchorPane content = null;
        try {
            content = mainWindowLoader.load();
        } catch (IOException e) {
            AlertMessage.alert("Impossible de charger la page !");
        }

        MainWindowController controller = mainWindowLoader.getController();
        controller.setMainWindowPage(mainWindowPage);
        controller.setTrottinettes(trottinettes);
        controller.setUsername(username);
        controller.setUserType(userType);
        controller.showStateColumn();

        return new Scene(content);
    }

    /**
     * Called when opening manageTrotti, will show content on subWindow
     * @param state State of the trottinette ("libre","en charge","defectueuse")
     * @param TID ID of the Trottinette (int)
     */
    public void openManageTrotti(String state, int TID){
        ManageTrottiController controller = manageTrottiLoader.getController();
        controller.setNumber(TID);
        controller.setInitialCB(state);

        subWindow.setScene(manageTrottiScene);
        subWindow.show();
    }

    /**
     * Called when opening MainWindow, will show content on window
     * @param trottinettes List of trottinette free for the user
     */
    public void openMainWindow(ObservableList<Trottinette> trottinettes) {
        window.setScene(createMainWindowScene(trottinettes));
    }

    /**
     * Called when opening Complaint, will show content on subWindow
     * @param TID ID of the trottinette (int)
     */
    public void openComplaint(int TID){
        ComplaintController controller = complaintLoader.getController();
        controller.setTID(TID);

        subWindow.setScene(complaintScene);
    }

    /**
     * Called when opening UserList, will show content on subWindow
     * @param users List of users that do not posses reloading right
     */
    public void openUserList(ObservableList<User> users){
        UserListController controller = userListLoader.getController();
        controller.setUserList(users);

        subWindow.setScene(userListScene);
        subWindow.show();
    }

    /**
     * Called when opening InfoTrotti, will show on subWindow
     * @param number ID of trottinette (int)
     * @param battery Battery level of trottinette (1,2,3,4)
     * @param complaint Number of complaints on trottinette (int)
     * @param state Curent State of trottinette ("libre","en charge","defectueuse")
     */
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

    /**
     * Called when opening History, will show on subWindow
     * @param pathList List of path the user did
     */
    public void openHistory(ObservableList<Path> pathList){
        HistoryController controller = historyLoader.getController();
        controller.setPathList(pathList);

        subWindow.setScene(historyScene);
        subWindow.show();
    }

    /**
     * Called when opening NewTrotti, will show on subwindow
     */
    public void openNewTrotti(){
        subWindow.setScene(newTrottiScene);
        subWindow.show();
    }

    /**
     * Called when opening Queries, will show on subWindow
     */
    public void openQueries(){
        subWindow.setScene(queriesScene);
        subWindow.show();
    }

    /**
     * Called when opening User, will show on subWindow
     * @param username ID of user (int)
     * @param password Password of user (int)
     * @param account BankAccount of user (long)
     */
    public void openUser(int username, int password, long account){
        RegisterController controller = registerLoader.getController();
        controller.presettingUser(username, password, account);

        subWindow.setScene(registerScene);
    }

    /**
     * Called when opening Register, will show on window
     */
    public void openRegister(){
        window.setScene(registerScene);
    }

    /**
     * Called when opening Login, will show on window
     */
    public void openLogin(){
        window.setScene(loginScene);
    }

    /**
     *
     * @return Username of current user (String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username of current user of the application
     * @param username Current user username (String)
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Set userType of current user of the application
     * @param userType Current user userType (String)
     */
    public void setUserType(String userType){
        this.userType = userType;
    }
}
