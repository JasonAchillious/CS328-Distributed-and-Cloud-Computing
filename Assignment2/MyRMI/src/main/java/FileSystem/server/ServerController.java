package FileSystem.server;

import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;
import myrmi.server.UnicastRemoteObject;

public class ServerController {
    public static void main(String args[]) {
        int port = 1099;

        try {
            FileServerImpl robj = new FileServerImpl();
            IFileServer stub = (IFileServer) UnicastRemoteObject.exportObject(robj, 0);


            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("FileServer", stub);
            System.out.println("File Server is ready to listen...");

        } catch (Exception e) {
            System.err.println("Server exception thrown: " + e.toString());
            e.printStackTrace();
        }
    }
}
