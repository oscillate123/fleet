import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {

        SQL sqlConnection = new SQL();

        print_string("  -- Hello ladies and gentlemen this is your Main Method speaking. We are now flying --  ");

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
            print_string("Type: " + sqlConnection.getObjectType(objectID));
        }

    }

    public static void print_string(String text) { System.out.println(text); }
    public static void print_int(int x) { System.out.println(x); }
    public static int strToInt(String str) { return Integer.parseInt(str); }

}
