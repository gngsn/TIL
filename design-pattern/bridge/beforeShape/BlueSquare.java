package bridge.beforeShape;

public class BlueSquare extends Blue {
    @Override
    public void draw() {
        System.out.println("BlueSquare drawn. " + this.fill());
    }
}
