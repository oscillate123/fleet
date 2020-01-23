import java.sql.SQLException;

public class MapObject {

    protected SQL SQL;
    protected String objectID;
    protected String objectType;
    protected boolean isDocked;
    protected int containerSum;
    protected int xAxis;
    protected int yAxis;
    protected boolean isBlocked = true;

    MapObject(String objectID, boolean isNew, SQL sqlConnection) throws SQLException {
        this.SQL = sqlConnection;

        if (isNew) {
            SuppFunc.print_string("Please provide information about the " + objectID + " below:");
            String objectType = SuppFunc.getStringInput("Object type: ");
            int containerSum = Integer.parseInt(SuppFunc.getStringInput("Container Amount: "));
            int xAxis = Integer.parseInt(SuppFunc.getStringInput("X Axis: "));
            int yAxis = Integer.parseInt(SuppFunc.getStringInput("Y Axis: "));
            SQL.createNewObject(objectID, objectType, 0, containerSum, xAxis, yAxis);

        } else {
            this.objectID = objectID;
            this.objectType = SQL.getObjectType(this.objectID);
            this.isDocked = (SQL.getObjectPostInt(this.objectID, SQL.qObjDocked) == 1);
            this.containerSum = SQL.getObjectPostInt(this.objectID, SQL.qObjConSum);
            this.xAxis = SQL.getObjectX(this.objectID);
            this.yAxis = SQL.getObjectY(this.objectID);
        }

    }

    public void setIsDocked(int binary) { SQL.setObjectColumnInt(SQL.qObjDocked, binary, this.objectID); }

    public boolean getIsDocked() { return (SQL.getObjectPostInt(this.objectID, this.SQL.qObjDocked) == 1); }

    public int getXAxis() { return SQL.getObjectX(this.objectID); }

    public int getYAxis() { return SQL.getObjectY(this.objectID); }

    public String getObjectType() { return SQL.getObjectType(this.objectID); }

    public void setContainerAmount(int newValue) { SQL.setObjectColumnInt(SQL.qObjConSum, newValue, this.objectID); }

    public int getContainerAmount() { return SQL.getObjectPostInt(this.objectID, SQL.qObjConSum); }

}


class Harbor extends MapObject {

    Harbor(String objectID, boolean isNew, SQL sqlConnection) throws SQLException {
        super(objectID, isNew, sqlConnection);
    }
}


class Ship extends MapObject {

    // get and set methods further down
    private int cruiseVelocity = 50;
    private int maxVelocity = 100;
    private int currentVelocity = this.maxVelocity;

    Ship(String objectID, boolean isNew, SQL sqlConnection) throws SQLException {

        super(objectID, isNew, sqlConnection);

        /*if (isNew) {
            SQL.setObjectColumnInt(SQL.qObjDocked, isDocked, this.objectID); // sets docked status (0 or 1)
            SQL.setObjectColumnInt(SQL.qObjConSum, containerSum, this.objectID); // sets container amount
        }*/
    }

    public int getCurrentVelocity() {
        return this.currentVelocity;
    }

    public void setCurrentVelocity(String maxOrCruise) {
        switch (maxOrCruise) {
            case "max":
                this.currentVelocity = this.maxVelocity;
                break;
            case "cruise":
                this.currentVelocity = this.cruiseVelocity;
                break;
            case "speedrun":
                this.currentVelocity = 1000;
                break;
        }
    }
}
