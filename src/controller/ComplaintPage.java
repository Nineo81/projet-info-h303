package controller;

import model.Database;

import java.sql.SQLException;

public class ComplaintPage {

    public void resolveComplaint(int TID, String note){
        Database db = Database.getInstance();
        db.open();

        try {
            db.resolveIntervention(TID, note);
            db.stateUpdate("libre", TID);
            db.clearComplain(TID);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        Main main1 = main;
    }
}
