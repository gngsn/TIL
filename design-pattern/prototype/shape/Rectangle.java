package prototype.shape;

public class Rectangle extends Shape {
    public int width, height;

    public Rectangle() {}

    public Rectangle(Rectangle target) {
        this.width = target.width;
        this.height = target.height;
    }

    @Override
    public String getShape() {
        return "width: " + width + ", height: " + height;
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Rectangle) || !super.equals(object2)) return false;
        Rectangle shape2 = (Rectangle) object2;
        return shape2.width == width && shape2.height == height;
    }
}
