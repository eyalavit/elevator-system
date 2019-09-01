package request;

public class ElevatorRequest implements Request {
    private int floorSource;
    private int floorDistination;

    public ElevatorRequest(int floorSource, int floorDestination) {
        this.floorSource = floorSource;
        this.floorDistination = floorDestination;
    }

    public int getFloorSource() {
        return floorSource;
    }

    public void setFloorSource(int floorSource) {
        this.floorSource = floorSource;
    }

    public int getFloorDistination() {
        return floorDistination;
    }

    public void setFloorDistination(int floorDistination) {
        this.floorDistination = floorDistination;
    }
}
