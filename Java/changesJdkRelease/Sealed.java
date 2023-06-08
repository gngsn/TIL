public class Sealed {
    public static void main(String[] args) {
        Shape circle = new Circle();
    }
}

abstract sealed class Shape permits Circle, Rectangle, Square, WeirdShape { /*...*/ }

final class Circle extends Shape { /*...*/ }
sealed class Rectangle extends Shape permits TransparentRectangle, FilledRectangle { /*...*/ }
final class Square extends Shape { /*...*/ }
non-sealed class WeirdShape extends Shape { /*...*/ }

class Something extends WeirdShape { /*...*/ }
class Else extends WeirdShape { /*...*/ }

final class TransparentRectangle extends Rectangle { /*...*/ }
final class FilledRectangle extends Rectangle { /*...*/ }

