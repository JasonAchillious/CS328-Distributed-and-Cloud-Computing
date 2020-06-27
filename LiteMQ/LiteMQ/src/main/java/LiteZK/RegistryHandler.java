package LiteZK;

import com.google.gson.Gson;
import msgProtocal.BrokerInfo;

import java.io.*;
import java.net.Socket;

public class RegistryHandler extends Thread{
    Socket sock;
    Gson gson = new Gson();
    int brokerId;

    public RegistryHandler(Socket sock, int brokerId) {
        this.sock = sock;
        this.brokerId = brokerId;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {
            try {
                this.sock.close();
            } catch (IOException ioe) {}
            System.out.println("broker disconnected.");
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        String brokerInfoJson = new String(input.readAllBytes());
        BrokerInfo brokerInfo = gson.fromJson(brokerInfoJson, BrokerInfo.class);
        brokerInfo.setBrokerId(this.brokerId);
    }
}
