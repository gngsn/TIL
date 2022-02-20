package bridge.shape;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();

        shapes.add(new Square("red"));
        shapes.add(new Square("blue"));
        shapes.add(new Triangle("red"));
        shapes.add(new Triangle("blue"));

        for (Shape shape: shapes) {
            shape.draw();
        }
    }
}

/*  OUTPUT :: before applying the bridge pattern

Square drawn. Color is red
Square drawn. Color is blue
Triangle drawn. Color is red
Triangle drawn. Color is blue

* */
