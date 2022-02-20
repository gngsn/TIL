package bridge.shape;

public class Square extends Shape {
    public Square(String color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.println("Square drawn. " + this.fill());
    }
}
