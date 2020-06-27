package server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServerController {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String stubName;
        int registryPort;
        int skeletonPort = 0;

        try {
            System.out.println("Please input the port of registry: ");
            registryPort = in.nextInt();

            System.out.println("Please input the name for binding the stub to registry: ");
            stubName = in.next();

            FileServerImpl robj = new FileServerImpl();
            IFileServer stub = (IFileServer) UnicastRemoteObject.exportObject(robj, skeletonPort);

            Registry registry = LocateRegistry.createRegistry(registryPort);
            registry.rebind(stubName, stub);

            System.out.println("File Server is ready to listen...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
