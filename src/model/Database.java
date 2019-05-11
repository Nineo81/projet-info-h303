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

    public void injectTrottinette(HashMap<String, String> hmap) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = formatter.parse(hmap.get(" mise en service"));
        java.sql.Timestamp sqlDate = new Timestamp(date.getTime());
        PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
                "TROTTINETTE(TID, DATESERVICE, MODELE, PLAINTE, BATTERIE" +
                ", POSITIONX, POSITIONY, ETAT)values(?,?,?,?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(hmap.get("numero")));
        statement.setTimestamp(2, sqlDate);
        statement.setString(3, hmap.get(" modele"));
        statement.setInt(4, Integer.parseInt(hmap.get(" plainte")));
        statement.setInt(5, Integer.parseInt(hmap.get(" charge")));
        statement.setDouble(6, Double.parseDouble("0"));
        statement.setDouble(7, Double.parseDouble("0"));
        statement.setString(8, "libre");
        statement.execute();
        statement.close();
    }

    public Trottinette getTrottinette(int tID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM TROTTINETTE WHERE TROTTINETTE.TID = ?");
        statement.setInt(1, tID);
        ResultSet res = statement.executeQuery();
        res.next();

        return new Trottinette(tID,
                String.valueOf(res.getTimestamp("DATESERVICE")),
                res.getString("MODELE"),
                res.getInt("PLAINTE"),
                res.getInt("BATTERIE"),
                res.getString("ETAT"),
                res.getInt("POSITIONX"),
                res.getInt("POSITIONY"));
    }

    public ArrayList<Trottinette> getTrottinettes() throws SQLException {
        ArrayList<Trottinette> trotis = new ArrayList<Trottinette>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM TROTTINETTE");
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            trotis.add(new Trottinette(
                    res.getInt("TID"),
                    String.valueOf(res.getInt("DATESERVICE")),
                    res.getString("MODELE"),
                    res.getInt("PLAINTE"),
                    res.getInt("BATTERIE"),
                    res.getString("ETAT"),
                    res.getInt("POSITIONX"),
                    res.getInt("POSITIONY")));
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
                ", POSITIONY FROM TROTTINETTE WHERE ETAT = 'libre'");
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

    public String[] checkUser(int UID, int password) throws SQLException {
        String query = "SELECT UID, MOTDEPASSE, " +
                       "CASE " +
                         "WHEN EXISTS (" +
                         "SELECT * FROM RECHARGEUR WHERE UTILISATEUR.UID = RECHARGEUR.UID ) " +
                         "THEN 'yes' ELSE 'no' END AS ISARECHARGEUR "+
                       "FROM UTILISATEUR WHERE UTILISATEUR.UID = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, UID);
        ResultSet res = statement.executeQuery();
        res.next();
        if (res.getString("ISARECHARGEUR") == "no" && res.getInt("MOTDEPASSE") == password) {
            res.close();
            return new String[]{String.valueOf(UID),"user"};
        }
        else if(res.getString("ISARECHARGEUR") == "yes" && res.getInt("MOTDEPASSE") == password){
            res.close();
            return new String[]{Integer.toString(UID),"rechargeur"};
        }else {
            res.close();
            return new String[]{Integer.toString(UID),"none"};
        }
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
        while (TechID.getBytes().length < 20){
            TechID = "0" + TechID;
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

    public int getTravolder() throws SQLException {
        int output;
        PreparedStatement statement = conn.prepareStatement("SELECT TID " +
                "From TRAJET " +
                "GROUP BY TID " +
                "ORDER BY SUM(SQRT((DESTX - SOURCEX)*(DESTX - SOURCEX)+(DESTY - SOURCEY)*(DESTY - SOURCEY))) DESC " +
                "FETCH FIRST ROW ONLY");
        ResultSet res = statement.executeQuery();
        res.next();
        output = res.getInt("TID");
        res.close();
        return output;
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

    public ArrayList<Path> getUserHistory(int ID) throws SQLException {
        ArrayList<Path> trips  = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(
                "SELECT SOURCEX, SOURCEY, DESTX, DESTY, DATEDEPART, DATEFIN " +
                    "FROM TRAJET " +
                    "WHERE UID = ?");
        statement.setInt(1,ID);
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            trips.add(new Path(
                    String.valueOf(res.getDouble("sourceX")),
                    String.valueOf(res.getDouble("sourceY")),
                    String.valueOf(res.getDouble("destX")),
                    String.valueOf(res.getDouble("destY")),
                    String.valueOf(res.getTimestamp("dateDepart")),
                    String.valueOf(res.getTimestamp("dateFin"))));
        }
        statement.close();
        res.close();
        return  trips;
    }

    public ArrayList<Integer> getR2() throws SQLException {
        ArrayList<Integer> users = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(
                "SELECT RECHARGEUR.UID " +                  //Utilisateurs pour lesquels il n'existe pas de trottinettes
                        "FROM RECHARGEUR " +                       //rechargées mais pas utilisées
                        "WHERE UID NOT IN (SELECT RECHARGE.UID" +           //Recharges de trottinettes rechargées mais pas utilisées par un même utilisateur
                        "                  FROM RECHARGE" +
                        "                  WHERE NOT EXISTS(SELECT TRAJET.UID" +
                        "                                  FROM TRAJET" +
                        "                                  WHERE TRAJET.UID = RECHARGE.UID AND TRAJET.TID = RECHARGE.TID))");
        ResultSet res = statement.executeQuery();
        while (res.next()){
            users.add(res.getInt("UID"));
        }
        res.close();
        statement.close();
        return users;
    }

    public ArrayList<HashMap<String,String>> getR5() throws SQLException {
        ArrayList<HashMap<String,String>> users = new ArrayList<>();
        HashMap<String, String> user = new HashMap<>();
        PreparedStatement statement = conn.prepareStatement(
                "SELECT UID, AVG({fn timestampdiff(SQL_TSI_MINUTE , DATEDEPART, DATEFIN )}), COUNT(UID)," +
                        "       SUM(CASE" +
                        "          WHEN {fn timestampdiff(SQL_TSI_MINUTE , DATEDEPART, DATEFIN )} > 1440" +
                        "            THEN 1 + 36 * FLOOR({fn timestampdiff(SQL_TSI_MINUTE , DATEDEPART, DATEFIN )}/1440)" +
                        "          WHEN {fn timestampdiff(SQL_TSI_MINUTE , DATEDEPART, DATEFIN )} > 60" +
                        "            THEN 1 + 6.5 * FLOOR({fn timestampdiff(SQL_TSI_MINUTE , DATEDEPART, DATEFIN )}/60)" +
                        "          ELSE 1 + 0.15 * {fn timestampdiff(SQL_TSI_MINUTE , DATEDEPART, DATEFIN )}" +
                        "        END)" +
                        "from TRAJET " +
                        "GROUP BY UID " +
                        "HAVING COUNT(UID) >= 10");
        ResultSet res = statement.executeQuery();
        while (res.next()){
            user.put("UID", String.valueOf(res.getInt("UID")));
            user.put("averageTime", String.valueOf(res.getInt("2")));
            user.put("tripsCount", String.valueOf(res.getInt("3")));
            user.put("money", String.valueOf(res.getInt("4")));
            users.add(new HashMap<>(user));
        }
        res.close();
        statement.close();
        return users;
    }
}