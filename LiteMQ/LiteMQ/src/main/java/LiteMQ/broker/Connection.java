package LiteMQ.broker;

import LiteMQ.broker.RemoteServices.IFileServer;
import com.google.gson.Gson;
import msgProtocal.BrokerInfo;
import msgProtocal.BrokerState;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Connection {
    private String serverName;
    private int brokerId = -1;
    private int port;

    private BrokerInfo brokerInfo;
    private Gson gson = new Gson();

    public Connection(String serverName, int port) throws UnknownHostException {
        this.serverName = serverName;
        this.port = port;
        InetAddress iadress = InetAddress.getLocalHost();
        this.brokerInfo = new BrokerInfo(this.brokerId,
                BrokerState.notStarted,
                iadress.getHostAddress(),
                -1, "null", -1);
    }

    public int tryConnectZK() throws IOException {
        Socket sock = new Socket(serverName, port);

        System.out.println("Try to connect the LiteZK");

        OutputStream outToServer = sock.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        String json = gson.toJson(this.brokerInfo);
        out.write(json.getBytes());
        out.flush();

        InputStream inFromServer = sock.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        String readStr = new String(in.readAllBytes());

        System.out.println(readStr);

        BrokerInfo biFromZK = gson.fromJson(readStr, BrokerInfo.class);

        this.brokerInfo.setRegistryHostName( biFromZK.getRegistryHostName() );
        this.brokerInfo.setRegistryPort( biFromZK.getRegistryPort() );
        brokerId = biFromZK.getBrokerId();
        this.brokerInfo.setBrokerId(brokerId);

        sock.close();
        return brokerId;
    }

    public boolean bindRemoteObj(Remote robj, String bindName, int exportPort) throws RemoteException {
        this.brokerInfo.setExportPort(exportPort);

        IFileServer stub = (IFileServer) UnicastRemoteObject.exportObject(robj, exportPort);
        String registryHostName = this.brokerInfo.getRegistryHostName();
        int registryPort = this.brokerInfo.getRegistryPort();

        Registry registry = LocateRegistry.getRegistry(registryHostName, registryPort);
        registry.rebind(bindName, stub);
        return true;
    }
}