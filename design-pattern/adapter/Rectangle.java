package adapter;

public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing jdk17seal.Rectangle");
    }

    @Override
    public void resize() {
        System.out.println("Resizing jdk17seal.Rectangle");
    }

    @Override
    public String description() {
        return "jdk17seal.Rectangle object";
    }
}