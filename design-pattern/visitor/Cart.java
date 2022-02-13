package visitor;

public class Cart extends Product implements Visitable {
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
        order += " - " + this.num + "개: " + this.price + "₩";
        return order;
    }

    public int getTax(int tax) {
        return (this.price * this.num) * tax/100;
    }
}
