package decorator.shop;

public class i7 extends Decorate {
    public Component base;

    public i7(Component concrete) {
        System.out.println("i7 생성되었습니다.");
        this.base = concrete;
    }

    @Override
    public String product() {
        return this.base.product() + ", i7";
    }

    @Override
    public int price() {
        return this.base.price() + 475000;
    }
}
