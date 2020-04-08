package myrmi.server;

import myrmi.Remote;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class Util {

    public static Remote createStub(RemoteObjectRef ref) {
        //TODO: finish
        Class objClass;
        Class[] itfs;

        try {
            objClass = Class.forName(ref.getInterfaceName());
            itfs = new Class[]{objClass};
        }catch (ClassNotFoundException e){
            System.out.println("**************" + ref.getInterfaceName() + "**************");
            e.printStackTrace();
            objClass = Remote.class;
            itfs = new Class[]{objClass};
        }

        StubInvocationHandler handler = new StubInvocationHandler(ref);
        return (Remote) Proxy.newProxyInstance(objClass.getClassLoader(), itfs, handler);

    }


}
