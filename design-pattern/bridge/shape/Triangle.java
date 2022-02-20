package bridge.shape;

public class Triangle extends Shape {
    public Triangle(String color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.println("Triangle drawn. " + this.fill());
    }
}
