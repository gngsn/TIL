package composite.files;

import java.util.ArrayList;

public class Folder implements FileSystem {
    private String name;
    private ArrayList<FileSystem> includes = new ArrayList<>();

    public Folder(String _name) {
        this.name = _name;
    }

    public void add(FileSystem fileSystem) {
        includes.add(fileSystem);
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystem include: includes) {
            total += include.getSize();
        }
        System.out.println(name + " 폴더 크기 : "+ total);
        System.out.println("- - - - -");
        return total;
    }

    @Override
    public void remove() {
        for (FileSystem include: includes) {
            include.remove();
        }
        System.out.println(name + " 폴더 삭제");
        System.out.println("- - - - -");
    }
}
