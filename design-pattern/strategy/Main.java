package strategy;

public class Main {
    public static void main(String[] args) {
        Charactor obj = new Charactor();
        obj.attack();

        obj.setWeapon(new Knife());
        obj.attack();

        obj.setWeapon(new Gun());
        obj.attack();
    }
}
