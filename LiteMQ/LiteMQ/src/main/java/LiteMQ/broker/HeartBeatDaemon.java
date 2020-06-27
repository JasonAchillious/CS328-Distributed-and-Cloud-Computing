package LiteMQ.broker;

import com.google.gson.Gson;
import msgProtocal.BrokerState;
import msgProtocal.HeartBeatInfo;

import java.io.*;
import java.net.Socket;

public class HeartBeatDaemon implements Runnable {
    private int brokerId;
    private int version;
    private String serverName;
    private int port;
    private int timeout;
    private int interval;
    private int timesTry;
    private Gson gson = new Gson();

    public HeartBeatDaemon(String serverName, int port, int brokerId, int version, int interval){
        this.serverName = serverName;
        this.port = port;
        this.brokerId = brokerId;
        this.version = version;
        this.interval = interval;
    }

    @Override
    public void run() {
        try {
            while (true){
                Socket socket = new Socket(serverName, port);
                HeartBeatInfo hbi = new HeartBeatInfo(version,
                        brokerId,
                        System.currentTimeMillis(),
                        BrokerState.valid);
                String json = gson.toJson(hbi);
                OutputStream outToServer = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.write(json.getBytes());
                socket.shutdownOutput();

                InputStream inFromServer = socket.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                String readStr = new String(in.readAllBytes());
                if (!readStr.equals("Ack")) reconnect();
                Thread.sleep(interval*1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    public void reconnect(){
        System.out.println("Did not recieve the right 'ack' .");
    }
}
