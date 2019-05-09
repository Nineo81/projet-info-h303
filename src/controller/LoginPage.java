package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Database;
import model.Trottinette;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

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
                main.setUsername(result[0]);
                main.openMainWindow(trottiList(db));
            } else if(result.equals("rechargeur")){
                main.setUsername(result[0]);
                main.openMainWindow(FXCollections.observableArrayList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Trottinette> trottiList(Database db){
        ObservableList<Trottinette> list = FXCollections.observableArrayList();

        try {
            for(HashMap<String, Integer> trotti : db.getTrottinettesDispo()){
                list.add(new Trottinette(trotti.get("TID"),0,0,0,0,0,trotti.get("POSITIONX"),trotti.get("POSITIONY")));
            }
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
