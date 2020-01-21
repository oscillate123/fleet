import java.util.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException{
        //print_string("  -- Hello ladies and gentlemen this is your Main Method speaking. We are now flying --  ");
        //System.out.println("testing");
        //SQL.getDatabases();
        SQL sqlConnection = new SQL();

        GridMap newMap = new GridMap(25);

        ArrayList<String> x = SQL.getAllObjectIDs();
        Map<String, String> boatCoords = new HashMap<String, String>();
        for (String objID : x) {
            int xCoord = SQL.getObjectX(objID);
            int yCoord = SQL.getObjectY(objID);
            String coordString = Integer.toString(xCoord) + "," + Integer.toString(yCoord);
            boatCoords.put(objID, coordString);
        }

        newMap.drawMap(boatCoords);


        /*System.out.println("\n ## IF YOU GET AN ERROR MESSAGE THAT INCLUDES AN IP ADRESS, SEND IT TO OSCAR! ## \n\n");
        print_string("kek");

        String objectID = "BOAT1234";
        ArrayList<String> l = sqlConnection.getOneObjectAsArrayList(objectID);

        print_string(l.toString());

        print_string(sqlConnection.getCoordinateObjectID(0, 0));
        print_string(sqlConnection.getObjectType(objectID));
        sqlConnection.setObjectColumnString(sqlConnection.qObjType, "ship" ,objectID);
        sqlConnection.setObjectColumnInt(sqlConnection.qObjConSum, 10, objectID);
        print_string(sqlConnection.getObjectType(objectID));
        print_int(sqlConnection.getObjectPostInt(objectID, sqlConnection.qObjConSum));

        MapObject kek = new MapObject("Gädda", "fiskebåt", +
                75, 75, false);

        print_string(kek.getObjectType());

        int counter = 0;

        Ship boat = new Ship(l.get(0), l.get(1), strToInt(l.get(2)),
                strToInt(l.get(3)), strToInt(l.get(4)), strToInt(l.get(5)), false);


        if (!boat.getIsDocked()) {
            print_string("it works");
        }

        for (String object_id : sqlConnection.getAllObjectIDs()) {
            print_string("\n");
            print_string("ID:   " + object_id);
            print_string("Type: " + SQL.getObjectType(objectID));
        }*/

    }

    /*public static void print_string(String text) { System.out.println(text); }
    public static void print_int(int x) { System.out.println(x); }
    public static int strToInt(String str) { return Integer.parseInt(str); }*/

}
