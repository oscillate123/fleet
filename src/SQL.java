import java.sql.*;
import java.util.ArrayList;

public class SQL {

    // TODO: If there is time, add SQL-injection checker
    // TODO: Reusable SQL query methods

    // Database connection
    private final String useSSLFalse  = "?useSSL=false";
    private final String useSSLTrue   = "?useSSL=true";
    private final String DATABASE     = "ships";
    private final String URL          = "jdbc:mysql://flottan.mysql.database.azure.com:3306/" + DATABASE + useSSLTrue;
    private final String USERNAME     = "goow@flottan";
    private final String PASSWORD     = "Nackademin!123";
    private final Connection SQL;

    // tables
    private final String OBJECTTABLE  = "object_state_log";

    // columns for OBJECTTABLE
    public final String qObjID = "object_id";
    public final String qObjType = "object_type";
    public final String qObjDocked = "is_docked";
    public final String qObjConSum = "container_sum";
    public final String qXAxis = "x_axis";
    public final String qYAxis = "y_axis";

    // constructor

    SQL() throws SQLException {
        this.SQL = DriverManager.getConnection(URL, USERNAME, PASSWORD);;
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

    public ArrayList<String> getOneObjectAsArrayList(String findObjectID) {
        // returns an array list with string array lists, one string array list is the content of one post in SQL DB

        ArrayList<String> resultList = new ArrayList<>();
        try {
            PreparedStatement myStatement = this.SQL.prepareStatement(
                    "select * from object_state_log where object_id = ? ;");
            myStatement.setString(1, findObjectID);

            ResultSet myResult = myStatement.executeQuery();
            while (myResult.next()) {
                String objectID = myResult.getString("object_id");
                String objectType = myResult.getString("object_type");
                int isDocked = myResult.getInt("is_docked");
                int containerSum = myResult.getInt("container_sum");
                int xAxis = myResult.getInt("x_axis");
                int yAxis = myResult.getInt("y_axis");

                resultList.add(objectID);
                resultList.add(objectType);
                resultList.add(toString(isDocked));
                resultList.add(toString(containerSum));
                resultList.add(toString(xAxis));
                resultList.add(toString(yAxis));
            }

            myResult.close();
            myStatement.close();
        } catch (Exception exc) { exc.printStackTrace(); }
        return resultList;
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

    private String getRunSQLQuery(String query, String returnColumn, String result) {
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

    public String getObjectType(String objectID) { return getObjectPostString(objectID); }

    public int getObjectX(String objectID) { return getObjectPostInt(objectID, this.qXAxis); }

    public int getObjectY(String objectID) { return getObjectPostInt(objectID, this.qYAxis); }

    public String getCoordinateObjectID(int xAxis, int yAxis) { return getObjectIdBasedOnCoordinate(xAxis, yAxis); }

    private String toString (int num) { return Integer.toString(num); }

    /*
     *
     *   THIS IS THE SET SQL DATA SECTION
     *
     * */

    public void setRunSQLQuery(String query) {
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement prepStatement = myConn.prepareStatement(query);
            prepStatement.executeUpdate();
        } catch (Exception exc) {exc.printStackTrace();}
    }

    public void setObjectColumnString(String objectColumn, String newValue, String objectID) {
        String query1 = "update " + this.OBJECTTABLE + " set " + objectColumn + " = '" + newValue + "'";
        String query2 = " where object_id = '" + objectID + "';";
        String runQuery = query1 + query2;
        setRunSQLQuery(runQuery);
    }

    public void setObjectColumnInt(String objectColumn, int newValue, String objectID) {
        String query1 = "update " + this.OBJECTTABLE + " set " + objectColumn + " = " + newValue + "";
        String query2 = " where object_id = '" + objectID + "';";
        String runQuery = query1 + query2;
        setRunSQLQuery(runQuery);
    }

    public void createNewObject(String objectID, String objectType, int isDocked, int containerSum,
                                           int xAxis, int yAxis) {
        String insert = "insert into object_state_log ";
        String columns = "(object_id, object_type, is_docked, container_sum, x_axis, y_axis)";
        String values = "values ('" + objectID + "', '" + objectType + "', " + isDocked + ", " + containerSum;
        String values2 = ", " + xAxis + ", " + yAxis + ");";
        String query = insert + columns + values + values2;
        setRunSQLQuery(query);
    }

}
