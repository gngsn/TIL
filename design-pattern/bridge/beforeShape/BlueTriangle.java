package bridge.beforeShape;

public class BlueTriangle extends Blue {
    @Override
    public void draw() {
        System.out.println("BlueTriangle drawn. " + fill());
    }
}
