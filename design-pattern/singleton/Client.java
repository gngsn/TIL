package singleton;

public class Client {
    public static void main(String[] args) {
        Printer printer1 = Printer.getInstance();
        Printer printer2 = Printer.getInstance();
        System.out.println(printer1 == printer2);
    }
}
