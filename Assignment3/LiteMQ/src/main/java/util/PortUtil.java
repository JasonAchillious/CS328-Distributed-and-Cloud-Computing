package util;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class PortUtil {
    public static int getAvailablePort(int port) {
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.close();
            return port;
        } catch (IOException e) {
            return getAvailablePort(port + 1);
        }
    }

}
