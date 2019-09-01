package system;

import system.Manager;

public class Floor implements Runnable {
    private Manager manager;

    public Floor(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
