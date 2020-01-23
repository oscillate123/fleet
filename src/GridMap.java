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
        Map<String, String> harborCoords = Main.getCoordMap("harbor", this.sqlConnection);
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

    public void autoMove(Ship ship, String destination, int container, Map<String, String> harborCoords) throws InterruptedException {
        int destX = this.sqlConnection.getObjectX(destination);
        int destY = this.sqlConnection.getObjectY(destination);
        int shipX = this.sqlConnection.getObjectX(ship.objectID);
        int shipY = this.sqlConnection.getObjectY(ship.objectID);

        foundDest:
        while (shipX != destX || shipY != destY) {
            shipX = this.sqlConnection.getObjectX(ship.objectID);
            shipY = this.sqlConnection.getObjectY(ship.objectID);
            Main.cls();
            if (shipX < destX)
                shipX++;
            else if (shipX > destX)
                shipX--;
            if (shipY < destY)
                shipY++;
            else if (shipY > destY)
                shipY--;
            while (!this.sqlConnection.getObjectTypeBasedOnCoordinate(shipX, shipY).equals("empty")){
                if (this.sqlConnection.getObjectTypeBasedOnCoordinate(shipX, shipY).equals("harbor") && this.sqlConnection.getObjectIdBasedOnCoordinate(shipX, shipY).equals(destination)) {
                    System.out.println("Destination reached, " + container + " containers.");
                    int oldSum = ship.getContainerAmount();
                    int sum = oldSum + container;
                    ship.setContainerAmount(sum);
                    break foundDest;
                } else {
                    if (shipY == this.size) {
                        shipY--;
                    } else if (shipY == 1) {
                        shipY++;
                    } else if (shipX == this.size) {
                        shipX--;
                    } else {
                        shipX++;
                    }
                }
            }

            this.sqlConnection.setObjectColumnInt("x_axis", shipX, ship.objectID);
            this.sqlConnection.setObjectColumnInt("y_axis", shipY, ship.objectID);

            System.out.println("On my way to " + destination);
            this.drawMap();
        }

    }

    public boolean updateCord(Ship myShip){
        boolean returnStatement = true;
        Scanner input = new Scanner(System.in);
        int x = this.sqlConnection.getObjectX(myShip.objectID);
        int y = this.sqlConnection.getObjectY(myShip.objectID);
        System.out.println("\nYour current coordinates are: " + x + ", " + y);
        System.out.print("Move ship (N, NW, W, SW, S, SE, E, NE) or write exit to stop: ");
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
            case "EXIT":
                returnStatement = false;
                break;
        }
        if (checkNextSquare(x, y)){
            this.sqlConnection.setObjectColumnInt("x_axis", x, myShip.objectID);
            this.sqlConnection.setObjectColumnInt("y_axis", y, myShip.objectID);
        }
        return returnStatement;
    }


    public boolean checkNextSquare(int newX, int newY) {
        String objectInNextSquare = this.sqlConnection.getObjectTypeBasedOnCoordinate(newX, newY);
        boolean allowMovement = true;
        if (objectInNextSquare.equals("ship")) {
            allowMovement = false;
            String shipInWay = Main.capitalize(this.sqlConnection.getObjectIdBasedOnCoordinate(newX, newY));
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
