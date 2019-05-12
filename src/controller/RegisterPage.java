package controller;

import model.Database;
import util.AlertMessage;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * RegisterPage link buttons action from RegisterController to database access and window access
 */
public class RegisterPage {

    private Main main;

    /**
     * Cancel the registration and go back to login
     */
    public void cancel(){
        main.openLogin();
    }

    /**
     * Ask databse to add a new user with given info
     * @param newUser Information used to add the new user
     */
    public void register(HashMap<String, String> newUser,boolean upgrade){
        Database db = Database.getInstance();
        db.open();

        if(newUser.containsKey("lastname")){
            try {
                if(upgrade){
                    db.upgradeUser(newUser);
                }else{
                    db.insertRechargeur(newUser);
                }
                db.close();
            } catch (SQLException e) {
                AlertMessage.alert("Impossible d'ajouter le nouvel rechargeur !");
            }
        } else {
            try {
                db.insertUtilisateur(newUser);
                db.close();
            } catch (SQLException e) {
                AlertMessage.alert("Impossible d'ajouter le nouvel utilisateur !");
            }
        }
        main.openLogin();
    }

    /**
     *
     * @param main Set the main to access info and window
     */
    public void setMain(Main main){
        this.main = main;
    }
}
