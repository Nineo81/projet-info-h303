package controller;

import javafx.collections.FXCollections;
import model.Database;

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
                main.setUsername(result[0]);
                main.openMainWindow(FXCollections.observableArrayList());
            } else if(result.equals("rechargeur")){
                main.setUsername(result[0]);
                main.openMainWindow(FXCollections.observableArrayList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
