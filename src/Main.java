public class Main {
    public static void main(String[] args) {
        System.out.println("\n ## IF YOU GET AN ERROR MESSAGE THAT INCLUDES AN IP ADRESS, SEND IT TO OSCAR! ## \n\n");

        String objectID = "BOAT1234";

        print_string(SQL.getCoordinateObjectID(0, 0));
        print_string(SQL.getObjectType(objectID));
        SQL.setObjectColumnString(SQL.qObjType, "ship" ,objectID);
        SQL.setObjectColumnInt(SQL.qObjConSum, 10, objectID);
        print_string(SQL.getObjectType(objectID));
        print_int(SQL.getObjectPostInt(objectID, SQL.qObjConSum));

        MapObject kek = new MapObject("Gädda", "fiskebåt" +
                "", 75, 75);

        print_string(kek.getObjectType());

        int counter = 0;

        while (counter != 100) {
            counter++;
            String query = "insert into container_log (current_owner_id) values ('testboat1234');";
            SQL.setRunSQLQuery(query);
        }

    }

    public static void print_string(String text) {
        System.out.println("\n" + text);
    }

    public static void print_int(int x) {
        System.out.println("\n" + x);
    }

}
