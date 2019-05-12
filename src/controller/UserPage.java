package controller;

/**
 * UserPage link buttons action from UserController to window access
 */
public class UserPage {

    private Main main;

    /**
     * Instanciate a new registration of a reloader
     * @param username ID of user
     * @param password password of user
     * @param account bankAccount of user
     */
    public void upgradeUser(int username, int password, long account){
        main.openUser(username, password, account);
    }

    /**
     *
     * @param main Set the main to access window
     */
    public void setMain(Main main){
        this.main = main;
    }
}
