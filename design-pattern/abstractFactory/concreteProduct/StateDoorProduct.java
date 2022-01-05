package abstractFactory.concreteProduct;

import abstractFactory.abstractProduct.DoorProduct;

public class StateDoorProduct extends DoorProduct {
    public StateDoorProduct() {
        System.out.println("StateDoorProduct 객체를 생성합니다.");
    }

    public void makeAssemble() {
        System.out.println("미국 도어 장착");
    }
}
