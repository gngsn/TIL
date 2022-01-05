package abstractFactory.concreteFactory;

import abstractFactory.Factory;
import abstractFactory.concreteProduct.StateTireProduct;
import abstractFactory.abstractProduct.TireProduct;
import abstractFactory.abstractProduct.DoorProduct;
import abstractFactory.concreteProduct.StateDoorProduct;

public class StateFactory extends Factory {
    public StateFactory() {
        System.out.println("StateFactory 객체를 생성합니다.");
    }

    public TireProduct createTire() {
        return new StateTireProduct();
    }

    public DoorProduct createDoor() {
        return new StateDoorProduct();
    }
}
