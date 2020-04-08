package myrmi.protocal;

import java.io.Serializable;

public class InfoFromSkeleton implements Serializable{
    private static final long serialVersionUID = 2L;

    private Object result;
    private int status;
    private String exception=null;
    private int objectKey;

    public InfoFromSkeleton(int objectKey){
        this.objectKey = objectKey;
    }

    public InfoFromSkeleton(Object result, int objectKey, String exception, int status){
        this.result = result;
        this.objectKey = objectKey;
        this.exception = exception;
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(int objectKey) {
        this.objectKey = objectKey;
    }
}
