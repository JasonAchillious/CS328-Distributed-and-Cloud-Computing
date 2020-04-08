package myrmi.protocal;


import java.io.Serializable;

public class InfoFromStub implements Serializable {
    private static final long serialVersionUID = 1L;
    private String method;
    private Object[] args;
    private int objectKey;


    public InfoFromStub(String method, Object[] args, int objectKey){
        this(method, args);
        this.objectKey = objectKey;
    }

    public InfoFromStub(String method, Object[] args ){
        this.method = method;
        this.args = args;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public int getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(int objectKey) {
        this.objectKey = objectKey;
    }
}
