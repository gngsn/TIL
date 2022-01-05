package abstractFactory.concreteProduct;

import abstractFactory.abstractProduct.DoorProduct;

public class KoreaDoorProduct extends DoorProduct {
    public KoreaDoorProduct() {
        System.out.println("KoreaTireProduct 객체를 생성합니다.");
    }

    public void makeAssemble() {
        System.out.println("한국 도어 장착");
    }
}
