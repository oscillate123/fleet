import java.util.*;

public class Main {
    public static void main(String[] args) {
        //System.out.println("testing");
        //SQL.getDatabases();
        ArrayList<String> boatsID = new ArrayList<String>();
        boatsID.add("BOAT1234");
        boatsID.add("din_mamma");
        boatsID.add("min_mamma");
        boatsID.add("ollan");
        boatsID.add("olle");
        boatsID.add("ollen");
        boatsID.add("qwe");
        boatsID.add("testboat1234");

        Map<String, String> xDict = new HashMap<String, String>();
        xDict.put("BOAT1234", "1,2");
        xDict.put("din_mamma", "2,2");
        xDict.put("min_mamma", "2,1");
        xDict.put("ollan", "24,1");
        xDict.put("olle", "24,2");
        xDict.put("ollen", "25,2");
        xDict.put("qwe", "24,24");
        xDict.put("testboat1234", "25,24");
        GridMap newMap = new GridMap(25);
        newMap.drawMap(boatsID, xDict);

        /*
        System.out.println("\n ## IF YOU GET AN ERROR MESSAGE THAT INCLUDES AN IP ADRESS, SEND IT TO OSCAR! ## \n\n");
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
        */

    }

    public static void print_string(String text) {
        System.out.println("\n" + text);
    }

    public static void print_int(int x) {
        System.out.println("\n" + x);
    }

    public static int stringToInt (String str) { return Integer.parseInt(str); }

}
