package composite;

public class Leaf extends Component {
    private int price;

    public Leaf(String name) {
        this.setName(name);
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
