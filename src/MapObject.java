public class MapObject {

    protected String objectID;
    protected String objectType;
    protected int xAxis;
    protected int yAxis;

    MapObject(String objectID, String objectType, int xAxis, int yAxis) {
        SQL.createNewObject(objectID, objectType, 0, 0, xAxis, yAxis);
        this.objectID = objectID;
        this.objectType = SQL.getObjectType(this.objectID);
        this.xAxis = SQL.getObjectX(this.objectID);
        this.yAxis = SQL.getObjectY(this.objectID);
    }


    public int getxAxis() { return SQL.getObjectX(this.objectID); }

    public int getyAxis() {
        return SQL.getObjectY(this.objectID);
    }

    public String getObjectType() {
        return SQL.getObjectType(this.objectID);
    }

}

class Ocean extends MapObject {

    Ocean(String objectID, String objectType, int xAxis, int yAxis, int isDocked){
        super(objectID, objectType, xAxis, yAxis);
        SQL.setObjectColumnInt(SQL.qObjDocked, isDocked, this.objectID);
    }

}

class Ship extends MapObject {

    Ship(String objectID, String objectType, int xAxis, int yAxis, int isDocked, int containerSum) {
        super(objectID, objectType, xAxis, yAxis);
        SQL.setObjectColumnInt(SQL.qObjDocked, isDocked, this.objectID); // sets docked status (0 or 1)
        SQL.setObjectColumnInt(SQL.qObjConSum, containerSum, this.objectID); // sets container amount
    }

    public void updateContainerAmount(int newValue) {
        SQL.setObjectColumnInt(SQL.qObjConSum, newValue, this.objectID);
    }

    public int getContainerAmount() {
        return SQL.getObjectPostInt(this.objectID, SQL.qObjConSum);
    }

    public boolean getIsDocked() {
        return (SQL.getObjectPostInt(this.objectID, SQL.qObjDocked) == 1);
    }
}

//påbörjat class för lassa/lossa
class LoadUnload{
    public int containerSum;

    LoadUnload(int containerSum){this.containerSum = containerSum;}

}