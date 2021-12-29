package strategy;

import strategy.ConcreteStrategy.Gun;
import strategy.ConcreteStrategy.Knife;

public class Main {
    public static void main(String[] args) {
        Fighter obj = new Fighter();
        obj.attack();

        obj.setWeapon(new Knife());
        obj.attack();

        obj.setWeapon(new Gun());
        obj.attack();
    }
}
