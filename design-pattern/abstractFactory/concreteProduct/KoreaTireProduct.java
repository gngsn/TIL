package abstractFactory.concreteProduct;

import abstractFactory.abstractProduct.TireProduct;

public class KoreaTireProduct extends TireProduct {
    public KoreaTireProduct() {
        System.out.println("KoreaTireProduct 객체를 생성합니다.");
    }

    public void makeAssemble() {
        System.out.println("한국 타이어 장착");
    }
}
