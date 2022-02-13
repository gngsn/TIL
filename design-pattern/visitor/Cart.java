package visitor;

public class Cart extends Product implements Visitable {
    public Cart(String name, int price) {
        this(name, price, 10);
    }

    public Cart(String name, int price, int num) {
        this.name = name;
        this.price = price;
        this.num = num;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.order(this);
    }

    public String list() {
        String order = this.name;
        order += ", 수량: " + this.num;
        order += ", 가격: " + this.price + " 입니다.";
        return order;
    }

    public int getTax() {
        return getTax(DEFAULT_TAX);
    }

    public int getTax(int tax) {
        return (this.price * this.num) * tax/100;
    }
}
