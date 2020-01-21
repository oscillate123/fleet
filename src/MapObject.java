public class MapObject {

    private String objectID;
    private String objectType;
    private int xAxis;
    private int yAxis;

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

//påbörjat class för lassa/lossa
class LoadUnload{
    public int containerSum;

    LoadUnload(int containerSum){this.containerSum = containerSum;}

}