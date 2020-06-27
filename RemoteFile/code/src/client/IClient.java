package client;

import java.rmi.RemoteException;

public interface IClient {
    public void read(String fileName) throws RemoteException;

    public void create(String fileName) throws RemoteException;

    public void edit(String fileName, String newContent) throws RemoteException;

    public void delete(String fileName) throws RemoteException;

    public void copy(String sourceFileName, String destinationFileName) throws RemoteException;

    public void move(String sourceFileName, String destinationFileName) throws RemoteException;

    public void printFileInfo(String fileName) throws RemoteException;
}
