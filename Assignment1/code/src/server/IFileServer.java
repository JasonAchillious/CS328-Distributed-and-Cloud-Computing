package server;

public interface IFileServer {
    public String read(String fileName);

    public void create(String fileName);

    public void edit(String fileName, String newContent);

    public void delete(String fileName);

    public void copy(String sourceFileName, String destinationFileName);

    public void move(String sourceFileName, String destinationFileName);

    public int size(String fileName);

    public long lastModified(String fileName);

    public long lastAccessed(String fileName);
}
