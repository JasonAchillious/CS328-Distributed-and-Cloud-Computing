package LiteZK;

import LiteZK.watcher.DeleteIdObserver;
import LiteZK.watcher.TimeCounter;
import com.google.gson.Gson;
import msgProtocal.BrokerState;
import msgProtocal.HeartBeatInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ZKSupervisor implements Runnable {
    private static Logger logger = Logger.getLogger("ZKSupervisor");
    private int port;
    private Gson gson = new Gson();
    private ConcurrentHashMap<Integer, TimeCounter> timeCounters = new ConcurrentHashMap<>();
    private Path watchPath;
    private int maxLimit;


    public ZKSupervisor(int port, int maxLimit){
        this.port = port;
        this.watchPath = Paths.get("./LiteZK/brokers/ids");
        this.maxLimit = maxLimit;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean addTimeCounter(int brokerId, TimeCounter tc){
        if (timeCounters.containsKey(brokerId)) return false;
        timeCounters.putIfAbsent(brokerId, tc);
        String addInfo = String.format("Add the time counter of broker-%d to the supervisor", brokerId);
        logger.info(addInfo);
        return true;
    }

    public boolean isBrokerValid(HeartBeatInfo hbi){
        if (hbi.getState() != BrokerState.valid){
            return false;
        }
        return true;
    }


    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));

            System.out.println("------------------------Start Supervisor------------------------");

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            watchPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
            while (true){
                int num = selector.select();
                //System.out.printf("num of channel get ready: %d\n", num);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    try{
                        if (selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel client = null;

                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()){
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuf = ByteBuffer.allocate(512);
                            int readNum = channel.read(readBuf);
                            if (readNum > 0){
                                readBuf.flip();
                                byte[] data = new byte[readBuf.limit()];
                                readBuf.get(data);
                                String json = new String(data);

                                //System.out.println(json);

                                HeartBeatInfo hbi = gson.fromJson(json, HeartBeatInfo.class);
                                if (!isBrokerValid(hbi)){
                                    System.out.printf("brokerId: %d, broker state: %s"
                                            , hbi.getBrokerId(), hbi.getState());
                                    // Do something.
                                }else {
                                    int brokerId = hbi.getBrokerId();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(32);
                                    writeBuffer.put(("Ack").getBytes());
                                    writeBuffer.flip();
                                    while (writeBuffer.hasRemaining())
                                        channel.write(writeBuffer);
                                    timeCounters.get(brokerId).setState(0);
                                }
                            }else if (readNum == -1) channel.close();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();

                WatchKey watchKey = watchService.poll();
                if (watchKey != null){
                    for (WatchEvent event: watchKey.pollEvents()){
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                            logger.info("detect that a broker has been create.");
                            String fileName = event.context().toString();
                            //System.out.println(fileName);
                            fileName = fileName.substring(0, fileName.lastIndexOf("."));

                            int brokerId = Integer.parseInt(fileName);

                            TimeCounter tc = new TimeCounter(brokerId, this.maxLimit);
                            DeleteIdObserver dib = new DeleteIdObserver(tc);
                            tc.attach(dib);
                            this.addTimeCounter(brokerId, tc);
                            Thread t = new Thread(this.timeCounters.get(brokerId));
                            t.start();
                            logger.info("broker-" + brokerId + " start to timing.");
                        }else if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                            logger.info("detect that a broker has been delete.");
                            //System.out.println(event.context());

                            String fileName = event.context().toString();
                            fileName = fileName.substring(0, fileName.lastIndexOf("."));
                            int brokerId = Integer.parseInt(fileName);

                            this.timeCounters.remove(brokerId);
                            logger.info("The timer of" + " broker-" + brokerId + " has been removed.");
                        }
                    }
                    watchKey.reset();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
