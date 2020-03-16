package server;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.rmi.RemoteException;
import java.io.File;


public class FileServerImpl implements IFileServer{

    @Override
    public String read(String fileName) throws RemoteException {
        File f = new File(fileName);
        String data = null;
        if (f.exists() && f.isFile() && f.canRead()){
            try {
                data = new String(Files.readAllBytes(Paths.get(fileName)));
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("Cannot read the file!");
        }
        return data;
    }

    @Override
    public void create(String fileName) throws RemoteException {
        File f = new File(fileName);
        boolean bool = false;
        try{
            bool = f.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (bool){
            System.out.println("Create the file successfully.");
        }
    }

    @Override
    public void edit(String fileName, String newContent) throws RemoteException {
        File file = new File(fileName);
        if (file.canWrite()){
            try {
                Files.write(file.toPath(), newContent.getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Cannot write the file!");
        }
    }

    @Override
    public void delete(String fileName) throws RemoteException {
        boolean bool;
        File file = new File(fileName);
        bool = file.delete();
        if (bool){
            System.out.println("Delete the file successfully.");
        }
    }

    @Override
    public void copy(String sourceFileName, String destinationFileName) throws RemoteException {
        try {
            File srcFile = new File(sourceFileName);
            File dstFile = new File(destinationFileName);
            Files.copy(srcFile.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void move(String sourceFileName, String destinationFileName) throws RemoteException {
        File srcFile = new File(sourceFileName);
        File dstFile = new File(destinationFileName);
        if (srcFile.renameTo(dstFile)) {
            System.out.println("File is moved successful!");
        }else {
            System.out.println("Fail to move the file!");
        }
    }

    @Override
    public long size(String fileName) throws RemoteException {
        File file = new File(fileName);
        long size = 0;
        if (file.isFile()){
            size = file.length();
        }
        return size;
    }

    @Override
    public long lastModified(String fileName) throws RemoteException {
        File file = new File(fileName);
        return file.lastModified();
    }

    @Override
    public long lastAccessed(String fileName) throws RemoteException {
        try {
            Path filePath = Paths.get(fileName);
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            FileTime time = attrs.lastAccessTime();
            return time.toMillis();
        }catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }
}
