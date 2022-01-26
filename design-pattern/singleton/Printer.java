package singleton;

public class Printer {
    private static Printer printer = new Printer();

    private Printer() { }

    public synchronized static Printer getInstance(){
        if (printer == null) {
            printer = new Printer();
        }
        return printer;
    }

}