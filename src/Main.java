import handlers.Elevator;
import request.ElevatorRequest;
import system.Manager;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int NUMBER_OF_ELEVATORS = 1;
        List<Elevator> elevatorList = createElevators(NUMBER_OF_ELEVATORS);

        Manager manager = new Manager();
        Thread thread = new Thread(manager);
        thread.start();
        sendRequestsToManager(manager);
        thread.join();
    }

    private static void sendRequestsToManager(Manager manager) {
        try {
            manager.addRequestToQueue(new ElevatorRequest(0, 10), (s) -> {
                System.out.println("elevator " + s + " will handle floor 0");
            });
            Thread.sleep(1000);
            manager.addRequestToQueue(new ElevatorRequest(4, 2), (s) -> {
                System.out.println("elevator " + s + " will handle floor 4");
            });
//            Thread.sleep(2000);
//            manager.addRequestToQueue(new ElevatorRequest(5, 7), (s) -> {
//                System.out.println("elevator " + s + " will handle floor 5");
//            });
        }catch (Exception e){
            System.out.println("***FAIL*** " + e + " in sendRequestsToManager");
        }
    }

    private static List<Elevator> createElevators(int number_of_elevators) {
        return null;
    }
}
