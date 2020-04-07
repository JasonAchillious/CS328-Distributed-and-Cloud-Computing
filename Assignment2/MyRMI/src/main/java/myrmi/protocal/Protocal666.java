package myrmi.protocal;


import java.lang.reflect.Method;

public class Protocal666 {
    private String className;
    private String methodName;
    private Method[] methods;
    private Object[] args;
    private int objectKey;
    private Object result;
    private Method method;

    public Protocal666(Object object, String methodName){
        this.className = object.getClass().getName();
        this.objectKey = object.hashCode();
        this.methods = object.getClass().getDeclaredMethods();
        this.methodName = methodName;
    }

    public Protocal666(Method method, Object[] args){
        this.method = method;
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public int getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(int objectKey) {
        this.objectKey = objectKey;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
