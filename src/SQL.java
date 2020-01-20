import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL {

    @SuppressWarnings("unused")
    private final String useSSLFalse  = "?useSSL=false";
    private static final String useSSLTrue   = "?useSSL=true";
    private static final String DATABASE     = "ships";
    private static final String URL          = "jdbc:mysql://flottan.mysql.database.azure.com:3306/" + DATABASE + useSSLTrue;
    private static final String USERNAME     = "goow@flottan";
    private static final String PASSWORD     = "Nackademin!123";


    public static void getDatabases () {

        // String sql_query = "select * from " + table + ";";
        String sql_query = "show databases;";

        try {

            Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement myStatement = myConn.createStatement();

            ResultSet myResult = myStatement.executeQuery(sql_query);

            while (myResult.next()) {
                System.out.println(myResult.getString("Database"));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
