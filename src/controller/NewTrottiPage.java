package controller;

import model.Database;
import util.AlertMessage;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * NewTrottiPage link buttons action from NewTrottiController to database access
 */
public class NewTrottiPage {

    /**
     * Ask database to add a new trottinette with given information
     * @param TID ID of the trottinette
     * @param model Model of the trottinette
     * @param posX Position X of the trottinette
     * @param posY Position Y of the trottinette
     */
    public void addTrotti(int TID, String model, Double posX, Double posY){
        HashMap<String, String> hmap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Database db = Database.getInstance();

        hmap.put(" mise en service", formatter.format(date));
        hmap.put("numero", Integer.toString(TID));
        hmap.put(" modele", model);
        hmap.put(" plainte", "0");
        hmap.put(" charge", "4");
        hmap.put("posX", Double.toString(posX));
        hmap.put("posY", Double.toString(posY));

        db.open();

        try {
            db.insertTrottinette(hmap);
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'ajouter la trottinette !");
        } catch (ParseException e) {
        }
    }
}
