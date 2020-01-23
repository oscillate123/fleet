import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Key;
import java.sql.SQLException;
import java.util.*;

public class SuppFunc {

    // TODO: SuppFunc = Support Functions
    // Here we have supportive functions, to make easy things more effective and use less code.
    // We don't want to repeat ourselves (DRY)

    // testing methods
    public static void main() throws SQLException {

    }

    // Static class properties
    private static Scanner scan = new Scanner(System.in);

    // print methods
    public static void print_string(String text) { System.out.println(text); }
    public static void print_int(int x) { System.out.println(x); }

    // convert methods
    public static int strToInt(String str) { return Integer.parseInt(str); }
    public static String intToStr(int num) { return Integer.toString(num); }
    public static String capitalize(String str) { return str.substring(0, 1).toUpperCase() + str.substring(1); }

    // input methods
    public static String getStringInput (String text) {
        print_string(text);
        return scan.nextLine();
    }
    public static int getIntInput (String text) {
        print_string(text);
        return scan.nextInt();
    }

    // lookup methods
    public static boolean isStringInArray (ArrayList<String> inList, String str) {
        boolean flag = false;

        for (String item : inList) {
            if (item.equals(str)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    // clear screen method
    public static void cls() throws IOException, InterruptedException {
        new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        System.out.println("\n\n\n");
    }

    public static ArrayList<String> getQueueFromUser(SQL sqlConnection, Ship ship) {

        ArrayList<String> harbors = sqlConnection.getAllObjectIDs("harbor", true);
        ArrayList<String> deliverySchedule = new ArrayList<>();

        while (deliverySchedule.size() > 0) {
            print_string("Available destinations: ");
            for (String item : harbors) { print_string(item); }
            print_string("");

            String harborResult = "";
            while (isStringInArray(harbors, harborResult) & harborResult.equals("exit")) {
                harborResult = getStringInput("Which harbor do you want to go to?");
                if (harborResult.equals("exit")) {
                    return new ArrayList<String>(Arrays.asList("exit"));
                }
            }

            boolean isNotBigger = true;
            int max = 11;
            int containerAmount = 1000000000; // TODO: Make this part of the code more efficient
            while (containerAmount > max & isNotBigger) {
                try {
                    String message1 = "\nUsage: '10' for getting 10 containers, and '-10' for dropping 10 containers.";
                    String message3 = "\nYou can maximally drop/retrieve 10 containers at the time.";
                    String message2 = "\nHow many containers do you want to drop or pickup at " + harborResult + " ?";
                    containerAmount = getIntInput(message1 + message3 + message2);

                    int newHarborContainerSum = sqlConnection.getObjectPostInt(harborResult, sqlConnection.qObjConSum) + containerAmount;

                    isNotBigger = compareVal(0, newHarborContainerSum);

                } catch (Exception e) {
                    print_string("Incorrect input.");
                }
            }



            deliverySchedule.add(harborResult + intToStr(containerAmount));
        }

        /*
        deliverySchedule.add(getStringInput(""));


        deliverySchedule.add("nw_harbor,10");
        deliverySchedule.add("sw_harbor,-5");
        deliverySchedule.add("se_harbor,15");
        deliverySchedule.add("c_harbor,-10");
        deliverySchedule.add("ne_harbor,-5");
         */

        return deliverySchedule;
    }

    public static boolean compareVal(int val1, int val2) {
        return val1 > val2;
    }

}
