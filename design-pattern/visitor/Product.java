package visitor;

public class Product {
    protected String name;
    protected int price;
    protected int num;
    protected final int DEFAULT_TAX = 10;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }
}
