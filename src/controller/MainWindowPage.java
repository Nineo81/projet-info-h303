package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Path;
import model.Trottinette;
import model.User;
import util.AlertMessage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * MainWindowPage link buttons action from MainWindowController to database access and window access
 */
public class MainWindowPage {

    private Main main;

    /**
     * Open a subWindow showing history of user given by the database
     */
    public void historyAccess(){
        Database db = Database.getInstance();
        db.open();

        ArrayList<Path> list = null;

        try {
            list = db.getUserHistory(Integer.parseInt(main.getUsername()));
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'accéder à l'historique !");
        }

        ObservableList<Path> pathList = FXCollections.observableArrayList();
        pathList.addAll(list);

        main.openHistory(pathList);
    }

    /**
     * Open a subwindow showing addTrotti
     */
    public void addTrotti(){
        main.openNewTrotti();
    }

    /**
     * Open a subwindow showing request
     */
    public void request(){
        main.openQueries();
    }

    /**
     * Open a subWindow showing a list of user given by the database
     */
    public void userAccess(){
        Database db = Database.getInstance();
        db.open();
        ArrayList<User> userList = null;

        try {
            userList = db.getUsers();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'accéder à la liste des utilisateurs !");
        }

        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(userList);

        main.openUserList(users);
    }

    /**
     * Open a subwindow showing infoTrotti for user and reloader and manageTrotti for thecnicien,
     * they each need a trottinette given by the database.
     * @param tID ID of the trottinette
     * @param userType Type of the user
     */
    public void trottiAccess(int tID, String userType){

        Database db = Database.getInstance();
        db.open();

        Trottinette trottinette = null;
        try {
            trottinette = db.getTrottinette(tID);
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'accéder à la trottinette !");
        }

        if(userType.equals("technicien")){
            main.openManageTrotti(trottinette.getState(),trottinette.getTID());
        } else  {
            main.openInfoTrotti(Integer.toString(trottinette.getTID()),trottinette.getBattery(),trottinette.getPlaint(), trottinette.getState());
        }
    }

    /**
     *
     * @param main Set the main to access info and window
     */
    public void setMain(Main main){
        this.main = main;
    }
}
