package factoryMethod;

class ProductFactory extends Factory {
    public ProductFactory() {
        System.out.println("ProductFactory 생성");
    }

    public Product createProduct(String model) {
        if (model == "LG") {
            return new LgProduct();
        } else {
            return new SamsungProduct();
        }
    }
}
