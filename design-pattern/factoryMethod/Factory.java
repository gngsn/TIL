package factoryMethod;

abstract class Factory {
    public Product create(String model) {
        return this.createProduct(model);
    }

    abstract public Product createProduct(String model);
}
