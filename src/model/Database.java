package model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

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
        statement.setString(3,Utilisateur.get("bankaccount"));
        statement.execute();
        statement.close();
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
        userStatement.setInt(1, Integer.parseInt(rechargeur.get("ID")));
        userStatement.setInt(2, Integer.parseInt(rechargeur.get("password")));
        userStatement.setString(3, rechargeur.get("bankaccount"));

        PreparedStatement regiStatement = this.conn.prepareStatement(regiQuery);
        regiStatement.setInt(1, Integer.parseInt(rechargeur.get("ID")));
        regiStatement.setString(2, rechargeur.get("lastname"));
        regiStatement.setString(3, rechargeur.get("firstname"));
        regiStatement.setInt(4, Integer.parseInt(rechargeur.get("phone")));
        regiStatement.setString(5, rechargeur.get("city"));
        regiStatement.setInt(6, Integer.parseInt(rechargeur.get("cp")));
        regiStatement.setString(7, rechargeur.get("street"));
        regiStatement.setInt(8, Integer.parseInt(rechargeur.get("number")));

        userStatement.execute();
        regiStatement.execute();
    }

    public void insertTechnicien(HashMap<String, String> Technicien) throws SQLException, ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO " +
                "TECHNICIEN(techid, nom, prenom, motdepasse, numtel, commune, codepostal, rue, numero, dateembauche, compte)"+
                "values(?,?,?,?,?,?,?,?,?,?,?)");
        statement.setString(1, Technicien.get("mechanicID"));
        statement.setString(2, Technicien.get("lastname"));
        statement.setString(3, Technicien.get("firstname"));
        statement.setInt(4, Integer.parseInt(Technicien.get("password")));
        statement.setInt(5, Integer.parseInt(Technicien.get("phone")));
        statement.setString(6, Technicien.get("city"));
        statement.setInt(7, Integer.parseInt(Technicien.get("cp")));
        statement.setString(8, Technicien.get("street"));
        statement.setInt(9, Integer.parseInt(Technicien.get("number")));
        Date date = formatter.parse(Technicien.get("hireDate"));
        java.sql.Date dateSQL = new java.sql.Date(date.getTime());
        statement.setDate(10, dateSQL);
        statement.setLong(11, Long.parseLong(Technicien.get("bankaccount")));
        statement.execute();

    }

    public void injectTrottinette(HashMap<String, String> hmap) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse(hmap.get(" mise en service"));
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
                "TROTTINETTE(TID, DATESERVICE, MODELE, PLAINTE, BATTERIE" +
                ", POSITIONX, POSITIONY, STATE)values(?,?,?,?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(hmap.get("numero")));
        statement.setDate(2, sqlDate);
        statement.setString(3, hmap.get(" modele"));
        statement.setInt(4, Integer.parseInt(hmap.get(" plainte")));
        statement.setInt(5, Integer.parseInt(hmap.get(" charge")));
        statement.setDouble(6, Double.parseDouble("0"));
        statement.setDouble(7, Double.parseDouble("0"));
        statement.setString(8, "libre");
        statement.execute();
        statement.close();
    }

    public ArrayList<Trottinette> getTrottinette() throws SQLException {
        ArrayList<Trottinette> trotis = new ArrayList<Trottinette>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM TROTTINETTE");
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            trotis.add(new Trottinette(res.getInt("TID"), res.getInt("DATESERVICE"), res.getInt("MODELE"), res.getInt("PLAINTE"), res.getInt("BATTERIE"), res.getInt("DISPONIBLE"), res.getInt("POSITIONX"), res.getInt("POSITIONY")));
        }
        return trotis;
    }

    public void deleteTrottinette(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM TROTTINETTE WHERE TID = " + TID);
        statement.execute();
        statement.close();
    }

    public ArrayList<HashMap<String, Integer>> getTrottinettesDispo() throws SQLException {
        ArrayList<HashMap<String, Integer>> trottinettes = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT TID, POSITIONX" +
                ", POSITIONY FROM TROTTINETTE WHERE STATE = libre");
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            HashMap<String, Integer> hmap = new HashMap<>();
            hmap.put("TID", res.getInt("TID"));
            hmap.put("POSITIONX", res.getInt("POSITIONX"));
            hmap.put("POSITIONY", res.getInt("POSITIONY"));
            trottinettes.add(hmap);
        }
        res.close();
        return trottinettes;
    }

    public int getBattery(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT BATTERIE FROM TROTTINETTE WHERE TID = " + TID);
        ResultSet res = statement.executeQuery();
        res.next();
        int charge = res.getInt("BATTERIE");
        res.close();
        return charge;
    }

    public void introduceComplain(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE TROTTINETTE SET PLAINTE = PLAINTE + 1 WHERE TID = " + TID);
        statement.execute();
        statement.close();
    }

    public void clearComplain(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE TROTTINETTE SET PLAINTE = 0 WHERE TID = " + TID);
        statement.execute();
        statement.close();
    }

    public void stateUpdate(String newState, int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE TROTTINETTE SET STATE = " + newState + " WHERE TID = " + TID);
        statement.execute();
        statement.close();
    }

    public ArrayList<Integer> tenComplaints() throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT TID FROM INTERVENTION GROUP BY TID HAVING COUNT(TID)>9");
        ResultSet res = statement.executeQuery();
        while(res.next()){
            list.add(res.getInt("TID"));
        }
        res.close();
        return list;
    }

    public boolean checkUser(int UID, int password) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT UID, MOTDEPASSE FROM UTILISATEUR WHERE UID = ?");
        statement.setInt(1, UID);
        ResultSet res = statement.executeQuery();
        res.next();
        if (res.getInt("MOTDEPASSE") == password) {
            res.close();
            return true;
        } else {
            res.close();
            return false;
        }
    }

    // Function used to fill the database if empty
    public void init() throws SQLException, ParseException {
        //ArrayList<HashMap<String,String>> reloads = CSVParser.parsing("Database_Data/reloads.csv");
        ArrayList<HashMap<String,String>> reparations = CSVParser.parsing("Database_Data/reparations.csv");
        ArrayList<HashMap<String,String>> scooters = CSVParser.parsing("Database_Data/scooters.csv");
        //ArrayList<HashMap<String,String>> trips = CSVParser.parsing("Database_Data/trips.csv");
        ArrayList<HashMap<String,String>> anonymous = XmlParserAnonymous.parse("Database_Data/anonyme_users.xml");
        ArrayList<HashMap<String,String>> mecaniciens = XmlParserMech.parse("Database_Data/mecaniciens.xml");
        ArrayList<HashMap<String,String>> registeredUsers = XmlParserRegistered.parse("Database_Data/registeredUsers.xml");

        for(HashMap<String, String> hmap : scooters){
            injectTrottinette(hmap);
        }
        for(HashMap<String, String> hmap : anonymous){
            insertUtilisateur(hmap);
        }
        for(HashMap<String, String> hmap : mecaniciens){
            insertTechnicien(hmap);
        }
        for(HashMap<String, String> hmap : registeredUsers){
            insertRechargeur(hmap);
        }
    }
}