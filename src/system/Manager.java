package system;

import handlers.Elevator;
import request.*;
import utills.Pair;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager implements Runnable {
    private ConcurrentLinkedQueue<Pair<Request, RequestCallback>> orders;
    private List<Elevator> elevatorList;

    public Manager(List elevatorList) {
        this.orders = new ConcurrentLinkedQueue<>();
        this.elevatorList = elevatorList;
        ExecutorService executor = Executors.newFixedThreadPool(this.elevatorList.size());
        for (Elevator elevator : this.elevatorList) {
            executor.execute(elevator);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!orders.isEmpty()) {
                Pair<Request, RequestCallback> currentOrder = orders.poll();
                Request currentRequest = currentOrder.getKey();
                RequestCallback currentRequestCallBack = currentOrder.getValue();
                Elevator elevator = findAvailableElevator(currentRequest);
                elevator.addDestination(currentRequest);
                currentRequestCallBack.printHandler(" ((( " + elevator.getId() + " ))) ");
            }
        }
    }

    private Elevator findAvailableElevator(Request request) {
        ElevatorRequest elevatorRequest = (ElevatorRequest) request;
        for (Elevator elevator : this.elevatorList) {
            if (elevator.getDestinationFloors().isEmpty()) {
                return elevator;
            }
            // request come from a floor
            // between current elevator floor and current elevator destination
            if ((elevator.getCurrentFloor() < elevatorRequest.getSourceFloor() &&
                    elevator.getDestinationFloors().get(0) > elevatorRequest.getSourceFloor())
                    || (elevator.getCurrentFloor() > elevatorRequest.getSourceFloor() &&
                    elevator.getDestinationFloors().get(0) < elevatorRequest.getSourceFloor())) {
                return elevator;
            }
        }
        Random r = new Random();
        return elevatorList.get(r.nextInt(elevatorList.size()));
    }

    public void addRequestToQueue(Request request, RequestCallback callback) {
        System.out.println("add request to manager queue");
        orders.add(new Pair<>(request, callback));
    }


}
