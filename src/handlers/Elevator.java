package handlers;

import request.ElevatorRequest;
import request.Request;

import java.util.*;

public class Elevator implements Runnable {
    private int id;
    private int currentFloor;
    private List<Integer> destinationFloors;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.destinationFloors = new LinkedList<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this.destinationFloors) {
                    while (!this.destinationFloors.isEmpty()) {
                        int currentDestination = this.destinationFloors.get(0);
                        while (this.currentFloor != currentDestination) {
                            if (this.currentFloor < currentDestination) {
                                goUp();
                            } else {
                                goDown();
                            }
                        }
                        openDoors();
                        closeDoors();
                        this.destinationFloors.remove(0);
                    }
                    System.out.println("elevator number " + this.id + " wait for orders ");
                    this.destinationFloors.wait();
                }
            }
        } catch (Exception e) {
            System.out.println("elevator " + this.id + "***FAIL*** " + e);
        }
    }

    public void addDestination(Request request){
        ElevatorRequest elevatorRequest = (ElevatorRequest) request;
        this.destinationFloors.add (0, elevatorRequest.getSourceFloor());
        this.destinationFloors.add(elevatorRequest.getDestinationFloor());
        synchronized (this.destinationFloors) {
            this.destinationFloors.notify();
        }
    }

    private void openDoors() {
        try {
            System.out.println("elevator number " + this.id + " opening doors at floor " + this.currentFloor);
            this.destinationFloors.wait(2000);
        } catch (InterruptedException e) {
            System.out.println("elevator number " + this.id + " ***FAIL*** opening doors at floor " + this.currentFloor);
        }
    }

    private void closeDoors() {
        try {
            System.out.println("elevator number " + this.id + " closing doors at floor " + this.currentFloor);
            this.destinationFloors.wait(2000);
        } catch (InterruptedException e) {
            System.out.println("elevator number " + this.id + " ***FAIL*** closing doors at floor " + this.currentFloor);
        }
    }

    private void goUp() {
        try {
            System.out.println("elevator number " + this.id + " going up from " + this.currentFloor + " to " + ++this.currentFloor);
            this.destinationFloors.wait(2000);
        } catch (InterruptedException e) {
            System.out.println("elevator number " + this.id + " ***FAIL*** go up at floor " + this.currentFloor);
        }
    }

    private void goDown() {
        if (this.currentFloor > 0) {
            try {
                System.out.println("elevator number " + this.id + " going down from " + this.currentFloor + " to " + --this.currentFloor);
                this.destinationFloors.wait(2000);
            } catch (InterruptedException e) {
                System.out.println("elevator number " + this.id + " ***FAIL*** go down at floor " + this.currentFloor);
            }
        } else {
            System.out.println("elevator number " + this.id + " cant go down any farther");
        }
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public List<Integer> getDestinationFloors() {
        return destinationFloors;
    }
}
