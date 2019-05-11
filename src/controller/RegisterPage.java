package controller;

import model.Database;

import java.sql.SQLException;
import java.util.HashMap;

public class RegisterPage {

    private Main main;

    public void cancel(){
        main.openLogin();
    }

    public void register(HashMap<String, String> newUser){
        Database db = Database.getInstance();
        db.open();

        if(newUser.containsKey("lastname")){
            try {
                db.insertRechargeur(newUser);
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                db.insertUtilisateur(newUser);
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        main.openLogin();
    }

    public void setMain(Main main){
        this.main = main;
    }
}
