package factoryMethod;

public class Main {
    public static void main(String[] args) {
        Factory fac = new ProductFactory();
        Product pro = fac.create("LG");
        pro.name();

        pro = fac.create("SAMSUNG");
        pro.name();
    }
}
