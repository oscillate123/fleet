import java.io.IOException;
import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        int gameMapSize = 25; //Här ändras storleken på kartan

        SQL sqlConnection = new SQL();
        GridMap newMap = new GridMap(gameMapSize, sqlConnection);

        Map<String, String> boatCoords = getCoordMap("ship", sqlConnection);
        Map<String, String> harborCoords = getCoordMap("harbor", sqlConnection);
        ArrayList<String> deliverySchedule = new ArrayList<String>();
        deliverySchedule.add("nw_harbor,10");
        deliverySchedule.add("sw_harbor,-5");
        deliverySchedule.add("se_harbor,15");
        deliverySchedule.add("c_harbor,-10");
        deliverySchedule.add("ne_harbor,-5");

        Scanner entry = new Scanner(System.in);
        System.out.println("Welcome Skipper!");
        System.out.println("These ships at your disposal: ");
        for (String ship : boatCoords.keySet()) {
            System.out.print(capitalize(ship) + "\n");
        }
        System.out.print("Choose your ship: ");
        String shipID = entry.nextLine().toLowerCase();

        boolean exists = SuppFunc.isStringInArray(sqlConnection.getAllObjectIDs(sqlConnection.qObjType, false), shipID);
        Ship myShip = new Ship(shipID, exists, sqlConnection);

        System.out.print("Do you want to go manually [1] or automatically [2]: ");
        int test = entry.nextInt();
        if (test == 1) {
            boolean moving = true;
            while (moving) {
                cls();
                newMap.drawMap();
                moving = newMap.updateCord(myShip);
            }
        } else if (test == 2) {
            for (String item : deliverySchedule){
                String dest = item.split(",")[0];
                int container = Integer.parseInt(item.split(",")[1]);
                newMap.autoMove(myShip, dest, container, harborCoords);
            }
        }
        // Always close connection before the program ends.
        entry.close();
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
