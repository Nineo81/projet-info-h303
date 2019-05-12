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
                       "UID, MOTDEPASSE, COMPTE)" +
                       "values(?,?,?)";
        PreparedStatement statement = this.conn.prepareStatement(query);
        statement.setInt(1,Integer.parseInt(Utilisateur.get("ID")));
        statement.setInt(2,Integer.parseInt(Utilisateur.get("password")));
        statement.setLong(3,Long.parseLong(Utilisateur.get("bankaccount")));
        statement.execute();
        statement.close();
    }

    public void insertRechargeur(HashMap<String,String> rechargeur) throws SQLException {
        //Adding Rechargeur id, password and bankaccount to UTILISATEUR
        String userQuery = "INSERT INTO UTILISATEUR (" +
                "UID, MOTDEPASSE, COMPTE)" +
                "values(?,?,?)";
        //Adding Rechargeur other info to RECHARGEUR
        String regiQuery = "INSERT INTO RECHARGEUR (" +
                "UID, NOM, PRENOM, NUMTEL, " +
                "COMMUNE, CODEPOSTAL, RUE, NUMERO)" +
                "values(?,?,?,?,?,?,?,?)";
        PreparedStatement userStatement = this.conn.prepareStatement(userQuery);
        userStatement.setInt(1, Integer.parseInt(rechargeur.get("ID")));
        userStatement.setInt(2, Integer.parseInt(rechargeur.get("password")));
        userStatement.setLong(3, Long.parseLong(rechargeur.get("bankaccount")));

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
        statement.close();
    }

    public void insertTrottinette(HashMap<String, String> hmap) throws SQLException, ParseException {
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
        if(hmap.get("posX") == null){
            statement.setDouble(6, Double.parseDouble("0"));
        }else {
            statement.setDouble(6, Double.parseDouble(hmap.get("posX")));
        }
        if(hmap.get("posY")==null){
            statement.setDouble(7, Double.parseDouble("0"));
        }else {
            statement.setDouble(7, Double.parseDouble(hmap.get("posY")));
        }
        statement.setString(8, "libre");
        statement.execute();
        statement.close();
    }

    public Trottinette getTrottinette(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM TROTTINETTE WHERE TROTTINETTE.TID = ?");
        statement.setInt(1, TID);
        ResultSet res = statement.executeQuery();
        res.next();

        return new Trottinette(TID,
                String.valueOf(res.getTimestamp("DATESERVICE")),
                res.getString("MODELE"),
                res.getInt("PLAINTE"),
                res.getInt("BATTERIE"),
                res.getString("ETAT"),
                res.getDouble("POSITIONX"),
                res.getDouble("POSITIONY"));
    }

    public ArrayList<Trottinette> getTrottinettes() throws SQLException {
        ArrayList<Trottinette> trotis = new ArrayList<Trottinette>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM TROTTINETTE");
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            trotis.add(new Trottinette(
                    res.getInt("TID"),
                    String.valueOf(res.getTimestamp("DATESERVICE")),
                    res.getString("MODELE"),
                    res.getInt("PLAINTE"),
                    res.getInt("BATTERIE"),
                    res.getString("ETAT"),
                    res.getDouble("POSITIONX"),
                    res.getDouble("POSITIONY")));
        }
        res.close();
        statement.close();
        return trotis;
    }

    public void deleteTrottinette(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM TROTTINETTE WHERE TID = ?");
        statement.setInt(1,TID);
        statement.execute();
        statement.close();
    }

    public ArrayList<Trottinette> getTrottinettesDispo() throws SQLException {
        ArrayList<Trottinette> trottinettes = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT TID, POSITIONX" +
                ", POSITIONY FROM TROTTINETTE WHERE ETAT = 'libre' OR ETAT = 'defectueuse'");
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            trottinettes.add(new Trottinette(
                    res.getInt("TID"),
                    "0",
                    "0",
                    0,
                    0,
                    "libre",
                    res.getDouble("POSITIONX"),
                    res.getDouble("POSITIONY")));
        }
        res.close();
        return trottinettes;
    }

    public ArrayList<Trottinette> getTrottinettesRechargeur(int UID) throws SQLException {
        ArrayList<Trottinette> trottinettes = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement(
                "SELECT TID, POSITIONX, POSITIONY, ETAT " +
                "FROM TROTTINETTE " +
                "WHERE ETAT = 'libre' " +
                "UNION " +
                "SELECT TID, POSITIONX,POSITIONY, ETAT " +
                "FROM TROTTINETTE " +
                "WHERE ETAT = 'en charge' AND " +
                        "EXISTS(SELECT * FROM RECHARGE WHERE RECHARGE.UID = ?)");
        statement.setInt(1, UID);
        ResultSet res = statement.executeQuery();
        while(res.next()) {
            trottinettes.add(new Trottinette(
                    res.getInt("TID"),
                    "0",
                    "0",
                    0,
                    0,
                    res.getString("ETAT"),
                    res.getDouble("POSITIONX"),
                    res.getDouble("POSITIONY")));
        }
        res.close();
        return trottinettes;
    }

    public int getBattery(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT BATTERIE FROM TROTTINETTE WHERE TID = ?");
        statement.setInt(1, TID);
        ResultSet res = statement.executeQuery();
        res.next();
        int charge = res.getInt("BATTERIE");
        res.close();
        return charge;
    }

    public void introduceComplain(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE TROTTINETTE SET PLAINTE = PLAINTE + 1, ETAT = 'defecteuse' WHERE TID = ?");
        statement.setInt(1, TID);
        statement.execute();
        statement.close();
    }

    public void clearComplain(int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE TROTTINETTE SET PLAINTE = 0 WHERE TID = ?");
        statement.setInt(1, TID);
        statement.execute();
        statement.close();
    }

    public void stateUpdate(String newState, int TID) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE TROTTINETTE SET ETAT = ? WHERE TID = ?");
        statement.setString(1, newState);
        statement.setInt(2, TID);
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
        statement.close();
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
            statement.close();
            return new String[]{String.valueOf(UID),"user"};
        }
        else if(res.getString("ISARECHARGEUR") == "yes" && res.getInt("MOTDEPASSE") == password){
            res.close();
            statement.close();
            return new String[]{Integer.toString(UID),"rechargeur"};
        }else {
            res.close();
            statement.close();
            return new String[]{Integer.toString(UID),"none"};
        }
    }

    public String checkTech(String TID, int password) throws SQLException {
        String query = "SELECT TECHID, MOTDEPASSE " +
                       "FROM TECHNICIEN " +
                       "WHERE TECHID = ?";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, TID);
        ResultSet res = statement.executeQuery();
        res.next();

        if(res.getInt("MOTDEPASSE") == password){
            res.close();
            statement.close();
            return TID;
        }
        else{
            res.close();
            statement.close();
            return "none";
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
        statement.close();
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
        statement.close();
    }

    public void endCharge(int TID, int UID, String endTime, Double destX, Double destY) throws SQLException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(endTime);
        Timestamp end = new Timestamp(date.getTime());

        PreparedStatement statement = this.conn.prepareStatement(
                "UPDATE RECHARGE " +
                        "SET DATEFIN = ?, " +
                        "CHARGEF = 4, " +
                        "DESTX = ?, " +
                        "DESTY = ? " +
                        "WHERE UID = ? AND TID = ?");
        statement.setTimestamp(1,end);
        statement.setDouble(2,destX);
        statement.setDouble(3,destY);
        statement.setInt(4,UID);
        statement.setInt(5,TID);
        statement.execute();
        statement.close();
    }

    public void resolveIntervention(int TID, String note) throws SQLException {
        Date date = new Date();
        Timestamp repair = new Timestamp(date.getTime());
        String query = "UPDATE INTERVENTION " +
                "SET DATEINTERVENTION = ?, NOTE = ?, ENSERVICE = 1 " +
                "WHERE TID = ? AND ENSERVICE = 0";

        PreparedStatement statement = this.conn.prepareStatement(query);
        statement.setTimestamp(1,repair);
        statement.setString(2,note);
        statement.setInt(3,TID);
        statement.execute();
        statement.close();
    }

    public void addingIntervention(int TID, String UID) throws SQLException, ParseException {
        Date date = new Date();
        Timestamp complainTime = new Timestamp(date.getTime());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.parse("0000-00-00 00:00:00");
        Timestamp interventionTime = new Timestamp(date.getTime());

        String query = "INSERT INTO " +
                "INTERVENTION(TECHID, TID, UID, DATEPLAINTE, DATEINTERVENTION, ENSERVICE)"+
                "VALUES(" +
                "CASE " +
                    "WHEN EXISTS(SELECT * FROM INTERVENTION WHERE ENSERVICE = 0 AND TID = ?) " +
                        "THEN (SELECT TECHID FROM INTERVENTION WHERE ENSERVICE = 0 AND TID = ?) " +
                "ELSE (SELECT TECHID FROM TECHNICIEN ORDER BY RANDOM() FETCH first 1 rows only) END," +
                "?,?,?,?,?)";

        PreparedStatement statement = this.conn.prepareStatement(query);
        statement.setInt(1,TID);
        statement.setInt(2,TID);
        statement.setInt(3,TID);
        statement.setInt(4,Integer.parseInt(UID));
        statement.setTimestamp(5, complainTime);
        statement.setTimestamp(6, interventionTime);
        statement.setInt(7,0);
        statement.execute();
        statement.close();
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
                "INTERVENTION(TID, UID, TECHID, DATEPLAINTE, DATEINTERVENTION, ENSERVICE)"+
                "values(?,?,?,?,?,?)");
        statement.setInt(1, Integer.parseInt(Intervention.get("scooter")));
        statement.setInt(2, Integer.parseInt(Intervention.get(" user")));
        statement.setString(3, TechID);
        statement.setTimestamp(4, complain);
        statement.setTimestamp(5, repaire);
        if(Intervention.get("enService")==null){
            statement.setInt(6, 1);
        } else{
            statement.setInt(6, Integer.parseInt(Intervention.get("enService")));
        }
        statement.execute();
        statement.close();
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
        statement.close();
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
            insertTrottinette(hmap);
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

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM UTILISATEUR U " +
                "WHERE U.UID NOT IN (SELECT UID FROM RECHARGEUR)";

        PreparedStatement statement = this.conn.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            userList.add(new User(res.getInt("UID"),
                    res.getInt("MOTDEPASSE"),
                    res.getLong("COMPTE")));
        }
        res.close();
        statement.close();
        return userList;
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

    public void close() throws SQLException {
        if(this.conn != null && !this.conn.isClosed()){
            this.conn.close();
        }
    }

    public void updatePosition(int TID) throws SQLException {
        System.out.println(TID);
        PreparedStatement statement = conn.prepareStatement(
                "UPDATE TROTTINETTE" +
                " SET POSITIONX = (SELECT x" +
                "                  FROM (SELECT TRAJET.TID as id, TRAJET.DateFin as datef, TRAJET.DestX as x FROM Trajet" +
                "                        UNION" +
                "                        SELECT RECHARGE.TID as id, RECHARGE.DateFin as datef, RECHARGE.DestX as x FROM RECHARGE) as HEHE" +
                "                  WHERE id = ? AND datef = (SELECT MAX(datef)" +
                "                                            FROM (SELECT TRAJET.TID as id, TRAJET.DateFin as datef FROM Trajet" +
                "                                                  UNION" +
                "                                                  SELECT RECHARGE.TID as id, RECHARGE.DateFin as datef FROM RECHARGE) as HAHA" +
                "                                            WHERE id = ?))," +
                "    POSITIONY = (SELECT y" +
                "                FROM (SELECT TRAJET.TID as id, TRAJET.DateFin as datef, TRAJET.DestY as y FROM Trajet" +
                "                      UNION" +
                "                      SELECT RECHARGE.TID as id, RECHARGE.DateFin as datef, RECHARGE.DestY as y FROM RECHARGE) as HEHE" +
                "                WHERE id = ? AND datef = (SELECT MAX(datef)" +
                "                                          FROM (SELECT TRAJET.TID as id, TRAJET.DateFin as datef FROM Trajet" +
                "                                                UNION" +
                "                                                SELECT RECHARGE.TID as id, RECHARGE.DateFin as datef FROM RECHARGE) as HAHA" +
                "                                          WHERE id = ?))" +
                " WHERE TID = ?");
        for(int i = 1; i<6 ; i++) statement.setInt(i, TID);
        statement.execute();
        statement.close();
    }
}