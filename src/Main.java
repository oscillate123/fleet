import java.io.IOException;
import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException{
        //print_string("  -- Hello ladies and gentlemen this is your Main Method speaking. We are now flying --  ");
        //System.out.println("testing");
        //SQL.getDatabases();
        SQL sqlConnection = new SQL();
        GridMap newMap = new GridMap(25);

        Map<String, String> boatCoords = getCoordMap("ship", sqlConnection);
        Map<String, String> harborCoords = getCoordMap("harbor", sqlConnection);
        ArrayList<String> ships = sqlConnection.getAllObjectIDs("ship", true);
        for (String temp : ships) {
            String str = temp.substring(0, 1).toUpperCase() + temp.substring(1);
            ships.set(ships.indexOf(temp), str);
        }
        Scanner entry = new Scanner(System.in);
        System.out.println("Welcome Skipper!");
        System.out.println("These ships at your disposal:");
        System.out.println(ships);
        System.out.print("Choose your ship ");
        String shipName = entry.nextLine();

        boolean moving = true;
        while (moving) {
            cls();
            newMap.drawMap(boatCoords, harborCoords);
            newMap.updateCord(shipName, sqlConnection);
            boatCoords = getCoordMap("ship", sqlConnection);
        }
        // Always close connection before the program ends.
        sqlConnection.closeSQLConnection();
    }

    public static Map<String, String> getCoordMap(String type, SQL sqlConnection) {
        ArrayList<String> x = sqlConnection.getAllObjectIDs(type, true);
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


    public static void cls()
    {
        try
        {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            System.out.println("\n\n\n");
        }catch(Exception E)
        {
            System.out.println(E);
        }
    }

}
