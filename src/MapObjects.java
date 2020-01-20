public class MapObjects {

    private String objectID;
    private String objectType;
    private int xAxis;
    private int yAxis;

    MapObjects(String objectID, String objectType, int xAxis, int yAxis) {
        this.objectID = objectID;
        this.objectType = objectType;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public int getxAxis() {
        return SQL.getObjectX(this.objectID);
    }

    public int getyAxis() {
        return SQL.getObjectY(this.objectID);
    }

    public String getObjectType() {
        return SQL.getObjectType(this.objectID);
    }

}
