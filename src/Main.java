import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        print_string("kek");

        String objectID = "BOAT1234";
        ArrayList<String> kek = SQL.getOneObjectAsArrayList(objectID);

        Ship boat = new Ship(kek.get(0), kek.get(1), stringToInt(kek.get(2)),
                stringToInt(kek.get(3)), stringToInt(kek.get(4)), stringToInt(kek.get(5)), false);

        if (!boat.getIsDocked()) {
            print_string("it works");
        }

        for (String object_id : SQL.getAllObjectIDs()) {
            print_string(object_id);
        }

    }

    public static void print_string(String text) {
        System.out.println("\n" + text);
    }

    public static void print_int(int x) {
        System.out.println("\n" + x);
    }

    public static int stringToInt (String str) { return Integer.parseInt(str); }

}
