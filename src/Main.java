import handlers.Elevator;
import request.ElevatorRequest;
import request.Request;
import request.RequestCallback;
import system.Manager;
import utills.Pair;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int NUMBER_OF_ELEVATORS = 2;
        List<Elevator> elevatorList = createElevators(NUMBER_OF_ELEVATORS);
        Manager manager = new Manager(elevatorList);
        Thread thread = new Thread(manager);
        thread.start();
        sendRequestsToManager(manager);
        thread.join();
    }

    private static void sendRequestsToManager(Manager manager) {
        try {
            Pair<Request, RequestCallback> r1 = createElevatorRequest(4, 10);
            manager.addRequestToQueue(r1.getKey(), r1.getValue());
            Thread.sleep(10000);

            Pair<Request, RequestCallback> r2 = createElevatorRequest(3, 2);
            manager.addRequestToQueue(r2.getKey(), r2.getValue());
            Thread.sleep(1000);

            Pair<Request, RequestCallback> r3 = createElevatorRequest(7, 1);
            manager.addRequestToQueue(r3.getKey(), r3.getValue());

        }catch (Exception e){
            System.out.println("***FAIL*** " + e + " in sendRequestsToManager");
        }
    }

    private static Pair<Request, RequestCallback> createElevatorRequest(int sourceFloor, int destinationFloor){
        return new Pair<>(
        new ElevatorRequest(sourceFloor, destinationFloor), (s) -> {
            System.out.println("elevator with id" + s + " will handle floor request " +
                    "from " + sourceFloor + " to floor " + destinationFloor);
        });
    }

    private static List<Elevator> createElevators(int number_of_elevators) {
        List<Elevator> res = new ArrayList<>(number_of_elevators);
        for (int id = 0; id < number_of_elevators; id++){
            res.add(new Elevator(id));
        }
        return res;
    }
}
