import java.io.IOException;
import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException, IOException {

        /* SQL sql = new SQL();
        Ship ship1 = new Ship("vasa", false, sql);

        ArrayList<String> kek = SuppFunc.getQueueFromUser(sql, ship1);
        SuppFunc.print_string(kek.toString()); OSCAR SUDDAADE DHÄR

        System.exit(0); */

        int gameMapSize = 25; //Här ändras storleken på kartan

        SQL sqlConnection = new SQL();
        GridMap newMap = new GridMap(gameMapSize, sqlConnection);

        Map<String, String> boatCoords = getCoordMap("ship", sqlConnection);

        /*ArrayList<String> deliverySchedule = new ArrayList<>();
        deliverySchedule.add("nw_harbor,10");
        deliverySchedule.add("sw_harbor,-5"); OSCAR SUDDADE DE HÄR
        deliverySchedule.add("se_harbor,15");
        deliverySchedule.add("c_harbor,-10");
        deliverySchedule.add("ne_harbor,-5");*/

        Scanner entry = new Scanner(System.in);
        System.out.println("Welcome Skipper!");
        System.out.println("These ships at your disposal: ");
        for (String ship : boatCoords.keySet()) {
            System.out.print(SuppFunc.capitalize(ship) + "\n");
        }
        System.out.print("Choose your ship: ");
        String shipID = entry.nextLine().toLowerCase();

        boolean exists = SuppFunc.isStringInArray(sqlConnection.getAllObjectIDs("ship", true), shipID);
        Ship myShip = new Ship(shipID, !exists, sqlConnection);

        System.out.print("Do you want to go manually [1] or automatically [2]: ");
        int test = entry.nextInt();
        if (test == 1) {
            boolean moving = true;
            while (moving) {
                // SuppFunc.cls();
                SuppFunc.print_string("\n\n\n\n\n\n\n"); // oscar: cls funkar inte på mac & lägg över sånna funktioner till SuppFunc filen
                moving = newMap.updateCord(myShip);
            }
        } else if (test == 2) {

            ArrayList<String> deliverySchedule = SuppFunc.getQueueFromUser(sqlConnection, myShip); // OSCAR LA TILL DEN HÄR

            for (String item : deliverySchedule){
                String dest = item.split(",")[0];
                int container = Integer.parseInt(item.split(",")[1]);
                newMap.autoMove(myShip, dest, container);
            }
        }
        // Always close before the program ends.
        SuppFunc.closeScan();
        entry.close();
        sqlConnection.closeSQLConnection();
    }

    public static Map<String, String> getCoordMap(String type, SQL sqlConnection) {
        ArrayList<String> x = sqlConnection.getAllObjectIDs(type, true);
        Map<String, String> coordMap = new HashMap<>();
        for (String objID : x) {
            int xCoord = sqlConnection.getObjectX(objID);
            int yCoord = sqlConnection.getObjectY(objID);
            String coordString = xCoord + "," + yCoord;
            coordMap.put(objID, coordString);
        }
        return coordMap;
    }
}
