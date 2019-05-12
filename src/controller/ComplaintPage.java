package controller;

import model.Database;
import util.AlertMessage;

import java.sql.SQLException;

/**
 * ComplaintPage link button action to database access
 */
public class ComplaintPage {

    /**
     * Access database to update status of INTERVENTION
     * @param TID ID of the broken trottinette (int)
     * @param note Note left by the technicien after fix (String)
     */
    public void resolveComplaint(int TID, String note){
        Database db = Database.getInstance();
        db.open();

        try {
            db.resolveIntervention(TID, note);
            db.stateUpdate("libre", TID);
            db.clearComplain(TID);
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible de résoudre la plainte !");
        }
    }
}
