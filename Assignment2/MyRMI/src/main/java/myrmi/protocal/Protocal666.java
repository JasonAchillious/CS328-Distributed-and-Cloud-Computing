package myrmi.protocal;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Protocal666 implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object[] args;
    private int objectKey;
    private Object result;
    private Method method;
    private int status;
    private Type returnType;
    private Exception exception=null;

    public Protocal666(int status){
        this.status = status;
    }

    public Protocal666(Method method, Object[] args, int objectKey){
        this(method, args);
        this.objectKey = objectKey;
    }

    public Protocal666(Method method, Object[] args ){
        this.method = method;
        this.args = args;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
