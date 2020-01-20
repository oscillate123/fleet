public class Main {
    public static void main(String[] args) {
        System.out.println("\n ## IF YOU GET AN ERROR MESSAGE THAT INCLUDES AN IP ADRESS, SEND IT TO OSCAR! ## \n\n");

        String objectID = "BOAT1234";

        String x = SQL.getObjectType(objectID);
        System.out.println(x);

        System.out.println(SQL.getObjectX(objectID));
        System.out.println(SQL.getObjectY(objectID));

        print_string(SQL.getCoordinateObjectID(0, 0));

    }

    public static void print_string(String text) {
        System.out.println("\n" + text);
    }

    public static void print_int(int x) {
        System.out.println("\n" + x);
    }

}
