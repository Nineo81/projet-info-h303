package controller;

public class UserPage {

    private Main main;

    public void upgradeUser(int username, int password, long account){
        main.openUser(username, password, account);
    }

    public void setMain(Main main){
        this.main = main;
    }
}
