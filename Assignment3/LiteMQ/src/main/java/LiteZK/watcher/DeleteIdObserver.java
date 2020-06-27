package LiteZK.watcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteIdObserver extends Observer {
    private Path dirPath = Paths.get(".", "LiteZK", "brokers", "ids");

    public DeleteIdObserver(TimeCounter tc){
        this.subject = tc;
    }

    @Override
    public void update() {
        TimeCounter tcSubject = (TimeCounter) this.subject;
        Path filePath = dirPath.resolve(Paths.get(tcSubject.getBrokerId() + ".zk"));
        System.out.println(filePath);
        try {
            Files.delete(filePath);
            System.out.println("deleted");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
