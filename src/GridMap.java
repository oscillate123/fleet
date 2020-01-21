import java.util.*;

public class GridMap {
    int size;
    int mapCenter;

    public GridMap(int mapSize) {
        this.size = mapSize;
        mapCenter = size/2+1;

    }

    void drawMap(Map<String, String> dict) {
        char objChar;
        for (int y = 1; y <= this.size; y++) {
            for (int x = 1; x <= this.size; x++) {
                if ((y == 1 && (x == 1 || x == this.size)) || ((y == this.size && (x == 1 || x == this.size))) || (x == this.mapCenter && y == this.mapCenter))
                    objChar = 'H';
                else
                    objChar = ' ';
                for(String boat : dict.keySet()){
                    String[] temp = dict.get(boat).split(",");
                    int boatX = Integer.parseInt(temp[0]);
                    int boatY = Integer.parseInt(temp[1]);
                    if (boatX == x && boatY == y)
                        objChar = 'B';
                }

                System.out.print("[" + objChar + "]");
            }
            System.out.print("\n");
        }
    }
}
