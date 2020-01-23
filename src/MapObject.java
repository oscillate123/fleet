import java.sql.SQLException;

public class MapObject {

    protected SQL SQL;
    protected String objectID;
    protected String objectType;

    MapObject(String objectID, boolean isNew, SQL sqlConnection) throws SQLException {
        this.SQL = sqlConnection;

        if (isNew) {
            // if isNew = true; we create a new post in the database
            SuppFunc.print_string("Please provide information about the " + objectID + " below:");
            String objectType = SuppFunc.getStringInput("Object type: ");
            int containerSum = Integer.parseInt(SuppFunc.getStringInput("Container Amount: "));
            int xAxis = Integer.parseInt(SuppFunc.getStringInput("X Axis: "));
            int yAxis = Integer.parseInt(SuppFunc.getStringInput("Y Axis: "));
            SQL.createNewObject(objectID, objectType, 0, containerSum, xAxis, yAxis);

        } else {
            // if isNew = false; we use the class methods for retrieving the data
            this.objectID = objectID;
            this.objectType = SQL.getObjectType(this.objectID);
        }

    }

    // setters

    protected void setContainerAmount(int newValue) { SQL.setObjectColumnInt(SQL.qObjConSum, newValue, this.objectID); }

    protected void setIsDocked(int binary) { SQL.setObjectColumnInt(SQL.qObjDocked, binary, this.objectID); }

    // getters

    protected boolean getIsDocked() { return (SQL.getObjectPostInt(this.objectID, this.SQL.qObjDocked) == 1); }

    protected int getXAxis() { return SQL.getObjectX(this.objectID); }

    protected int getYAxis() { return SQL.getObjectY(this.objectID); }

    protected String getObjectType() { return SQL.getObjectType(this.objectID); }

    protected int getContainerAmount() { return SQL.getObjectPostInt(this.objectID, SQL.qObjConSum); }

}


class Harbor extends MapObject {
    // class for creating a new harbor object in java

    Harbor(String objectID, boolean isNew, SQL sqlConnection) throws SQLException {
        super(objectID, isNew, sqlConnection);
    }
}


class Ship extends MapObject {
    // class for creating a new ship object in java

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
