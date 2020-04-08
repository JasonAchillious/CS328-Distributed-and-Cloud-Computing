package myrmi.server;

import myrmi.exception.RemoteException;
import myrmi.protocal.Protocal666;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class StubInvocationHandler implements InvocationHandler, Serializable {
    private String host;
    private int port;
    private int objectKey;

    public StubInvocationHandler(String host, int port, int objectKey) {
        this.host = host;
        this.port = port;
        this.objectKey = objectKey;
        System.out.printf("Stub created to %s:%d, object key = %d\n", host, port, objectKey);
    }

    public StubInvocationHandler(RemoteObjectRef ref) {
        this(ref.getHost(), ref.getPort(), ref.getObjectKey());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws RemoteException, IOException, ClassNotFoundException, Throwable {
        /*TODO: implement stub proxy invocation handler here
         *  You need to do:
         * 1. connect to remote skeleton, send method and arguments
         * 2. get result back and return to caller transparently
         * */
        //InetAddress address = InetAddress.getByName(host);
        Object result = null;
        Socket client = new Socket(this.host, this.port);
        Protocal666 prototcal = new Protocal666(method, args, this.objectKey);
        ObjectOutputStream ops = new ObjectOutputStream(client.getOutputStream());
        ops.writeObject(prototcal);
        ops.close();

        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        Object returnObj = ois.readObject();
        if (returnObj instanceof Protocal666){
            Protocal666 invokedResult = (Protocal666) returnObj;
            if (invokedResult.getException() != null){
                if (invokedResult.getStatus() == -1){
                    System.out.println("Invocation Error");
                }
                throw invokedResult.getException();
            }else {
                if (invokedResult.getStatus() == 2){
                    result = invokedResult.getResult();
                }else if (invokedResult.getStatus() == 1){
                    System.out.println("Successfully invoked");
                }
            }
        }
        ois.close();
        return result;
    }

}
