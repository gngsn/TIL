package composite;

public class Client {
    public static void main(String[] args) {
        Computer obj = new Computer();
        obj.setMonitor(new Monitor());

        System.out.println(obj.getName());
        System.out.println(obj.getMonitor().name);
    }
}
