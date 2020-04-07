import myrmi.exception.NotBoundException;
import myrmi.exception.RemoteException;
import myrmi.registry.LocateRegistry;
import myrmi.registry.Registry;
import myrmi.server.UnicastRemoteObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class OverallTest {
    static TestInterface stub;

    @BeforeAll
    static void setup() throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.createRegistry();
        TestInterface impl = new TestInterfaceImpl();
        TestInterface stubServerSide = (TestInterface) UnicastRemoteObject.exportObject(impl, 0);
        registry.rebind("Test", stubServerSide);
        stub = (TestInterface) LocateRegistry.getRegistry().lookup("Test");
        System.out.println("Lookup");
    }


    @Test
    void testVoidMethod() {
        stub.testVoidMethod();
    }

    @Test
    void testCustomObjectRet() {
        CustomObject obj = stub.testCustomObjectRet(123, "123");
        Assertions.assertEquals(new CustomObject(123, "123"), obj);
    }

    @Test
    void testCustomObjectArg() {
        String ret = stub.testCustomObjectArg(new CustomObject(123, "12345"));
        Assertions.assertEquals("12345", ret);
    }

    @Test
    void testCustomException() {
        Assertions.assertThrows(CustomException.class, () -> {
            stub.testCustomException();
        });
    }


}
