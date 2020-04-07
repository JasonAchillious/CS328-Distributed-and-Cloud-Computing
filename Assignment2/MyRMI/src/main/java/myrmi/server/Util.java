package myrmi.server;

import myrmi.Remote;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Util {

    public static Remote createStub(RemoteObjectRef ref) {
        //TODO: finish
        Class itfClass;
        try {
            String interfaceName = ref.getInterfaceName();
            itfClass = Class.forName(interfaceName);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            itfClass = Remote.class;
        }

        StubInvocationHandler handler = new StubInvocationHandler(ref);
        Class<?>[] interfaces = new Class[]{itfClass};
        return (Remote) Proxy.newProxyInstance(itfClass.getClassLoader(), interfaces, handler);

    }


}
