package abstractFactory.concreteFactory;

import abstractFactory.Factory;
import abstractFactory.abstractProduct.TireProduct;
import abstractFactory.abstractProduct.DoorProduct;
import abstractFactory.concreteProduct.KoreaDoorProduct;
import abstractFactory.concreteProduct.KoreaTireProduct;

public class KoreaFactory extends Factory {
    public KoreaFactory() {
        System.out.println("KoreaFactory 객체를 생성합니다.");
    }

    public TireProduct createTire() {
        return new KoreaTireProduct();
    }

    public DoorProduct createDoor() {
        return new KoreaDoorProduct();
    }
}
