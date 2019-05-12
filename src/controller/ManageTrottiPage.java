package controller;

import model.Database;
import util.AlertMessage;

import java.sql.SQLException;

/**
 * ManageTrottiPage link buttons action from ManageTrottiController to database access and window access
 */
public class ManageTrottiPage {

    private Main main;

    /**
     * Ask the database to change the state of a given trottinette
     * @param TID ID of the trottinette
     * @param state new state to set
     */
    public void updateState(int TID, String state){
        Database db = Database.getInstance();
        db.open();

        try {
            db.stateUpdate(state, TID);
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible de changer le status !");
        }

    }

    /**
     * Change subWindow to openComplaint
     * @param TID ID of the trottinette
     */
    public void openComplaint(int TID){
        main.openComplaint(TID);
    }

    /**
     * Ask database to delete a given trottinette
     * @param TID ID of the trottinette
     */
    public void deleteTrotti(int TID){
        Database db = Database.getInstance();
        db.open();

        try {
            db.deleteTrottinette(TID);
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible de supprimer la trottinette !");
        }
    }

    /**
     *
     * @param main Set the main to access window
     */
    public void setMain(Main main){
        this.main = main;
    }
}
