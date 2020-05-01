package myrmi.server;

import myrmi.Remote;
import myrmi.exception.RemoteException;

import static java.lang.Thread.sleep;


public class UnicastRemoteObject implements Remote, java.io.Serializable {
    int port;

    protected UnicastRemoteObject() throws RemoteException {
        this(0);
    }

    protected UnicastRemoteObject(int port) throws RemoteException {
        this.port = port;
        exportObject(this, port);
    }

    public static Remote exportObject(Remote obj) throws RemoteException {
        return exportObject(obj, 0);
    }

    public static Remote exportObject(Remote obj, int port) throws RemoteException{

        return exportObject(obj, "127.0.0.1", port);
    }

    /**
     * create skeleton on given host:port
     * returns a stub
     **/
    public static Remote exportObject(Remote obj, String host, int port) throws RemoteException {
        //TODO: finish here

        int objectKey = obj.hashCode();

        String Ifname = "Remote";
        Class[] objInterfaces = obj.getClass().getInterfaces();
        if (objInterfaces.length > 0){
            Ifname = objInterfaces[0].getName();
        }

        Skeleton skeleton = new Skeleton(obj, host, port, objectKey);
        skeleton.start();
        //System.out.println(port + "________________________");
        try {
            sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        int allocatedPort = skeleton.getPort();
        //System.out.println(allocatedPort + "***************************");
        RemoteObjectRef objRef = new RemoteObjectRef(host, allocatedPort, objectKey, Ifname);

        return Util.createStub(objRef);
    }
}
