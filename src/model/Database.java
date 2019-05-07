package model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        statement.setString(3,Utilisateur.get("bankaccount"));
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
        Date date = formatter.parse(Technicien.get("hireDate"));
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());

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
        statement.setTimestamp(10, dateSQL);
        statement.setLong(11, Long.parseLong(Technicien.get("bankaccount")));
        statement.execute();

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

    public void injectTrottinette(HashMap<String, String> hmap) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse(hmap.get(" mise en service"));
        java.sql.Timestamp sqlDate = new Timestamp(date.getTime());
        PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
                "TROTTINETTE(TID, DATESERVICE, MODELE, PLAINTE, BATTERIE" +
                ", DISPONIBLE, POSITIONX, POSITIONY)values(?,?,?,?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(hmap.get("numero")));
        statement.setTimestamp(2, sqlDate);
        statement.setString(3, hmap.get(" modele"));
        statement.setInt(4, Integer.parseInt(hmap.get(" plainte")));
        statement.setInt(5, Integer.parseInt(hmap.get(" charge")));
        statement.setInt(6, Integer.parseInt("0"));
        statement.setDouble(7, Double.parseDouble("0"));
        statement.setDouble(8, Double.parseDouble("0"));
        statement.execute();
    }

    public void insertTrajet(HashMap<String, String> Trajet) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse(Trajet.get(" starttime"));
        java.sql.Timestamp start = new java.sql.Timestamp(date.getTime());

        date = formatter.parse(Trajet.get(" endtime"));
        java.sql.Timestamp end = new java.sql.Timestamp(date.getTime());

        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO " +
                "TRAJET(TID, UID, SOURCEX, SOURCEY, DESTX, DESTY, DATEDEPART, DATEFIN)"+
                "values(?,?,?,?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(Trajet.get("scooter")));
        statement.setInt(2, Integer.parseInt(Trajet.get(" user")));
        statement.setDouble(3, Double.parseDouble(Trajet.get(" sourceX")));
        statement.setDouble(4, Double.parseDouble(Trajet.get(" sourceY")));
        statement.setDouble(5, Double.parseDouble(Trajet.get(" destinationX")));
        statement.setDouble(6, Double.parseDouble(Trajet.get(" destinationY")));
        statement.setTimestamp(7, start);
        statement.setTimestamp(8, end);
        statement.execute();

    }

    public void insertRecharge(HashMap<String, String> Recharge) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse(Recharge.get(" startTime"));
        java.sql.Timestamp start = new java.sql.Timestamp(date.getTime());

        date = formatter.parse(Recharge.get(" endTime"));
        java.sql.Timestamp end = new java.sql.Timestamp(date.getTime());

        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO " +
                "RECHARGE(TID, UID, CHARGEI, CHARGEF, SOURCEX, SOURCEY, DESTX, DESTY, DATEDEPART, DATEFIN)"+
                "values(?,?,?,?,?,?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(Recharge.get("scooter")));
        statement.setInt(2, Integer.parseInt(Recharge.get(" user")));
        statement.setInt(3, Integer.parseInt(Recharge.get(" initialLoad")));
        statement.setInt(4, Integer.parseInt(Recharge.get(" finalLoad")));
        statement.setDouble(5, Double.parseDouble(Recharge.get(" sourceX")));
        statement.setDouble(6, Double.parseDouble(Recharge.get(" sourceY")));
        statement.setDouble(7, Double.parseDouble(Recharge.get(" destinationX")));
        statement.setDouble(8, Double.parseDouble(Recharge.get(" destinationY")));
        statement.setTimestamp(9, start);
        statement.setTimestamp(10, end);
        statement.execute();

    }

    public void insertIntervention(HashMap<String, String> Intervention) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse(Intervention.get(" complainTime"));
        java.sql.Timestamp complain = new java.sql.Timestamp(date.getTime());

        date = formatter.parse(Intervention.get(" repaireTime"));
        java.sql.Timestamp repaire = new java.sql.Timestamp(date.getTime());

        String TechID = Intervention.get(" mechanic");
        String zero = "0";
        while (TechID.getBytes().length < 20){
            TechID = zero + TechID;
        }

        PreparedStatement statement = this.conn.prepareStatement("INSERT INTO " +
                "INTERVENTION(TID, UID, TECHID, DATEPLAINTE, DATEINTERVENTION)"+
                "values(?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(Intervention.get("scooter")));
        statement.setInt(2, Integer.parseInt(Intervention.get(" user")));
        statement.setString(3, TechID);
        statement.setTimestamp(4, complain);
        statement.setTimestamp(5, repaire);
        statement.execute();

    }

    public void init() throws SQLException, ParseException {
        ArrayList<HashMap<String,String>> reloads = CSVParser.parsing("Database_Data/reloads.csv");
        ArrayList<HashMap<String,String>> reparations = CSVParser.parsing("Database_Data/reparations.csv");
        ArrayList<HashMap<String,String>> scooters = CSVParser.parsing("Database_Data/scooters.csv");
        ArrayList<HashMap<String,String>> trips = CSVParser.parsing("Database_Data/trips.csv");
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
        for(HashMap<String, String> hmap : trips){
            insertTrajet(hmap);
        }
        for(HashMap<String, String> hmap : reloads){
            insertRecharge(hmap);
        }
        for(HashMap<String, String> hmap : reparations){
           insertIntervention(hmap);
        }
    }
}
