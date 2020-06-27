import LiteZK.ZKRegisty;
import LiteZK.ZKSupervisor;
import util.PathUtil;
import util.PortUtil;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestRegistry {
    public static void main(String[] args) {
        int supervisorPort = 9090;
        int maxLimit = 10;

        Path brokersPath = Paths.get("./LiteZK");
        boolean brokersExists = Files.exists(brokersPath, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
        if (brokersExists){
            PathUtil.deleteDir(new File("./LiteZK"));
            System.out.println("Successfully delete ./LiteZK");
        }
        try {
            Files.createDirectory(brokersPath);
            Files.createDirectories(Paths.get("./LiteZK/brokers"));
            Files.createDirectories(Paths.get("./LiteZK/brokers/ids"));
            Files.createDirectories(Paths.get("./LiteZK/admin"));
            Files.createDirectories(Paths.get("./LiteZK/log"));

        }catch (IOException e){
            e.printStackTrace();
        }
        ZKSupervisor supervisor = new ZKSupervisor(supervisorPort, maxLimit);
        Thread tSup = new Thread(supervisor);
        tSup.setDaemon(true);
        tSup.start();

        try {
            ZKRegisty zkRegisty = new ZKRegisty(7070);
            zkRegisty.run();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Done.");
        //PortUtil.deleteDir(new File("./LiteZK"));
    }
}
