package myrmi.server;

import myrmi.Remote;
import myrmi.protocal.Protocal666;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class SkeletonReqHandler extends Thread {
    private Socket socket;
    private Remote obj;
    private int objectKey;

    public SkeletonReqHandler(Socket socket, Remote remoteObj, int objectKey) {
        this.socket = socket;
        this.obj = remoteObj;
        this.objectKey = objectKey;
    }

    @Override
    public void run() {
        /*TODO: implement method here
         * You need to:
         * 1. handle requests from stub, receive invocation arguments
         * 2. get result by calling the real object, and handle different cases (non-void method, void method, method throws exception, exception in invocation process)
         * Hint: you can use a int to represent the cases: -1 invocation error, 0 exception thrown,
         * 1 void method, 2 non-void method
         *
         *  */
        Object stub = null;
        Object result = null;

        Protocal666 invocationResult = new Protocal666(this.objectKey);
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            stub = ois.readObject();
            ois.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            invocationResult.setException(e);
        }

        if (stub != null && stub instanceof Protocal666) {
            int objectKey = ((Protocal666) stub).getObjectKey();
            Method method = ((Protocal666) stub).getMethod();
            Object[] args = ((Protocal666) stub).getArgs();
            invocationResult.setObjectKey(objectKey);
            invocationResult.setReturnType(method.getReturnType());

            try {
                if (objectKey == this.objectKey) {
                    result = method.invoke(obj, args);
                    if (result == null) {
                        invocationResult.setStatus(1);
                    }else {
                        invocationResult.setStatus(2);
                    }
                }else {
                    invocationResult.setException(new Exception("Wrong object key"));
                    invocationResult.setStatus(-1);
                }
            }catch (Exception e) {
                e.printStackTrace();
                invocationResult.setStatus(-1);
                invocationResult.setException(e);
            }
        }else {
            invocationResult.setException( new Exception("Stub is null or is not instance of the protocal."));
        }

        try {
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            invocationResult.setResult(result);
            if (invocationResult.getStatus() != -1 && invocationResult.getException() != null){
                invocationResult.setStatus(0);
            }
            ops.writeObject(invocationResult);
            ops.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
