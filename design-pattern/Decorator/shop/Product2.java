package Decorator.shop;

public class Product2 implements Component {
    @Override
    public String product() {
        return "블라우스";
    }

    @Override
    public int price() {
        return 25000;
    }
}
