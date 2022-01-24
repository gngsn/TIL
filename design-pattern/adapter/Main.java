package adapter;

import adapter.extra.Triangle;

public class Main {
    public static void main(String[] args) {
        System.out.println("Creating drawing of shapes...");
        Drawing drawing = new Drawing();
        drawing.addShape(new Rectangle());
        drawing.addShape(new Circle());

        // Object Adapter
        drawing.addShape(new ObjectAdapter(new Triangle()));

        // EnumIterClass Adapter
        drawing.addShape(new ClassAdapter());

        System.out.println("Drawing...");
        drawing.draw();
        System.out.println("Resizing...");
        drawing.resize();
    }
}