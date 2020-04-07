import myrmi.exception.AlreadyBoundException;
import myrmi.exception.NotBoundException;
import myrmi.exception.RemoteException;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;
import myrmi.server.UnicastRemoteObject;
import org.junit.jupiter.api.*;

public class RegistryTest {
    static Registry registry;

    @BeforeAll
    static void setup() throws RemoteException {
        LocateRegistry.createRegistry(21099);
        registry = LocateRegistry.getRegistry("127.0.0.1", 21099);
    }


    @Test
    void testBindException() throws RemoteException, AlreadyBoundException, NotBoundException {
        TestInterface t = new TestInterfaceImpl();
        TestInterface s = (TestInterface) UnicastRemoteObject.exportObject(t);

        registry.bind("testBindException", s);
        // should not throw exception here
        registry.rebind("testBindException", s);
        Assertions.assertThrows(AlreadyBoundException.class, () -> {
            registry.bind("testBindException", s);
        });
        // should not throw exception here
        registry.unbind("testBindException");
    }

    @Test
    void testNotBound() {
        Assertions.assertThrows(NotBoundException.class, () -> {
            registry.unbind("testNotBound");
        });
    }

    @Test
    void testLookup() throws RemoteException, NotBoundException {
        TestInterface t = new TestInterfaceImpl();
        TestInterface s = (TestInterface) UnicastRemoteObject.exportObject(t);

        registry.rebind("testLookup", s);
        TestInterface stub = (TestInterface) registry.lookup("testLookup");
        Assertions.assertTrue(stub.getClass().getName().contains("com.sun.proxy.$Proxy"));
    }

    @Test
    void testList() throws RemoteException {
        TestInterface t = new TestInterfaceImpl();
        TestInterface s = (TestInterface) UnicastRemoteObject.exportObject(t);
        registry.rebind("testList", s);
        registry.rebind("testList2", s);
        registry.rebind("testList3", s);

        Assertions.assertArrayEquals(new String[]{"testList", "testList2", "testList3"}, registry.list());
    }

}
