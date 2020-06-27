package LiteMQ.broker;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
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
        int second = 12;

        try {
            Connection connection = new Connection(serverName, 7070);
            int brokerId  = connection.tryConnectZK();
            Path brokerPath = Paths.get(String.valueOf(brokersPath),"broker-"+brokerId);
            Files.createDirectory(brokerPath);
            Thread hb = new Thread(new HeartBeatDaemon(serverName, port, brokerId, 1, 1));
            hb.setDaemon(true);
            hb.start();
        } catch (IOException e){
            e.printStackTrace();
        }



    }
}
