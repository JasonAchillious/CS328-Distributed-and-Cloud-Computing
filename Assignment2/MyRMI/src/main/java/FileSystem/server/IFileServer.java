package FileSystem.server;
import myrmi.Remote;
import myrmi.exception.RemoteException;

public interface IFileServer extends Remote{

    public String read(String fileName) throws RemoteException;

    public boolean create(String fileName) throws RemoteException;

    public boolean edit(String fileName, String newContent) throws RemoteException;

    public boolean delete(String fileName) throws RemoteException;

    public boolean copy(String sourceFileName, String destinationFileName) throws RemoteException;

    public boolean move(String sourceFileName, String destinationFileName) throws RemoteException;

    public long size(String fileName) throws RemoteException;

    public long lastModified(String fileName) throws RemoteException;

    public long lastAccessed(String fileName) throws RemoteException;

}
