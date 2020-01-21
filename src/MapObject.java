public class MapObject {

    protected String objectID;
    protected String objectType;
    protected int xAxis;
    protected int yAxis;

    MapObject(String objectID, String objectType, int xAxis, int yAxis) {
        this.objectID = objectID;
        this.objectType = objectType;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public int getxAxis() { return SQL.getObjectX(this.objectID); }

    public int getyAxis() {
        return SQL.getObjectY(this.objectID);
    }

    public String getObjectType() {
        return SQL.getObjectType(this.objectID);
    }

}

// påbörjat havclass /gunnar
class Ocean {
    public boolean isDocked;

    Ocean(boolean isDocked){
       this.isDocked = isDocked;
    }

}

class Ship extends MapObject {

    private boolean isDocked;

    Ship(String objectID, String objectType, int xAxis, int yAxis, boolean isDocked) {
        super(objectID, objectType, xAxis, yAxis);
        this.isDocked = isDocked;
    }

    public void updateContainerAmount(int newValue) {
        // TODO: SKA INTE VARA getObject, utan setObjectPostInt! ## vi vill ändra inte hämta ##
        SQL.getObjectPostInt(this.objectID, "container_sum");
    }

    public int getContainerAmount() {
        return SQL.getObjectPostInt(this.objectID, "container_sum");
    }

    public boolean isDocked() {
        return isDocked;
    }
}

//påbörjat class för lassa/lossa
class LoadUnload{
    public int containerSum;

    LoadUnload(int containerSum){this.containerSum = containerSum;}

}