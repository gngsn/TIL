package abstractFactory;

import abstractFactory.abstractProduct.DoorProduct;
import abstractFactory.abstractProduct.TireProduct;
import abstractFactory.concreteFactory.KoreaFactory;
import abstractFactory.concreteFactory.StateFactory;

public class Main {
    public static void main(String[] args) {
        Factory korea = new KoreaFactory();

        TireProduct tire = korea.createTire();
        tire.makeAssemble();

        DoorProduct door = korea.createDoor();
        door.makeAssemble();


        Factory state = new StateFactory();

        tire = state.createTire();
        tire.makeAssemble();

        door = state.createDoor();
        door.makeAssemble();



    }
}
