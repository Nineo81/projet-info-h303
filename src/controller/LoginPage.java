package controller;

import javafx.collections.FXCollections;

import java.io.IOException;

public class LoginPage {

    private Main main;

    /**
     * Handle the login action
     */
    public void login(String username, String password) {
        main.openMainWindow(FXCollections.observableArrayList());
    }

    /**
     * Handle the register action
     */
    public void register(String username, String password){

    }

    public void setMain(Main main){
        this.main = main;
    }
}
