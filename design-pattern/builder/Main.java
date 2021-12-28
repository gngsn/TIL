package builder;

public class Main {
    public static void main(String[] args) {
        Algorithm algorithm = new ProductModel();

        Factory factory = new Factory(algorithm);

//        Factory factory = new Factory();
//        factory.setAlgorithm(algorithm);

        Computer computer = factory.build().getInstance();
        System.out.println(computer.toString());
    }
}
