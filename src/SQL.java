import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL {

    @SuppressWarnings("unused")
    private static final String useSSLFalse  = "?useSSL=false";
    private static final String useSSLTrue   = "?useSSL=true";
    private static final String DATABASE     = "ships";
    private static final String URL          = "jdbc:mysql://flottan.mysql.database.azure.com:3306/" + DATABASE + useSSLTrue;
    private static final String USERNAME     = "goow@flottan";
    private static final String PASSWORD     = "Nackademin!123";


    public static void getDatabases () {

        String table = "object_state_log";

        String sql_query = "select * from " + table + ";";
        // String sql_query = "show databases;";

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

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static String toString (int num) {
        return Integer.toString(num);
    }
}
