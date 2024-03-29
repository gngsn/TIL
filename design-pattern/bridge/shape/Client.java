package bridge.shape;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();

        shapes.add(new Square(new Red()));
        shapes.add(new Square(new Blue()));
        shapes.add(new Triangle(new Red()));
        shapes.add(new Triangle(new Blue()));

        for (Shape shape: shapes) {
            shape.draw();
        }
    }
}

/*  OUTPUT :: after applying the bridge pattern

RedSquare drawn. Color is red
RedSquare drawn. Color is blue
RedTriangle drawn. Color is red
RedTriangle drawn. Color is blue

* */
