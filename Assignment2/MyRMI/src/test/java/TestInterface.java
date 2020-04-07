import myrmi.Remote;

public interface TestInterface extends Remote {
    public void testVoidMethod();

    public CustomObject testCustomObjectRet(int intField, String stringField);

    public String testCustomObjectArg(CustomObject obj);

    public void testCustomException() throws CustomException;
}
