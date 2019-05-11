package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Trottinette;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPage {

    private Main main;

    /**
     * Handle the login action
     */
    public void login(String username, String password) throws IOException {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Trottinette> trottiList(Database db){
        ObservableList<Trottinette> list = FXCollections.observableArrayList();

        try {
            list.addAll(db.getTrottinettesDispo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

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

    public void setMain(Main main){
        this.main = main;
    }
}
