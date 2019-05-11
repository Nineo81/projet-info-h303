package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Path;
import model.Trottinette;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Path> pathList = FXCollections.observableArrayList();
        pathList.addAll(list);

        main.openHistory(pathList);
    }

    public void trottiAccess(int tID){

        Database db = Database.getInstance();
        db.open();

        Trottinette trottinette = null;
        try {
            trottinette = db.getTrottinette(tID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        main.openInfoTrotti(Integer.toString(trottinette.getTID()),trottinette.getBattery(),trottinette.getPlaint(), trottinette.getState());
    }

    public void setMain(Main main){
        this.main = main;
    }
}
