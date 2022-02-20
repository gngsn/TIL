package bridge.beforeShape;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();

        shapes.add(new RedSquare());
        shapes.add(new RedTriangle());
        shapes.add(new RedSquare());
        shapes.add(new RedSquare());

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
