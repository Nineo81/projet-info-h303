package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Path;
import model.Trottinette;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void trottiAccess(Trottinette trottinette){
        main.openInfoTrotti(Integer.toString(trottinette.getTID()),trottinette.getBattery(),trottinette.getPlaint());
    }

    public void setMain(Main main){
        this.main = main;
    }
}
