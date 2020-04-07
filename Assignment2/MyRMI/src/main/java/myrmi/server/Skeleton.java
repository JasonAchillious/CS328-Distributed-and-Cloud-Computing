package myrmi.server;

import myrmi.Remote;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Skeleton extends Thread {
    static final int BACKLOG = 5;
    private Remote remoteObj;

    private String host;
    private int port;
    private int objectKey;

    public int getPort() {
        return port;
    }

    public Skeleton(Remote remoteObj, RemoteObjectRef ref) {
        this(remoteObj, ref.getHost(), ref.getPort(), ref.getObjectKey());
    }

    public Skeleton(Remote remoteObj, String host, int port, int objectKey) {
        super();
        this.remoteObj = remoteObj;
        this.host = host;
        this.port = port;
        this.objectKey = objectKey;
        this.setDaemon(false);
    }

    @Override
    public void run() {
        /*TODO: implement method here
         * You need to:
         * 1. create a server socket to listen for incoming connections
         * 2. use a handler thread to process each request (SkeletonReqHandler)
         *  */

        try {
            InetAddress address = InetAddress.getByName(host);
            ServerSocket serverSocket = new ServerSocket(port , BACKLOG, address);
            serverSocket.setSoTimeout(10000);

            while (true) {
                Socket socket = serverSocket.accept();
                Thread handler = new SkeletonReqHandler(socket, remoteObj, objectKey);
                handler.run();
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
