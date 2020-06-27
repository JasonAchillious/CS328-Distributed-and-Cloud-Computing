package LiteZK;

import LiteZK.watcher.Observer;
import LiteZK.watcher.TestObserver;
import LiteZK.watcher.TimeCounter;

public class Main {
    public static void main(String[] args) {
        int port = 9090;
        int maxLimit = 10;
        ZKSupervisor supervisor = new ZKSupervisor(port, maxLimit);

        TimeCounter timeCounter = new TimeCounter(1,maxLimit);
        //For test
        Observer observeBs = new TestObserver(timeCounter);
        timeCounter.attach(observeBs);
        supervisor.addTimeCounter(1, timeCounter);
        Thread tc = new Thread(timeCounter);
        tc.start();
        Thread t = new Thread(supervisor);
        t.setDaemon(true);
        t.start();


        System.out.println("Done.");
    }
}
