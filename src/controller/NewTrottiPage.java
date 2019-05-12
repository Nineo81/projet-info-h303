package controller;

import model.Database;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewTrottiPage {

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
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        Main main1 = main;
    }
}
