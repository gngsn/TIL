package Decorator.shop;

public class ssd256 extends Decorate {
    public Component base;

    public ssd256(Component concrete) {
        System.out.println("ssd256 생성되었습니다.");
        this.base = concrete;
    }

    @Override
    public String product() {
        return this.base.product() + ", ssd256";
    }

    @Override
    public int price() {
        return this.base.price() + 110000;
    }
}
