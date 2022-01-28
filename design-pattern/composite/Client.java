package composite;

public class Client {
    public static void main(String[] args) {
        Component root = new Composite("root");
        Component home = new Composite("home");
        Component hojin = new Composite("hojin");
        Component jiny = new Composite("jiny");
        Component user = new Composite("user");
        Component temp = new Composite("temp");

        Leaf img1 = new Leaf("img1");
        Leaf img2 = new Leaf("img2");
        Leaf img3 = new Leaf("img3");
        Leaf img4 = new Leaf("img4");

        Computer obj = new Computer();
        obj.setMonitor(new Monitor());

        System.out.println(obj.getName());
        System.out.println(obj.getMonitor().name);
    }
}
