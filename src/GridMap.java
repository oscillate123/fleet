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

    public void updateCord(String objID, SQL sqlConnection){
        Scanner input = new Scanner(System.in);
        int x = sqlConnection.getObjectX(objID);
        int y = sqlConnection.getObjectY(objID);
        System.out.print("Move ship (N, NW, W, SW, S, SE, E, NE): ");
        String answer = input.nextLine().toUpperCase();

        switch (answer){
            case "N":
                y--;
                break;
            case  "E":
                x++;
                break;
            case "S":
                y++;
                break;
            case "W":
                x--;
                break;
            case "NW":
                y--;
                x--;
                break;
            case "NE":
                y--;
                x++;
                break;
            case "SE":
                y++;
                x++;
                break;
            case "SW":
                y++;
                x--;
                break;
        }
        sqlConnection.setObjectColumnInt("x_axis", x, objID);
        sqlConnection.setObjectColumnInt("y_axis", y, objID);
    }
}
