package LiteZK;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ZKController implements Runnable{
    private static Logger logger = Logger.getLogger(ZKController.class.getName());
    public static final String CONTROLLER = "/controller";
    private String host;
    private int port;
    private List<String> brokerIds;


    public ZKController(String host, int port){
        this.brokerIds = new ArrayList<>();
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {

    }
}
