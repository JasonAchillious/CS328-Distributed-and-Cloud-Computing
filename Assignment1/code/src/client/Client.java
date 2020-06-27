package client;

import server.IFileServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client implements IClient{
    private IFileServer stub;

    public Client(String host, String binding) throws RemoteException, NotBoundException {

        Registry reg = LocateRegistry.getRegistry(host);
        stub = (IFileServer) reg.lookup(binding);

    }

    @Override
    public void read(String fileName) throws RemoteException {
        System.out.println("File Content:\n");
        String data = stub.read(fileName);
        System.out.println(data);
    }

    @Override
    public void create(String fileName) throws RemoteException {
        System.out.printf("Creating File %s.....\n", fileName);
        boolean flag = stub.create(fileName);
        if (flag){
            System.out.println("Successfully create the file!");
        }else{
            System.out.println("Fail to create the file.");
        }
    }

    @Override
    public void edit(String fileName, String newContent) throws RemoteException {
        System.out.printf("Editing new content to the file %s\n", fileName);
        boolean flag = stub.edit(fileName, newContent);
        if (flag){
            System.out.println("Successfully edit!");
        }else{
            System.out.println("Fail to Edit the file.");
        }
    }

    @Override
    public void delete(String fileName) throws RemoteException {
        System.out.println("Deleting the file...\n");
        boolean flag = stub.delete(fileName);
        if (flag){
            System.out.println("Successfully delete!");
        }else{
            System.out.println("Fail to delete the file.");
        }
    }

    @Override
    public void copy(String sourceFileName, String destinationFileName) throws RemoteException {
        System.out.printf("Copying the file to %s...\n", destinationFileName);
        boolean flag = stub.copy(sourceFileName, destinationFileName);
        if (flag){
            System.out.println("Successfully copy!");
        }else {
            System.out.println("Fail to copy.");
        }
    }

    @Override
    public void move(String sourceFileName, String destinationFileName) throws RemoteException {
        System.out.printf("Moving the file to %s\n", destinationFileName);
        boolean flag = stub.move(sourceFileName, destinationFileName);
        if (flag){
            System.out.println("Successfully move!");
        }else {
            System.out.println("Fail to move.");
        }
    }

    @Override
    public void printFileInfo(String fileName) throws RemoteException {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.printf("\nThe following are the information of the file <%s>:\n", fileName);
        System.out.printf("size: %d\n", this.stub.size(fileName));
        System.out.printf("last modified time: %s\n", sdf.format(new Date(this.stub.lastModified(fileName))));
        System.out.printf("last accessed time: %s\n", sdf.format(new Date(this.stub.lastAccessed(fileName))));
    }
}
