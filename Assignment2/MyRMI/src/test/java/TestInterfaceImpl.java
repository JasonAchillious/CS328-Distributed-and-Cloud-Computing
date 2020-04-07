public class TestInterfaceImpl implements TestInterface {

    @Override
    public void testVoidMethod() {
        System.out.println("Void method executed");
    }

    @Override
    public CustomObject testCustomObjectRet(int intField, String stringField) {
        return new CustomObject(intField, stringField);
    }

    @Override
    public String testCustomObjectArg(CustomObject obj) {
        return obj.getStringField();
    }

    @Override
    public void testCustomException() throws CustomException {
        throw new CustomException();
    }
}
