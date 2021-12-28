package builder;

import java.util.ArrayList;

public class ProductModel extends Algorithm {

    public ProductModel() {
        System.out.println("ProductModel 객체 생성");
        this.composite = new Computer();
    }

    public ProductModel setCpu(String cpu) {
        System.out.println("CPU 설정");
        this.composite.cpu = cpu;

        return this;
    }

    public ProductModel setRam(ArrayList<Integer> ram) {
        System.out.println("RAM 설정");
        this.composite.ram = ram;

        return this;
    }

    public ProductModel setStorage(ArrayList<Integer> storage) {
        System.out.println("STORAGE 설정");
        this.composite.storage = storage;

        return this;
    }
}
