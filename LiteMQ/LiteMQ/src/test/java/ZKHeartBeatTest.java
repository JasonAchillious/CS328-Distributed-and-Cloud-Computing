import LiteZK.ZKSupervisor;
import LiteZK.watcher.Observer;
import LiteZK.watcher.TestObserver;
import LiteZK.watcher.TimeCounter;

public class ZKHeartBeatTest {
    public static void main(String[] args) {
        int port = 9090;
        int maxLimit = 10;
        ZKSupervisor supervisor = new ZKSupervisor(port, maxLimit);

        TimeCounter timeCounter = new TimeCounter(1, maxLimit);
        //For test
        Observer observeBs = new TestObserver(timeCounter);
        timeCounter.attach(observeBs);
        supervisor.addTimeCounter(1, timeCounter);
        Thread tc = new Thread(timeCounter);
        tc.start();
        Thread t = new Thread(supervisor);
        t.setDaemon(true);
        t.start();

        try {
            Thread.sleep(25 * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}
