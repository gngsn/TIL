package strategy.ConcreteStrategy;

import strategy.Weapon;

public class Gun implements Weapon {
    @Override
    public void attack() {
        System.out.println("총 발포");
    }
}