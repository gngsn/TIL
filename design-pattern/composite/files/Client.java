package composite.files;

public class Client {
    public static void main(String[] args) {
        Folder rootFolder = new Folder("School");

//        1 학년
        Folder folder1 = new Folder("freshman");
        rootFolder.add(folder1);

        File assign1 = new File("picture", 256);
        folder1.add(assign1);


//        2 학년
        Folder folder2 = new Folder("sophomore");
        rootFolder.add(folder2);

//      2 학년 1 학기
        Folder sem1Folder = new Folder("1semester");
        Folder lecture1 = new Folder("C programming");
        File lecture2 = new File("Python", 520);

        File assign3 = new File("array", 266);
        File assign4 = new File("pointer", 302);

        sem1Folder.add(lecture1);
        sem1Folder.add(lecture2);
        lecture1.add(assign3);
        lecture1.add(assign4);

//        2 학년 2 학기
        Folder sem2Folder = new Folder("2semester");

        folder2.add(sem1Folder);
        folder2.add(sem2Folder);

        rootFolder.getSize();
    }
}
