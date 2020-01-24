import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class SuppFunc {

    // TODO: SuppFunc = Support Functions
    // Here we have supportive functions, to make easy things more effective and use less code.
    // We don't want to repeat ourselves (DRY)

    // Static class properties
    private static Scanner scan = new Scanner(System.in);

    // print methods
    public static void print_string(String text) { System.out.println(text); }
    public static void print_int(int x) { System.out.println(x); }
    public static void closeScan() { scan.close();}

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

    // general support methods
    public static boolean compareVal(int val1, int val2) {
        return val1 > val2;
    }

    public static ArrayList<String> getQueueFromUser(SQL sqlConnection, Ship ship) {

        ArrayList<String> harbors = sqlConnection.getAllObjectIDs("harbor", true);
        ArrayList<String> deliverySchedule = new ArrayList<>();
        String continueOrder = "";
        String stopp = "no";
        int maxUnload = ship.getContainerAmount();
        int unOrLoadAmount = 0;

        if (maxUnload+1 < unOrLoadAmount) {
            print_string("Ship is out of containers. Current order queue will be executed.");
        }

        while (!continueOrder.equals(stopp) & (maxUnload > unOrLoadAmount-11)) {

            String harborResult = "";
            while (!isStringInArray(harbors, harborResult)) {
                print_string("\n\nAvailable destinations: ");
                for (String item : harbors) { print_string(item); }
                print_string("");

                harborResult = getStringInput("Which harbor do you want to go to? Type 'cancel' to abort.");
                if (harborResult.equals("cancel")) {
                    return new ArrayList<String>(Arrays.asList("cancel"));
                } else if (!isStringInArray(harbors, harborResult)) {
                    print_string("\n" + harborResult + " is not a viable choice!");
                }
            }

            boolean isNotBigger = false;
            int max = 11;
            int containerAmount = 999999999; // TODO: Make this part of the code more efficient

            while (containerAmount > max & !isNotBigger) {
                try {
                    String message1 = "\nUsage: '10' for getting 10 containers, and '-10' for dropping 10 containers.";
                    String message3 = "\nYou can maximally drop/retrieve 10 containers at the time.";
                    String message2 = "\nHow many containers do you want to drop or pickup at " + harborResult + " ?";
                    containerAmount = getIntInput(message1 + message3 + message2);

                    int harborContainerSum = sqlConnection.getObjectPostInt(harborResult, sqlConnection.qObjConSum);
                    int newHarborContainerSum = harborContainerSum + containerAmount;
                    int newShipContainerSum = ship.getContainerAmount() + containerAmount;

                    isNotBigger = compareVal(newHarborContainerSum, 0);
                    isNotBigger = compareVal(newShipContainerSum, 0);

                    if (!isNotBigger) {
                        print_string("You can not un/load " + containerAmount + " containers.");
                        print_string("The harbor has " + harborContainerSum + " containers and the ship has " + ship.getContainerAmount() + ".");
                    } else if (containerAmount > max) {
                        print_string("You can only un/load " + max + " containers at once.");
                    }
                } catch (Exception e) {
                    print_string("Incorrect input.");
                }
            }

            Scanner tempScan = new Scanner(System.in); // apparently it doesn't work using xyz.nextLine() twice, therefor we create a new one.
            print_string("Do you want to queue for more orders? Type 'no' to start shipping!");
            continueOrder = tempScan.nextLine();

            if (continueOrder.equals(stopp)) { tempScan.close(); }

            unOrLoadAmount = unOrLoadAmount + containerAmount; // check if the route container drop amount is too much
            deliverySchedule.add(harborResult + "," + intToStr(containerAmount)); // adding ship info to the array
        }
        return deliverySchedule;
    }

}
