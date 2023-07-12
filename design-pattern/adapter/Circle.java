package adapter;

public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Drawing jdk17seal.Circle");
    }

    @Override
    public void resize() {
        System.out.println("Resizing jdk17seal.Circle");
    }

    @Override
    public String description() {
        return "jdk17seal.Circle object";
    }
}
