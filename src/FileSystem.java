import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    // TODO
    public FileSystem() {
        this.nameTree = new BST<>();
        this.dateTree = new BST<>();
    }


    // TODO
    public FileSystem(String inputFile) {
    	// Add your code here
        this.nameTree = new BST<>();
        this.dateTree = new BST<>();

        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Add your code here
                String name = data[0];
                String dir = data[1];
                String lastModifiedDate = data[2];

                add(name, dir, lastModifiedDate);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // TODO
    public void add(String name, String dir, String date) {
        if(name == null || dir == null || date == null) return;
    	// FileData fd = new FileData(name, dir, date);
        if(nameTree.containsKey(name) == true){
            FileData fd = nameTree.get(name);
            if(fd.lastModifiedDate.compareTo(date) < 0){ ///fd has new date --> remove the old from dateTree and add new one to dateTree
                ArrayList<FileData> arr = dateTree.get(fd.lastModifiedDate);
                for(int i = 0; i < arr.size(); i++){
                    FileData f = arr.get(i);
                    if(f.name.equals(name)){
                        arr.remove(i);
                        break;
                    }
                }

                fd.lastModifiedDate = date;
                
                
                if(dateTree.containsKey(date) == true){
                    arr = dateTree.get(date);
                    arr.add(fd);
                }else{
                    arr = new ArrayList<>();
                    arr.add(fd);
                    dateTree.put(date, arr);
                }
            }
        } else{
            FileData fd = new FileData(name, dir, date);
            nameTree.put(name, fd);

            if(dateTree.containsKey(date) == true){
                ArrayList<FileData> arr = dateTree.get(date);
                arr.add(fd);
            }else{
                ArrayList<FileData> arr = new ArrayList<>();
                arr.add(fd);
                dateTree.put(date, arr);
            }
        }
    }


    // TODO
    public ArrayList<String> findFileNamesByDate(String date) {
        if(date == null) return null;

        if(dateTree.containsKey(date) == false) return null;

        ArrayList<String> arr = new ArrayList<>();
        ArrayList<FileData> fds =  dateTree.get(date);;
        for(FileData fd : fds){
            arr.add(fd.name);
        }

        return arr;
    }


    // TODO
    public FileSystem filter(String startDate, String endDate) {
        FileSystem fs = new FileSystem();

        ArrayList<FileData> fds = nameTree.getValues(); //get all filedatas from nameTree

        for(FileData fd : fds){
            String name = fd.name;
            String dir = fd.dir;
            String date = fd.lastModifiedDate;

            if(date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0){
                fs.add(name, dir, date);
            }
        }

        return fs;
    }

    
    
    // TODO
    public FileSystem filter(String wildCard) {
        FileSystem fs = new FileSystem();

        ArrayList<FileData> fds = nameTree.getValues(); //get all filedatas from nameTree

        for(FileData fd : fds){
            String name = fd.name;
            if(name.contains(wildCard)){
                String dir = fd.dir;
                String date = fd.lastModifiedDate;
    
                fs.add(name, dir, date);
            }
        }

        return fs;
    }
    
    
    // TODO
    public List<String> outputNameTree(){
        List<String> ans = new ArrayList<>();
        ArrayList<FileData> fds = nameTree.getValues();
        for(FileData fd : fds){
            ans.add(String.format("%s: %s", fd.name, fd.toString()));
        }

        return ans;
    }
    
    
    // TODO
    public List<String> outputDateTree(){
        List<String> ans = new ArrayList<>();
        ArrayList< ArrayList<FileData> > arr = new ArrayList<>();
        //arr is a list of items, each items is a list of FileData objects ~ 2D array

        for(int i = arr.size()-1; i >= 0; i--){
            ArrayList<FileData> fds = arr.get(i);
            for(int index = fds.size()-1; i >= 0; i--){
                FileData fd = fds.get(index);
                ans.add(String.format("%s: %s", fd.name, fd.toString()));
            }
        }

        return ans;
    }
}

