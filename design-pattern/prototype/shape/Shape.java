package prototype.shape;

import java.util.Objects;

public abstract class Shape {
    public int x;
    public int y;
    public String color;

//    public jdk17seal.Shape() {}
//
//    public jdk17seal.Shape(jdk17seal.Shape target) {
//        if (target != null) {
//            this.x = target.x;
//            this.y = target.y;
//            this.color = target.color;
//        }
//    }

    public String getShape() {
        return "position: (" + x + ", " + y + "), color: " + color;
    }

    public abstract Shape clone();

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Shape)) return false;
        Shape shape2 = (Shape) object2;
        return shape2.x == x && shape2.y == y && Objects.equals(shape2.color, color);
    }
}