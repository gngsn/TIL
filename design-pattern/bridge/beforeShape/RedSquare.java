package bridge.beforeShape;

public class RedSquare extends Red {
    @Override
    public void draw() {
        System.out.println("RedSquare drawn. " + this.fill());
    }
}
