package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Database {
    private String CONNECTION_URL = "jdbc:derby:" + "Database" + ";create=false";
    private Connection conn = null;
    private static Database instance = null;

    /***
     * Private constructor to prevent multiple instantiation
     */
    private Database(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            this.conn = DriverManager.getConnection(CONNECTION_URL);
        }
        catch(Throwable e)  {
            e.printStackTrace();
        }
    }

    /**
     * Open a connection to Derby
     */
    public void open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_URL);
        }
        catch(SQLException e) {
            e.printStackTrace();
            //TO DO : gérer l'exception
        }
    }

    /***
     * Return the only instance of the DAO (it's a singleton).
     * The synchronized is mandatory because of the multi-threads coming from JavaFX.
     * @return instance of DAO
     */
    public synchronized static Database getInstance(){
        if ( instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void insertUtilisateur(HashMap<String,String> Utilisateur) throws SQLException {
        String query = "INSERT INTO UTILISATEUR (" +
                       "UID, MOTDEPASSE, NUMCARTE)" +
                       "values(?,?,?)";
        PreparedStatement statement = this.conn.prepareStatement(query);
        statement.setInt(1,Integer.parseInt(Utilisateur.get("ID")));
        statement.setInt(2,Integer.parseInt(Utilisateur.get("password")));
        statement.setInt(3,Integer.parseInt(Utilisateur.get("bankaccount")));
        statement.execute();
    }

    public void insertRechargeur(HashMap<String,String> rechargeur) throws SQLException {
        //Adding Rechargeur id, password and bankaccount to UTILISATEUR
        String userQuery = "INSERT INTO UTILISATEUR (" +
                       "UID, MOTDEPASSE, NUMCARTE)" +
                       "values(?,?,?)";
        //Adding Rechargeur other info to RECHARGEUR
        String regiQuery = "INSERT INTO RECHARGEUR (" +
                           "UID, NOM, PRENOM, NUMTEL, " +
                           "COMMUNE, CODEPOSTAL, RUE, NUMERO)" +
                           "values(?,?,?,?,?,?,?,?)";
        PreparedStatement userStatement = this.conn.prepareStatement(userQuery);
        userStatement.setInt(1,Integer.parseInt(rechargeur.get("ID")));
        userStatement.setInt(2,Integer.parseInt(rechargeur.get("password")));
        userStatement.setInt(3,Integer.parseInt(rechargeur.get("bankaccount")));

        PreparedStatement regiStatement = this.conn.prepareStatement(regiQuery);
        regiStatement.setString(1,rechargeur.get("ID"));
        regiStatement.setString(2,rechargeur.get("lastname"));
        regiStatement.setString(3,rechargeur.get("firstname"));
        regiStatement.setInt(4,Integer.parseInt(rechargeur.get("phone")));
        regiStatement.setString(5,rechargeur.get("city"));
        regiStatement.setInt(6,Integer.parseInt(rechargeur.get("cp")));
        regiStatement.setString(7,rechargeur.get("street"));
        regiStatement.setInt(8,Integer.parseInt(rechargeur.get("number")));

        userStatement.execute();
        regiStatement.execute();
    }
}
