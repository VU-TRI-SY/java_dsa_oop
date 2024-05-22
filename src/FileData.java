public class FileData {

    public String name;
    public String dir;
    public String lastModifiedDate;

    // TODO
    public FileData(String name, String directory, String modifiedDate) {
        this.name = name;
        this.dir = directory; 
        this.lastModifiedDate = modifiedDate;
    }

    // TODO
    public String toString() {
        String ans = String.format("{Name: %s, Directory: %s, Modified Date: %s}.", this.name, this.dir, this.lastModifiedDate);
        return ans;
    }
}