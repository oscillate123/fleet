
public class Ship {
    private String name;
    private int cargoCapacity;
    private int yAxis;
    private int xAxis;

    public Ship(String name, int cargoCapacity, int y, int x) {
        this.name = name;
        this.cargoCapacity = cargoCapacity;
        this.yAxis = y;
        this.xAxis = x;
    }

    public static void main(String[] args) {
        String tiles = "  Ship   | Cargocapacity | coordinates";
        Ship myShip = new Ship("ship one", 100,1, 1);
        System.out.println(tiles);
        System.out.println(myShip.name + " |      " + myShip.cargoCapacity + "      |  " + "x:"+myShip.xAxis + " y:"+myShip.yAxis);
    }
}