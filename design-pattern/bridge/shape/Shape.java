package bridge.shape;

abstract class Shape {
    String color;

    public Shape(String color) {
        this.color = color;
    }

    public String fill() {
        return "Color is " + color;
    }

    public abstract void draw();
}
