import java.sql.*;

public class SQL {

    // TODO: If there is time, add SQL-injection checker

    @SuppressWarnings("unused")
    private static final String useSSLFalse  = "?useSSL=false";
    private static final String useSSLTrue   = "?useSSL=true";
    private static final String DATABASE     = "ships";
    private static final String URL          = "jdbc:mysql://flottan.mysql.database.azure.com:3306/" + DATABASE + useSSLTrue;
    private static final String USERNAME     = "goow@flottan";
    private static final String PASSWORD     = "Nackademin!123";
    private static final String OBJECTTABLE  = "object_state_log";


    public static final String qObjID = "object_id";
    public static final String qObjType = "object_type";
    public static final String qObjDocked = "is_docked";
    public static final String qObjConSum = "container_sum";
    public static final String qObjX = "x_axis";
    public static final String qObjY = "y_axis";


    /*
    *
    *   THIS IS THE GET SQL DATA SECTION
    *
    * */

    public static void getObjectStateTable() {
        String sql_query = "select * from " + OBJECTTABLE + ";";

        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement myStatement = myConn.createStatement();
            ResultSet myResult = myStatement.executeQuery(sql_query);
            while (myResult.next()) {
                String objectID = myResult.getString("object_id");
                String objectType = myResult.getString("object_type");
                boolean isDocked = (myResult.getInt("is_docked") == 1);
                int containerSum = myResult.getInt("container_sum");
                int xAxis = myResult.getInt("x_axis");
                int yAxis = myResult.getInt("y_axis");

                String titles = "    ID   |     TYPE      |DOCKED |SUM |  X   |  Y";
                System.out.println(titles);

                String messagePart1 = objectID + " | " + objectType + " | "  + isDocked + " | ";
                String messagePart2 = toString(containerSum) + " | X: " + toString(xAxis) + " | Y: " + toString(yAxis);
                String message = messagePart1 + messagePart2;
                System.out.println(message);
            }
        } catch (Exception exc) { exc.printStackTrace(); }
    }

    private static String getObjectPostString(String objectID) {
        String sqlQuery = "select * from " + SQL.OBJECTTABLE + " where object_id = '" + objectID + "';";
        String result = "No return: " + "object_type" + " on " + SQL.OBJECTTABLE;

        return getRunSQLQuery(sqlQuery, "object_type", result);
    }

    public static int getObjectPostInt(String objectID, String returnColumn) {
        String sqlQuery = "select * from " + SQL.OBJECTTABLE + " where object_id = '" + objectID + "';";
        int result = -1;

        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement myStatement = myConn.createStatement();
            ResultSet myResult = myStatement.executeQuery(sqlQuery);
            myResult.next();

            result = myResult.getInt(returnColumn);

            myConn.close();

        } catch (Exception exc) { exc.printStackTrace(); }
        return result;
    }

    private static String getObjectBasedOnCoordinate(int x, int y) {
        String sqlQuery = "select * from " + SQL.OBJECTTABLE + " where x_axis = '" + x + "' and y_axis = '" + y + "';";
        String result = "No return: " + "object_id" + " on " + SQL.OBJECTTABLE;

        return getRunSQLQuery(sqlQuery, "object_id", result);
    }

    private static String getRunSQLQuery(String query, String returnColumn, String result) {
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement myStatement = myConn.createStatement();
            ResultSet myResult = myStatement.executeQuery(query);
            myResult.next();

            result = myResult.getString(returnColumn);

            myConn.close();

        } catch (Exception exc) {exc.printStackTrace();}
        return result;
    }

    public static String getObjectType(String objectID) {
        return getObjectPostString(objectID);
    }

    public static int getObjectX(String objectID) {
        return getObjectPostInt(objectID, "x_axis");
    }

    public static int getObjectY(String objectID) {
        return getObjectPostInt(objectID, "y_axis");
    }

    public static String getCoordinateObjectID(int xAxis, int yAxis) {
        return getObjectBasedOnCoordinate(xAxis, yAxis);
    }

    private static String toString (int num) {
        return Integer.toString(num);
    }

    /*
     *
     *   THIS IS THE SET SQL DATA SECTION
     *
     * */

    public static void setRunSQLQuery(String query) {
        try {
            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement prepStatement = myConn.prepareStatement(query);
            prepStatement.executeUpdate();
        } catch (Exception exc) {exc.printStackTrace();}
    }

    public static void setObjectColumnString(String objectColumn, String newValue, String objectID) {
        String query1 = "update " + OBJECTTABLE + " set " + objectColumn + " = '" + newValue + "'";
        String query2 = " where object_id = '" + objectID + "';";
        String runQuery = query1 + query2;
        setRunSQLQuery(runQuery);
    }

    public static void setObjectColumnInt(String objectColumn, int newValue, String objectID) {
        String query1 = "update " + OBJECTTABLE + " set " + objectColumn + " = " + newValue + "";
        String query2 = " where object_id = '" + objectID + "';";
        String runQuery = query1 + query2;
        setRunSQLQuery(runQuery);
    }

    public static void createNewObject(String objectID, String objectType, int isDocked, int containerSum,
                                           int xAxis, int yAxis) {
        String insert = "insert into object_state_log ";
        String columns = "(object_id, object_type, is_docked, container_sum, x_axis, y_axis)";
        String values = "values ('" + objectID + "', '" + objectType + "', " + isDocked + ", " + containerSum;
        String values2 = ", " + xAxis + ", " + yAxis + ");";
        String query = insert + columns + values + values2;
        setRunSQLQuery(query);
    }

}
