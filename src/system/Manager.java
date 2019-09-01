package system;

import handlers.Elevator;
import request.Request;
import request.RequestCallback;
import utills.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager implements Runnable {
    private ConcurrentLinkedQueue<Pair<Request, RequestCallback>> orders;
    private List<Elevator> elevatorList;
    private Map<Integer, List<Integer>> elevatorIdToFloorDestination;

    public Manager() {
        this.orders = new ConcurrentLinkedQueue<>();
        this.elevatorList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Elevator elevator = new Elevator(1);
        elevatorList.add(elevator);
        executor.execute(elevator);
    }

    @Override
    public void run() {
        while (true){
            if(!orders.isEmpty()){
               Pair currentOrder = orders.poll();
               Request currentRequest = (Request) currentOrder.getKey();
               RequestCallback currentRequestCallBack = (RequestCallback) currentOrder.getValue();
               Elevator elevator =  findAvailableElevator(currentRequest);
               elevator.addDestination(currentRequest);
               currentRequestCallBack.printHandler("elevator with id " + elevator.getId());
            }
        }
    }

    private Elevator findAvailableElevator(Request request) {
        return this.elevatorList.get(0);
    }

    public void addRequestToQueue(Request request, RequestCallback callback){
        System.out.println("add request to manager queue");
        orders.add(new Pair<>(request, callback));

    }



}
