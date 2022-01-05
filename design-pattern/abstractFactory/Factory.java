package abstractFactory;

import abstractFactory.abstractProduct.DoorProduct;
import abstractFactory.abstractProduct.TireProduct;

public abstract class Factory {
    public abstract TireProduct createTire();
    public abstract DoorProduct createDoor();
}