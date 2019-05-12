package controller;

import model.Database;
import model.Trottinette;
import util.AlertMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * QueriesPage link buttons action from QueriesController to database access
 */
public class QueriesPage {

    /**
     * Ask database for result of queries R1
     * @return List of trottinette
     */
    public ArrayList<String> R1(){
        Database db = Database.getInstance();
        ArrayList<Trottinette> res = null;
        ArrayList<String> trottiList = new ArrayList<>();
        db.open();

        try {
            res = db.getTrottinettesDispo();
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'obtenir la liste de trottinettes !");
        }

        for(Trottinette trotti : res){
            trottiList.add(Integer.toString(trotti.getTID()));
        }

        return trottiList;
    }

    /**
     * Ask database for result of queries R2
     * @return List of users
     */
    public ArrayList<Integer> R2(){
        Database db = Database.getInstance();
        ArrayList<Integer> res = null;
        db.open();

        try {
            res = db.getR2();
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'obtenir la liste d'utilisateurs !");
        }

        return res;
    }

    /**
     * Ask database for result of queries R3
     * @return Trottinette ID
     */
    public int R3(){
        Database db = Database.getInstance();
        int res = 0;
        db.open();

        try {
            res = db.getTravolder();
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'obtenir la trottinette !");
        }

        return res;
    }

    /**
     * Ask database for result of queries R4
     * @return list of trottinette
     */
    public ArrayList<Integer> R4(){
        Database db = Database.getInstance();
        ArrayList<Integer> res = null;
        db.open();

        try {
            res = db.tenComplaints();
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'obtenir la liste de trottinettes !");
        }

        return res;
    }

    /**
     * Ask database for result of queries R5
     * @return list of users
     */
    public ArrayList<HashMap<String, String>> R5(){
        Database db = Database.getInstance();
        ArrayList<HashMap<String, String>> res = null;
        db.open();

        try {
            res = db.getR5();
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Impossible d'obtenir la liste d'utilisateurs !");
        }

        return res;
    }
}
