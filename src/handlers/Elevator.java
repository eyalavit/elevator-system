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
                while (!this.destinationFloors.isEmpty()) {
                    int currentDestination = this.destinationFloors.get(0);
                    while (this.currentFloor != currentDestination) {
                        if (this.currentFloor < currentDestination) {
                            goUp();
                        }else{
                            goDown();
                        }
                    }
                    this.destinationFloors.remove(0);
                    openDoors();
                    closeDoors();
                }
                System.out.println("elevator number " + this.id + " wait for orders ");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            System.out.println("elevator " + this.id + "***FAIL*** " + e);
        }
    }

    public void openDoors() {
        try {
            System.out.println("elevator number " + this.id + " opening doors at floor " + this.currentFloor);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("elevator number " + this.id + " ***FAIL*** opening doors at floor " + this.currentFloor);
        }
    }

    public void closeDoors() {
        try {
            System.out.println("elevator number " + this.id + " closing doors at floor " + this.currentFloor);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("elevator number " + this.id + " ***FAIL*** closing doors at floor " + this.currentFloor);
        }
    }

    public void goUp() {
        try {
            System.out.println("elevator number " + this.id + " going up from " + this.currentFloor + " to " + this.currentFloor++);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("elevator number " + this.id + " ***FAIL*** go up at floor " + this.currentFloor);
        }
    }

    public void goDown() {
        if (this.currentFloor > 0) {
            try {
                System.out.println("elevator number " + this.id + " going down from " + this.currentFloor + " to " + this.currentFloor--);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("elevator number " + this.id + " ***FAIL*** go down at floor " + this.currentFloor);
            }
        } else {
            System.out.println("elevator number " + this.id + " cant go down any farther");
        }
    }

    public void addDestination(Request request){
        //TODO: fix logic, source floor need to be serve first...
        ElevatorRequest elevatorRequest = (ElevatorRequest) request;
        this.destinationFloors.add(elevatorRequest.getFloorSource());
        this.destinationFloors.add(elevatorRequest.getFloorDistination());
        Collections.sort(this.destinationFloors);
    }

    public int getId() {
        return id;
    }




}
