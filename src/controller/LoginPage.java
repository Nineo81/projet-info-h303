package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Trottinette;
import util.AlertMessage;
import java.sql.SQLException;

/**
 * LoginPage link buttons action from LoginController to database access
 */
public class LoginPage {

    private Main main;

    /**
     * Login into the application, check with the database the existence and type of user trying to connect
     * @param username ID of the user
     * @param password Password of the user
     */
    public void login(String username, String password) {
        Database db = Database.getInstance();
        db.open();

        try {
            String[] result = db.checkUser(Integer.parseInt(username),Integer.parseInt(password));
            if(result[1].equals("user")){
                main.setUserType("user");
                main.setUsername(result[0]);
                main.openMainWindow(trottiList(db));
            } else if(result[1].equals("rechargeur")){
                main.setUserType("rechargeur");
                main.setUsername(result[0]);
                main.openMainWindow(trottiListRechargeur(db));
            }
            db.close();
        } catch (SQLException e) {
            AlertMessage.alert("Mauvais mot de passe ou utilisateur !");
        } catch (NumberFormatException e){
            try {
                String res = db.checkTech(username, Integer.parseInt(password));
                if(res.equals("none")){
                    AlertMessage.alert("Mauvais mot de passe ou utilisateur !");
                } else {
                    main.setUserType("technicien");
                    main.setUsername(res);
                    main.openMainWindow(trottiListTechnicien(db,username));
                }
                db.close();
            } catch (SQLException e1) {
                AlertMessage.alert("Mauvais mot de passe ou utilisateur !");
            }
        }
    }

    /**
     * Access database to get all trotinette free for the user
     * @param db Database
     * @return List of the trottinette
     */
    private ObservableList<Trottinette> trottiList(Database db){
        ObservableList<Trottinette> list = FXCollections.observableArrayList();

        try {
            list.addAll(db.getTrottinettesDispo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Access database to get all trotinette
     * @param db Database
     * @param username ID of the technicien
     * @return List of the trottinette
     */
    private ObservableList<Trottinette> trottiListTechnicien(Database db,String username){
        ObservableList<Trottinette> list = FXCollections.observableArrayList();

        try {
            list.addAll(db.getTrottinettes(username));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Access database to get all trotinette free for the reloader
     * @param db Database
     * @return List of the trottinette
     */
    private ObservableList<Trottinette> trottiListRechargeur(Database db){
        ObservableList<Trottinette> list = FXCollections.observableArrayList();

        try {
            list.addAll(db.getTrottinettesRechargeur(Integer.parseInt(main.getUsername())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Handle the register action
     */
    public void register(String username, String password){
        main.openRegister();
    }

    /**
     *
     * @param main Set the main to access info and window
     */
    public void setMain(Main main){
        this.main = main;
    }
}
