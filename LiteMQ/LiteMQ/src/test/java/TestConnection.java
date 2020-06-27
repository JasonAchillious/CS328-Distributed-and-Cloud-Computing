import LiteMQ.broker.Connection;
import LiteMQ.broker.HeartBeatDaemon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestConnection {
    public static void main(String[] args) {
        Path brokersPath = Paths.get("./brokers");
        boolean brokersExists = Files.exists(brokersPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if (!brokersExists){
            try {
                Files.createDirectory(brokersPath);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        String serverName = "localhost";
        int port = 9090;
        int second = 10;

        try {
            Connection connection = new Connection(serverName, 7070);
            int brokerId  = connection.tryConnectZK();
            Path brokerPath = Paths.get(String.valueOf(brokersPath),"broker-"+brokerId);
            Files.deleteIfExists(brokerPath);
            Files.createDirectory(brokerPath);
            Thread hb = new Thread(new HeartBeatDaemon(serverName, port, brokerId, 1, 1));
            hb.setDaemon(true);
            hb.start();
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            Thread.sleep(second*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
