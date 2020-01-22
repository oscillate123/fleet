import java.io.IOException;
import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException{
        //print_string("  -- Hello ladies and gentlemen this is your Main Method speaking. We are now flying --  ");
        //System.out.println("testing");
        //SQL.getDatabases();
        SQL sqlConnection = new SQL();
        GridMap newMap = new GridMap(25, sqlConnection);

        Map<String, String> boatCoords = getCoordMap("ship", sqlConnection);
        Map<String, String> harborCoords = getCoordMap("harbor", sqlConnection);

        Scanner entry = new Scanner(System.in);
        System.out.println("Welcome Skipper!");
        System.out.println("These ships at your disposal: ");
        for (String ship : boatCoords.keySet()) {
            System.out.print(ship + "\n");
        }
        System.out.print("Choose your ship: ");
        String shipName = entry.nextLine().toLowerCase();

        System.out.print("Do you want to go manually [1] or automatically [2]: ");
        int test = entry.nextInt();
        if (test == 1) {
            boolean moving = true;
            while (moving) {
                cls();
                newMap.drawMap();
                newMap.updateCord(shipName);
            }
        } else if (test == 2) {
            System.out.print("Where do you want to go? Enter destination Harbor: ");
            String destination = "sw_harbor";
            newMap.autoMove(shipName, destination, harborCoords);
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

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
