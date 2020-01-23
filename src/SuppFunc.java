import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class SuppFunc {

    // TODO: SuppFunc = Support Functions
    // Here we have supportive functions, to make easy things more effective and use less code.
    // We don't want to repeat ourselves (DRY)

    // Static class properties
    private static Scanner scan = new Scanner(System.in);

    // print methods
    public static void print_string(String text) { System.out.println(text); }
    public static void print_int(int x) { System.out.println(x); }

    // convert methods
    public static int strToInt(String str) { return Integer.parseInt(str); }
    public static String intToStr(int num) { return Integer.toString(num); }

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

}
