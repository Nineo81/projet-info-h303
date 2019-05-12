package controller;

import model.Database;

import java.sql.SQLException;

public class ManageTrottiPage {

    private Main main;

    public void updateState(int TID, String state){
        Database db = Database.getInstance();
        db.open();

        try {
            db.stateUpdate(state, TID);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void openComplaint(int TID){
        main.openComplaint(TID);
    }

    public void deleteTrotti(int TID){
        Database db = Database.getInstance();
        db.open();

        try {
            db.deleteTrottinette(TID);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main){
        this.main = main;
    }
}
