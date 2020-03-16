package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFileServer extends Remote{

    public String read(String fileName) throws RemoteException;

    public void create(String fileName) throws Exception;

    public void edit(String fileName, String newContent) throws RemoteException;

    public void delete(String fileName) throws RemoteException;

    public void copy(String sourceFileName, String destinationFileName) throws RemoteException;

    public void move(String sourceFileName, String destinationFileName) throws RemoteException;

    public long size(String fileName) throws RemoteException;

    public long lastModified(String fileName) throws RemoteException;

    public long lastAccessed(String fileName) throws RemoteException;

}
