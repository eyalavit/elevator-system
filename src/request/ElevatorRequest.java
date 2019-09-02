package request;

public class ElevatorRequest implements Request {
    private int sourceFloor;
    private int destinationFloor;

    public ElevatorRequest(int floorSource, int floorDestination) {
        this.sourceFloor = floorSource;
        this.destinationFloor = floorDestination;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

}
