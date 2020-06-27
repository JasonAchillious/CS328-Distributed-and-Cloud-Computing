package LiteZK;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import msgProtocal.BrokerInfo;
import msgProtocal.BrokerInfoDeserializer;
import msgProtocal.BrokerState;
import util.*;

public class ZKRegisty implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private Gson gson =
            new GsonBuilder().registerTypeAdapter(BrokerInfo.class, new BrokerInfoDeserializer()).create();
    private IdGenerator idGenerator = new IdGenerator();
    Logger logger = Logger.getLogger("ZK-registry");
    private Map<Integer, Registry> registries;
    private static final String logPath = "./LiteZK/log/ZK-registry";
    private static final String brokerIdPath = "./LiteZK/brokers/ids";

    public ZKRegisty(int port) throws IOException {
        this.port = port;
        registries = new ConcurrentHashMap<>();
        FileHandler fileHandler = new FileHandler(logPath);
        logger.addHandler(fileHandler);
        serverSocket = new ServerSocket(this.port);
        logger.info("Start to listen the connection requests from the broker.");
    }

    public boolean createRegistry(int brokerId, int port) throws RemoteException {
        Registry wr = LocateRegistry.createRegistry(port);
        if (!registries.containsKey(brokerId)) {
                    registries.put(brokerId, wr);
                    logger.info("successful add the registry for broker"
                    + brokerId + " at port-" + port);
                    return true;
        } else {
                    String info = "Broker " + brokerId
                            + " already has a registry for writing.";
                    logger.warning(info);
                    return false;
        }
    }

    public int createRegistry(int brokerId) throws RemoteException {
        int port = PortUtil.getAvailablePort(20000);
        if (createRegistry(brokerId,port)){
            return port;
        }
        return -1;
    }



    @Override
    public void run() {
        while(true)
        {
            try {
                Socket server = serverSocket.accept();

                DataInputStream in = new DataInputStream(server.getInputStream());
                byte[] buf = new byte[1024];
                int readNum = in.read(buf);
                //System.out.println(readNum);
                String brokerInfoJson = new String(buf, 0, readNum);

                logger.info(brokerInfoJson);
                //System.out.println(brokerInfoJson);

                BrokerInfo brokerInfo = gson.fromJson(brokerInfoJson, BrokerInfo.class);


                if (brokerInfo.getBrokerId() == -1
                        && brokerInfo.getBrokerState() == BrokerState.notStarted) {
                    int brokerId = idGenerator.getNextId();
                    logger.info(server.getRemoteSocketAddress().toString()
                            + "[a new broker connected]");
                    int registryPort = this.createRegistry(brokerId);
                    brokerInfo.setBrokerId(brokerId);
                    brokerInfo.setBrokerState(BrokerState.valid);
                    brokerInfo.setRegistryHostName(InetAddress.getLocalHost().getHostAddress());
                    brokerInfo.setRegistryPort(registryPort);

                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    String biJson = gson.toJson(brokerInfo);
                    out.write(biJson.getBytes());
                    out.flush();

                    logger.info(server.getRemoteSocketAddress().toString()
                            + "[assign a broker-" + brokerId + " and create corresponding registry.]");
                    Path idPath = Paths.get(brokerIdPath, brokerId + ".zk");
                    Files.createFile(idPath);
                }
                server.close();

            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
