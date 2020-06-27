package server;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.rmi.RemoteException;
import java.io.File;



public class FileServerImpl implements IFileServer{

    @Override
    public String read(String fileName) throws RemoteException{
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
        if (!f.exists()){
            data = "No such file.";
        }else if (!f.isFile()){
            data = "It is not a file.";
        }else if (!f.canRead()){
            data = "The file cannot be read";
        }
        return data;
    }

    @Override
    public boolean create(String fileName) throws RemoteException{
        File f = new File(fileName);
        boolean bool = false;
        try{
            bool = f.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (bool){
            System.out.println("Create the file successfully.");
        }else {
            System.out.println("Fail to create the file.");
        }
        return bool;
    }

    @Override
    public boolean edit(String fileName, String newContent) throws RemoteException{
        File file = new File(fileName);
        boolean hasEdited = false;
        if (file.canWrite()){
            try {
                Files.write(file.toPath(), newContent.getBytes());
                hasEdited = true;
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Cannot write the file!");
        }
        return hasEdited;
    }

    @Override
    public boolean delete(String fileName) throws RemoteException{
        boolean bool = false;
        File file = new File(fileName);
        bool = file.delete();
        if (bool){
            System.out.println("Delete the file successfully.");
        }
        return bool;
    }

    @Override
    public boolean copy(String sourceFileName, String destinationFileName) throws RemoteException{
        boolean hasCopy = false;
        try {
            File srcFile = new File(sourceFileName);
            File dstFile = new File(destinationFileName);
            Files.copy(srcFile.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            hasCopy = true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return hasCopy;
    }

    @Override
    public boolean move(String sourceFileName, String destinationFileName) throws RemoteException{
        Boolean hasMove = false;
        File srcFile = new File(sourceFileName);
        File dstFile = new File(destinationFileName);
        if (srcFile.renameTo(dstFile)) {
            hasMove = true;
            System.out.println("File is moved successful!");
        }else {
            System.out.println("Fail to move the file!");
        }
        return hasMove;
    }

    @Override
    public long size(String fileName) throws RemoteException{
        File file = new File(fileName);
        long size = 0;
        if (file.isFile()){
            size = file.length();
        }
        return size;
    }

    @Override
    public long lastModified(String fileName) throws RemoteException{
        File file = new File(fileName);
        return file.lastModified();
    }

    @Override
    public long lastAccessed(String fileName) throws RemoteException{
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
