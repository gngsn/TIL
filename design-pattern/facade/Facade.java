package facade;

public class Facade {
    private Package1 package1;
    private Package2 package2;
    private Package3 package3;

    public Facade() {
        this.package1 = new Package1();
        this.package2 = new Package2();
        this.package3 = new Package3();
    }

    public void processAll() {
        this.package1.process();
        this.package2.process();
        this.package3.process();
    }
}
