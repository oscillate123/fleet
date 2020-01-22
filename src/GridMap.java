import java.util.*;
import java.util.concurrent.TimeUnit;

public class GridMap {
    int size;
    int mapCenter;
    SQL sqlConnection;

    public GridMap(int mapSize, SQL sqlConnection) {
        this.size = mapSize;
        mapCenter = size/2+1;
        this.sqlConnection = sqlConnection;
    }

    void drawMap() {
        Map<String, String> boatCoords = Main.getCoordMap("ship", this.sqlConnection);
        Map<String, String> harborCoords = Main.getCoordMap("ship", this.sqlConnection);
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
                        objChar = boat.toUpperCase().charAt(0);
                }

                System.out.print("[" + objChar + "]");
            }
            System.out.print("\n");
        }
        System.out.print("H = Harbor, D = Dock \nBoats: ");
        for(String boat : boatCoords.keySet()){
            System.out.print(Main.capitalize(boat).charAt(0) + " = " + Main.capitalize(boat) + ", ");
        }
    }

    public void autoMove(String objID, String destination, Map<String, String> harborCoords) {
        Map<String, String> boatCoords = Main.getCoordMap("ship", this.sqlConnection);
        int destX = this.sqlConnection.getObjectX(destination);
        int destY = this.sqlConnection.getObjectY(destination);
        int shipX = this.sqlConnection.getObjectX(objID);
        int shipY = this.sqlConnection.getObjectY(objID);


        while (shipX != destX || shipY != destY) {
            shipX = this.sqlConnection.getObjectX(objID);
            shipY = this.sqlConnection.getObjectY(objID);
            Main.cls();
            boatCoords = Main.getCoordMap("ship", this.sqlConnection);
            if (shipX < destX)
                shipX++;
            else if (shipX > destX)
                shipX--;
            if (shipY < destY)
                shipY++;
            else if (shipY > destY)
                shipY--;
            System.out.println("I want to go to x=" + shipX + " y=" + shipY);
            if (checkNextSquare(destX, destY)){
                this.sqlConnection.setObjectColumnInt("x_axis", destX, objID);
                this.sqlConnection.setObjectColumnInt("y_axis", destY, objID);
            }
            this.drawMap();
        }

    }

    public void updateCord(String objID){
        Scanner input = new Scanner(System.in);
        int x = this.sqlConnection.getObjectX(objID);
        int y = this.sqlConnection.getObjectY(objID);
        System.out.println("\nYour current coordinates are: " + x + ", " + y);
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
        if (checkNextSquare(x, y)){
            this.sqlConnection.setObjectColumnInt("x_axis", x, objID);
            this.sqlConnection.setObjectColumnInt("y_axis", y, objID);
        }

    }

    public boolean checkNextSquare(int newX, int newY) {
        String objectInNextSquare = this.sqlConnection.getObjectTypeBasedOnCoordinate(newX, newY);
        boolean allowMovement = true;
        if (objectInNextSquare.equals("ship")) {
            allowMovement = false;
            String shipInWay = Main.capitalize(this.sqlConnection.getObjectBasedOnCoordinate(newX, newY));
            System.out.println(shipInWay + " is in the way");
        } else if (objectInNextSquare.equals("harbor")){
            allowMovement = false;
            System.out.println("There is a " + objectInNextSquare + " in the way.");
        } else if (newX > this.size || newX < 1 || newY > this.size || newY < 1) {
            allowMovement = false;
            System.out.println("You can't leave the map");
        }

        return  allowMovement;
    }
}
