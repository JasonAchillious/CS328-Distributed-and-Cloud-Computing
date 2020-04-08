package myrmi.server;

import myrmi.Remote;
import myrmi.protocal.InfoFromSkeleton;
import myrmi.protocal.InfoFromStub;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

        InfoFromSkeleton invocationResult = new InfoFromSkeleton(this.objectKey);
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            stub = ois.readObject();
            //ois.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            invocationResult.setException(e.getMessage());
        }

        if (stub != null && stub instanceof InfoFromStub) {
            int objectKey = ((InfoFromStub) stub).getObjectKey();
            String method = ((InfoFromStub) stub).getMethod();
            Object[] args = ((InfoFromStub) stub).getArgs();


            try {
                if (objectKey == this.objectKey) {
                    boolean flag = false;
                    for (Method m : this.obj.getClass().getDeclaredMethods()) {
                        if (m.getName().equals(method)) {
                            result = m.invoke(obj, args);
                            invocationResult.setResult(result);
                            flag = true;
                        }
                    }
                    if (flag && result == null) {
                        invocationResult.setStatus(1);
                    }else if (flag){
                        invocationResult.setStatus(2);
                    }
                }else {
                    invocationResult.setException("Wrong object key");
                    invocationResult.setStatus(-1);
                }
            }catch (Exception e) {
                e.printStackTrace();
                invocationResult.setStatus(-1);
                invocationResult.setException(e.getMessage());
            }
        }else {
            invocationResult.setException("Stub is null or is not instance of the protocal.");
        }

        try {
            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            /*
            if (result instanceof Serializable) {
                invocationResult.setResult((Serializable) result);
            }else {
                invocationResult.setException("The result is not serializable");
                invocationResult.setStatus(0);
            }
            */

            if (invocationResult.getStatus() != -1 && invocationResult.getException() != null){
                invocationResult.setStatus(0);
            }

            ops.writeObject(invocationResult);
            //ops.close();
            this.socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
