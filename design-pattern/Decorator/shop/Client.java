package Decorator.shop;

public class Client {
    public static void main(String[] args) {
        Component p = new Product1();

        Decorate spec = new i7(p);
        spec = new ssd256(spec);

        System.out.println("spec = " + spec.product());
        System.out.println("price = " + spec.price());
    }
}
