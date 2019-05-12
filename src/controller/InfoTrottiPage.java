package controller;

import model.Database;
import model.Trottinette;
import util.AlertMessage;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * InfoTrottiPage link buttons action from InfoTrottiController to database access and window access
 */
public class InfoTrottiPage {

    private Main main;

    /**
     * Increment the number of complaint on a trottinette in the database
     * @param TID
     */
    public void complaint(int TID){
        Database db = Database.getInstance();
        db.open();

        try {
            db.introduceComplain(TID);
            db.addingIntervention(TID,main.getUsername());
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible de porter plainte !");
        } catch (ParseException e) {
        }
    }

    /**
     * When not in charge, update state of trottinette to in charge and add a new recharge to RECHARGE
     * When already in charge, update state of trottinette to free and update RECHARGE
     * @param TID ID of the trottinette to charge
     * @param destX Position X where the trottinette is left after charge
     * @param destY Position Y where the trottinette is left after charge
     */
    public void charging(int TID, String destX, String destY){
        Database db = Database.getInstance();
        db.open();
        Trottinette trotti = null;
        HashMap<String, String> hmap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            trotti = db.getTrottinette(TID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(trotti.getState().equals("libre")){
            //Get the current date
            Date date = new Date();

            hmap.put(" startTime", formatter.format(date));
            hmap.put(" endTime", "0000-00-00 00:00:00");
            hmap.put("scooter", Integer.toString(TID));
            hmap.put(" user",main.getUsername());
            hmap.put(" initialLoad", Integer.toString(trotti.getBattery()));
            hmap.put(" finalLoad", "0");
            hmap.put(" sourceX", Double.toString(trotti.getPosX()));
            hmap.put(" sourceY", Double.toString(trotti.getPosY()));
            hmap.put(" destinationX", "0");
            hmap.put(" destinationY", "0");

            try {
                db.insertRecharge(hmap);
                db.stateUpdate("en charge", TID);
                db.close();
            } catch (SQLException e) {
                AlertMessage.alert("Impossible de charger cette trottinette !");
            } catch (ParseException e) {
            }
        }
        else if(trotti.getState().equals("en charge")){
            //Get the current date
            Date date = new Date();

            try {
                db.endCharge(
                        TID,
                        Integer.parseInt(main.getUsername()),
                        formatter.format(date),
                        Double.parseDouble(destX),
                        Double.parseDouble(destY));
                db.stateUpdate("libre",TID);
                db.close();
            } catch (SQLException e) {
                AlertMessage.alert("Impossible de finir de charger cette trottinette !");
            } catch (ParseException e) {
            }
        }
    }

    /**
     *
     * @param main Set the main to access info
     */
    public void setMain(Main main){
        this.main = main;
    }
}
