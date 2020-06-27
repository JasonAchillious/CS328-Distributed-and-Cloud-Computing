import LiteMQ.broker.HeartBeatDaemon;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MQHeartBeatTest {
    public static void main(String[] args) {
        int brokerId = 1;
        Path path = Paths.get("broker-"+brokerId);


        String serverName = "localhost";
        int port = 9090;
        int second = 12;

        Thread hb = new Thread(new HeartBeatDaemon(serverName, port, brokerId, 1, 3));
        hb.setDaemon(true);
        hb.start();

        try {
            Thread.sleep(second * 1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
