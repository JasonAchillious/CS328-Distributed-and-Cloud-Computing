package myrmi.registry;

import myrmi.Remote;
import myrmi.exception.AlreadyBoundException;
import myrmi.exception.NotBoundException;
import myrmi.exception.RemoteException;
import myrmi.server.Skeleton;


import java.net.InetAddress;
import java.util.*;

public class RegistryImpl implements Registry {
    //private String host;

    private final HashMap<String, Remote> bindings = new HashMap<>();

    /**
     * Construct a new RegistryImpl
     * and create a skeleton on given port
     **/
    public RegistryImpl(int port) throws RemoteException {
        Skeleton skeleton = new Skeleton(this, "127.0.0.1", port, 0);
        skeleton.start();
    }


    public Remote lookup(String name) throws RemoteException, NotBoundException {
        System.out.printf("RegistryImpl: lookup(%s)\n", name);
        //TODO: implement method here
        if (bindings.containsKey(name)){
            return bindings.get(name);
        }else {
            System.out.println("It has not such name of binding.");
            return null;
        }
    }

    public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException {
        System.out.printf("RegistryImpl: bind(%s)\n", name);

        //TODO: implement method here
        this.bindings.put(name, obj);

    }

    public void unbind(String name) throws RemoteException, NotBoundException {
        System.out.printf("RegistryImpl: unbind(%s)\n", name);

        //TODO: implement method here
        if (bindings.containsKey(name)){
            bindings.remove(name);
            System.out.println("unbind successfully");
        }else {
            System.out.printf("The registry has no such binding: %s\n", name);
        }

    }

    public void rebind(String name, Remote obj) throws RemoteException {
        System.out.printf("RegistryImpl: rebind(%s)\n", name);

        //TODO: implement method here
        bindings.put(name, obj);
    }

    public String[] list() throws RemoteException {
        //TODO: implement method here
        String[] list = new String[bindings.size()];
        int i = 0;
        for (Map.Entry<String, Remote> entry : bindings.entrySet()) {
            list[i] =  "Name: "
                    + entry.getKey()
                    + ", Class of the object: "
                    + entry.getValue().getClass().getSimpleName();
            i++;
        }
        return list;
    }

    public static void main(String args[]) {
        final int regPort = (args.length >= 1) ? Integer.parseInt(args[0])
                : Registry.REGISTRY_PORT;
        RegistryImpl registry;
        try {
            registry = new RegistryImpl(regPort);
        } catch (RemoteException e) {
            System.exit(1);
        }

        System.out.printf("RMI Registry is listening on port %d\n", regPort);

    }
}
