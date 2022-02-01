package composite.files;

public class File implements FileSystem {
    private String name;
    private int size;

    public File(String _name, int _size) {
        this.name = _name;
        this.size = _size;
    }

    @Override
    public int getSize() {
        System.out.println(name + " 파일 크기 " + size);
        return size;
    }

    @Override
    public void remove() {
        System.out.println(name + " 파일 삭제");
    }
}
