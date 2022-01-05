package abstractFactory.concreteProduct;

import abstractFactory.abstractProduct.TireProduct;

public class StateTireProduct extends TireProduct {
    public StateTireProduct() {
        System.out.println("StateTireProduct 객체를 생성합니다.");
    }

    public void makeAssemble() {
        System.out.println("미국 타이어 장착");
    }

}
