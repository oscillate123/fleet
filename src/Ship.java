
public class Ship extends MapObject {

    int containerAmount;

    Ship(String objectID, String objectType, int xAxis, int yAxis, int containerAmount) {
        super(objectID, objectType, xAxis, yAxis);
        this.containerAmount = containerAmount;
    }

}