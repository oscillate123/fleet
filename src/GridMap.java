import java.util.*;

public class GridMap {
    int size;
    int mapCenter;

    public GridMap(int mapSize) {
        this.size = mapSize;
        mapCenter = size/2+1;

    }

    void drawMap(Map<String, String> boatCoords, Map<String, String> harborCoords) {
        for (int y = 1; y <= this.size; y++) {
            for (int x = 1; x <= this.size; x++) {
                char objChar = ' ';

                for(String harbor : harborCoords.keySet()){
                    String[] temp = harborCoords.get(harbor).split(",");
                    int harborX = Integer.parseInt(temp[0]);
                    int harborY = Integer.parseInt(temp[1]);
                    if (harborX == x && harborY == y)
                        objChar = 'H';
                    else if ((harborX-1 <= x && x <= harborX+1) && (harborY-1 <= y && y <= harborY+1))
                        objChar = 'D';
                }

                for(String boat : boatCoords.keySet()){
                    String[] temp = boatCoords.get(boat).split(",");
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
