package bridge.shape;

public class Square extends Shape {
    public Square(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.println("Square drawn. " + color.fill());
    }
}
