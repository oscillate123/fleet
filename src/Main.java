public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        //System.out.println("testing");
        //SQL.getDatabases();
        Map newMap = new Map(25);
        newMap.drawMap();
=======
        System.out.println("\n ## IF YOU GET AN ERROR MESSAGE THAT INCLUDES AN IP ADRESS, SEND IT TO OSCAR! ## \n\n");

        String objectID = "BOAT1234";

        print_string(SQL.getCoordinateObjectID(0, 0));
        print_string(SQL.getObjectType(objectID));
        SQL.setObjectColumnString(SQL.qObjType, "ship" ,objectID);
        SQL.setObjectColumnInt(SQL.qObjConSum, 10, objectID);
        print_string(SQL.getObjectType(objectID));
        print_int(SQL.getObjectPostInt(objectID, SQL.qObjConSum));

        MapObject kek = new MapObject("din_mamma", "ship", 0, 0);

        print_string(kek.getObjectType());

>>>>>>> 4da957849491876379e4fcaaa16743cbf292bbf3
    }

    public static void print_string(String text) {
        System.out.println("\n" + text);
    }

    public static void print_int(int x) {
        System.out.println("\n" + x);
    }

}
