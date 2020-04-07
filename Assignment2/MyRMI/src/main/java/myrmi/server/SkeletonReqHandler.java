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
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Protocal666 stub = (Protocal666) ois.readObject();
            ois.close();

            int objectKey = stub.getObjectKey();
            String methodName = stub.getMethodName();
            Method[] objMethods = stub.getMethods();
            Method methodInvoked = null;
            for (Method m: objMethods){
                if (m.getName().equals(methodName)){
                    methodInvoked = m;
                }
            }
            Class<?>[] argTypes = methodInvoked.getParameterTypes();
            Object[] args = stub.getMethods();

            Class objClass = obj.getClass();
            Method objMethod = objClass.getDeclaredMethod(methodName, argTypes);
            Object result = objMethod.invoke(obj, args);

            ObjectOutputStream ops = new ObjectOutputStream(socket.getOutputStream());
            stub.setResult(result);
            ops.writeObject(stub);
            ops.close();

        }catch (IOException |
                ClassNotFoundException |
                NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException e){
            e.printStackTrace();
        }
    }
}
