package controller;

import model.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class QueriesPage {
    private Main main;

    public ArrayList<Integer> R2(){
        Database db = Database.getInstance();
        ArrayList<Integer> res = null;
        db.open();

        try {
            res = db.getR2();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public int R3(){
        Database db = Database.getInstance();
        int res = 0;
        db.open();

        try {
            res = db.getTravolder();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ArrayList<Integer> R4(){
        Database db = Database.getInstance();
        ArrayList<Integer> res = null;
        db.open();

        try {
            res = db.tenComplaints();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public ArrayList<HashMap<String, String>> R5(){
        Database db = Database.getInstance();
        ArrayList<HashMap<String, String>> res = null;
        db.open();

        try {
            res = db.getR5();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
