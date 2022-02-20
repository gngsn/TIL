package bridge.beforeShape;

public class RedTriangle extends Red {
    @Override
    public void draw() {
        System.out.println("RedTriangle drawn. " + fill());
    }
}
