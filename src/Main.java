import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException{
        //print_string("  -- Hello ladies and gentlemen this is your Main Method speaking. We are now flying --  ");
        //System.out.println("testing");
        //SQL.getDatabases();
        SQL sqlConnection = new SQL();
        GridMap newMap = new GridMap(25);
        Map<String, String> boatCoords = getCoordMap(sqlConnection);

        newMap.drawMap(boatCoords);

    }

    public static Map<String, String> getCoordMap(SQL sqlConnection) {
        ArrayList<String> x = sqlConnection.getAllObjectIDs("ship", true);
        Map<String, String> coordMap = new HashMap<String, String>();
        for (String objID : x) {
            int xCoord = sqlConnection.getObjectX(objID);
            int yCoord = sqlConnection.getObjectY(objID);
            String coordString = Integer.toString(xCoord) + "," + Integer.toString(yCoord);
            coordMap.put(objID, coordString);
        }
        return coordMap;
    }
    public static void print_string(String text) { System.out.println(text); }
    public static void print_int(int x) { System.out.println(x); }
    public static int strToInt(String str) { return Integer.parseInt(str); }

}
