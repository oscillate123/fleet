import java.sql.SQLException;

public class MapObject {

    protected SQL SQL;
    protected String objectID;
    protected String objectType;
    protected int xAxis;
    protected int yAxis;
    protected boolean isDocked;

    MapObject(String objectID, String objectType, int xAxis, int yAxis, boolean isNew) throws SQLException {
        this.SQL = new SQL();
        if (isNew) {
            SQL.createNewObject(objectID, objectType, 0, 0, xAxis, yAxis);
        } else {
            this.objectID = objectID;
            this.objectType = SQL.getObjectType(this.objectID);
            this.isDocked = (SQL.getObjectPostInt(this.objectID, SQL.qObjDocked) == 1);
            this.xAxis = SQL.getObjectX(this.objectID);
            this.yAxis = SQL.getObjectY(this.objectID);
        }
    }

    public boolean getIsDocked() { return this.isDocked; }

    public int getxAxis() { return SQL.getObjectX(this.objectID); }

    public int getyAxis() { return SQL.getObjectY(this.objectID); }

    public String getObjectType() { return this.objectType; }

}

class Ocean extends MapObject {

    Ocean(String objectID,
          String objectType,
          int isDocked,
          int xAxis,
          int yAxis,
          boolean isNew) throws SQLException {

        super(objectID, objectType, xAxis, yAxis, isNew);
        SQL.setObjectColumnInt(SQL.qObjDocked, isDocked, this.objectID);
    }

}

class Ship extends MapObject {

    // get and set methods further down
    private int containerSum = SQL.getObjectPostInt(this.objectID, SQL.qObjConSum);

    Ship(String objectID,
         String objectType,
         int isDocked,
         int containerSum,
         int xAxis,
         int yAxis,
         boolean isNew) throws SQLException {

        super(objectID, objectType, xAxis, yAxis, isNew);

        if (isNew) {
            SQL.setObjectColumnInt(SQL.qObjDocked, isDocked, this.objectID); // sets docked status (0 or 1)
            SQL.setObjectColumnInt(SQL.qObjConSum, containerSum, this.objectID); // sets container amount
        }
    }

    public void setContainerAmount(int newValue) { SQL.setObjectColumnInt(SQL.qObjConSum, newValue, this.objectID); }

    public int getContainerAmount() { return this.containerSum; }

}

//påbörjat class för lassa/lossa
class LoadUnload{
    public int containerSum;

    LoadUnload(int containerSum){this.containerSum = containerSum;}

}