package myrmi.server;

import myrmi.exception.RemoteException;
import myrmi.protocal.InfoFromSkeleton;
import myrmi.protocal.InfoFromStub;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
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

        Object result = null;

        Socket client = new Socket(this.host, this.port);
        /*
        Serializable[] arguments;
        try {
            arguments = (Serializable[]) args;
        }catch (Exception e){
            arguments=null;
            e.printStackTrace();
        }
        */
        InfoFromStub infoStub = new InfoFromStub(method.getName(), args, this.objectKey);
        ObjectOutputStream ops = new ObjectOutputStream(client.getOutputStream());
        ops.writeObject(infoStub);


        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        Object returnObj = ois.readObject();
        if (returnObj instanceof InfoFromSkeleton){

            InfoFromSkeleton invokedResult = (InfoFromSkeleton) returnObj;

            if (invokedResult.getObjectKey() != this.objectKey) throw new Exception("Wrong objectKey");

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
        //FileSystem.client.close();
        return result;
    }

}
