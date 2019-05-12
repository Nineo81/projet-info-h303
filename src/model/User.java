package model;

/**
 * Object used by javaFX to show in table
 */
public class User {

    private int username;
    private int password;
    private long account;

    public User(int username, int password, long account){
        this.username = username;
        this.account = account;
        this.password = password;
    }

    public int getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public long getAccount() {
        return account;
    }
}
