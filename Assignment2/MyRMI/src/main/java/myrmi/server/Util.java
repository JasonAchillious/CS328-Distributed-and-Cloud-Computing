package myrmi.server;

import myrmi.Remote;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class Util {

    public static Remote createStub(Remote obj, RemoteObjectRef ref) {
        //TODO: finish
        Class objClass = obj.getClass();
        Class[] itfs = objClass.getInterfaces();

        StubInvocationHandler handler = new StubInvocationHandler(ref);
        return (Remote) Proxy.newProxyInstance(objClass.getClassLoader(), itfs, handler);

    }


}
