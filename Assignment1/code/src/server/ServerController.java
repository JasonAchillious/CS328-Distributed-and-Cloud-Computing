package server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerController {
    public static void main(String args[]) {

        try {
            FileServerImpl robj = new FileServerImpl();
            IFileServer stub = (IFileServer) UnicastRemoteObject.exportObject(robj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("FileServer", stub);
            System.out.println("File Server is ready to listen...");

        } catch (Exception e) {
            System.err.println("Server exception thrown: " + e.toString());
            e.printStackTrace();
        }
    }
}
