import java.sql.*;
import java.util.ArrayList;

public class SQL {

    // TODO: Extra, add SQL-injection checker (Security)
    // TODO: Extra, Reusable SQL query methods (Java optimization)
    // TODO: Extra, create SQL-user and define rights for the user (Security)
    // TODO: Extra, create SQL-connection pool (Java & SQL-server optimization)

    /* LIST OF METHODS IN THIS CLASS
    *
    * ### SQL GET DATA SECTION ###
    * closeSQLConnection             -> closes the sql connection
    * getAllObjectIDs                -> returns ArrayList with strings
    * getObjectPostString            -> query builder
    * getObjectPostInt               -> query builder
    * getObjectIdBasedOnCoordinate   -> query builder
    * getObjectTypeBasedOnCoordinate -> query builder
    * getObjectType                  -> query builder
    * getObjectX                     -> query builder
    * getObjectY                     -> query builder
    *
    * getRunSQLQuery                 -> returns a value from the database
    *
    * ### SQL SET DATA SECTION ###
    * setRunSQLQuery                 -> inserts a value to the database
    *
    * setObjectColumnString          -> query builder
    * setObjectColumnString          -> query builder
    * createNewObject                -> creates new post in the database, containing all the properties of the MapObject
    * */

    // Database connection parameters
    private final String useSSLFalse  = "?useSSL=false";
    private final String useSSLTrue   = "?useSSL=true";
    private final String DATABASE     = "ships";
    private final String URL          = "jdbc:mysql://flottan.mysql.database.azure.com:3306/" + DATABASE + useSSLTrue;
    private final String USERNAME     = "goow@flottan";
    private final String PASSWORD     = "Nackademin!123";
    private final Connection SQL;

    // tables
    private final String OBJECTTABLE  = "object_state_log";

    // columns for OBJECTTABLE (used in query-strings)
    public final String qObjID = "object_id";
    public final String qObjType = "object_type";
    public final String qObjDocked = "is_docked";
    public final String qObjConSum = "container_sum";
    public final String qXAxis = "x_axis";
    public final String qYAxis = "y_axis";

    // constructor

    SQL() throws SQLException {
        this.SQL = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // getters

    public void closeSQLConnection() throws SQLException {this.SQL.close();}

    /*
    *
    *   THIS IS THE GET SQL DATA SECTION
    *
    * */

    public ArrayList<String> getAllObjectIDs(String objectType, boolean checkFor) {
        // returns an array list with string array lists, one string array list is the content of one post in SQL DB

        ArrayList<String> listOfObjectIDs = new ArrayList<>();

        try {
            PreparedStatement myStatement = this.SQL.prepareStatement("select * from ?");
            myStatement.setString(1, this.OBJECTTABLE);

            if (checkFor) {
                myStatement = this.SQL.prepareStatement("select * from object_state_log where object_type = ?;");
                myStatement.setString(1, objectType);
            }

            ResultSet myResult = myStatement.executeQuery();
            while (myResult.next()) {
                String objectID = myResult.getString("object_id");
                listOfObjectIDs.add(objectID);
            }
            myResult.close();
            myStatement.close();
        } catch (Exception exc) { exc.printStackTrace(); }

        return listOfObjectIDs;
    }

    private String getObjectPostString(String objectID) {
        String sqlQuery = "select * from " + this.OBJECTTABLE + " where object_id = '" + objectID + "';";
        String result = "No return: " + "object_type" + " on " + this.OBJECTTABLE;

        return getRunSQLQuery(sqlQuery, "object_type", result);
    }

    public int getObjectPostInt(String objectID, String returnColumn) {
        String sqlQuery = "select * from " + this.OBJECTTABLE + " where object_id = '" + objectID + "';";
        int result = -1;

        try {
            Statement myStatement = this.SQL.createStatement();
            ResultSet myResult = myStatement.executeQuery(sqlQuery);
            myResult.next();

            result = myResult.getInt(returnColumn);


        } catch (Exception exc) { exc.printStackTrace(); }
        return result;
    }

    public String getObjectIdBasedOnCoordinate(int x, int y) {
        String sqlQuery = "select * from " + this.OBJECTTABLE + " where x_axis = '" + x + "' and y_axis = '" + y + "';";
        String result = "empty";

        return getRunSQLQuery(sqlQuery, "object_id", result);
    }

    public String getObjectTypeBasedOnCoordinate(int x, int y) {
        String sqlQuery = "select * from " + this.OBJECTTABLE + " where x_axis = " + x + " and y_axis = " + y + ";";
        String result = "empty";

        return getRunSQLQuery(sqlQuery, "object_type", result);
    }

    public String getObjectType(String objectID) { return getObjectPostString(objectID); }

    public int getObjectX(String objectID) { return getObjectPostInt(objectID, this.qXAxis); }

    public int getObjectY(String objectID) { return getObjectPostInt(objectID, this.qYAxis); }

    private String getRunSQLQuery(String query, String returnColumn, String result) {
        // runs SQL queries which will only retrieve data
        try {
            Statement myStatement = this.SQL.createStatement();
            ResultSet myResult = myStatement.executeQuery(query);
            while (myResult.next()) {
                result = myResult.getString(returnColumn);
            }
            myResult.close();
            myStatement.close();
        } catch (Exception exc) {exc.printStackTrace();}
        return result;
    }

    /*
     *
     *   THIS IS THE SET SQL DATA SECTION
     *
     * */

    public void setRunSQLQuery(String query) {
        // runs SQL queries which will update and/or get SQL-data
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement prepStatement = myConn.prepareStatement(query);
            prepStatement.executeUpdate();
        } catch (Exception exc) {exc.printStackTrace();}
    }

    public void setObjectColumnString(String objectColumn, String newValue, String objectID) {
        // query making, update a certain column which is a string
        String query1 = "update " + this.OBJECTTABLE + " set " + objectColumn + " = '" + newValue + "'";
        String query2 = " where object_id = '" + objectID + "';";
        String runQuery = query1 + query2;
        setRunSQLQuery(runQuery);
    }

    public void setObjectColumnInt(String objectColumn, int newValue, String objectID) {
        // query making, update a certain column which is an int
        String query1 = "update " + this.OBJECTTABLE + " set " + objectColumn + " = " + newValue;
        String query2 = " where object_id = '" + objectID + "';";
        String runQuery = query1 + query2;
        setRunSQLQuery(runQuery);
    }

    public void createNewObject(String objectID, String objectType, int isDocked, int containerSum, int xAxis, int yAxis) {
        // query making, creates a new post in a SQL-table
        String insert = "insert into object_state_log ";
        String columns = "(object_id, object_type, is_docked, container_sum, x_axis, y_axis)";
        String values = "values ('" + objectID + "', '" + objectType + "', " + isDocked + ", " + containerSum;
        String values2 = ", " + xAxis + ", " + yAxis + ");";
        String query = insert + columns + values + values2;
        setRunSQLQuery(query);
    }

}
