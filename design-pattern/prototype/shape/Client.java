package prototype.shape;


public class Client {
    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.x = 10;
        circle.y = 20;
        circle.radius = 15;
        circle.color = "red";
        Circle clonedCircle = (Circle) circle.clone();

        System.out.println("[circle] " + circle.getShape());
        System.out.println("[clonedCircle] " + clonedCircle .getShape());
        System.out.println("circle equals anotherCircle? " + circle.equals(clonedCircle));

        Rectangle rect = new Rectangle();
        rect.width = 10;
        rect.height = 20;
        rect.color = "blue";
        System.out.println("\n=== Clone Multiple Object ===");
        for (int i = 0; i < 3; i++) {
            Rectangle clonedRect = (Rectangle) rect.clone();
            System.out.println("[clonedRect " + i + "] "+ clonedRect.getShape());
        }
    }
}
