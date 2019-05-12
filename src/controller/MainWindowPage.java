package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Path;
import model.Trottinette;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindowPage {

    private Main main;

    /**
     * Open a node showing history of user
     */
    public void historyAccess(){
        Database db = Database.getInstance();
        db.open();

        ArrayList<Path> list = null;

        try {
            list = db.getUserHistory(Integer.parseInt(main.getUsername()));
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Path> pathList = FXCollections.observableArrayList();
        pathList.addAll(list);

        main.openHistory(pathList);
    }

    public void addTrotti(){
        main.openNewTrotti();
    }

    public void request(){
        main.openQueries();
    }

    public void userAccess(){
        Database db = Database.getInstance();
        db.open();
        ArrayList<User> userList = null;

        try {
            userList = db.getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(userList);

        main.openUserList(users);
    }

    public void trottiAccess(int tID, String userType){

        Database db = Database.getInstance();
        db.open();

        Trottinette trottinette = null;
        try {
            trottinette = db.getTrottinette(tID);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(userType.equals("technicien")){
            main.openManageTrotti(trottinette.getState(),trottinette.getTID());
        } else  {
            main.openInfoTrotti(Integer.toString(trottinette.getTID()),trottinette.getBattery(),trottinette.getPlaint(), trottinette.getState());
        }
    }

    public void setMain(Main main){
        this.main = main;
    }
}
