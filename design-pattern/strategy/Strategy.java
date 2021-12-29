package strategy;

public abstract class Strategy {
    protected Weapon delegate;

    public void setWeapon(Weapon weapon) {
        System.out.println("-- 무기 교환 --");
        this.delegate = weapon;
    }

    public abstract void attack();
}
