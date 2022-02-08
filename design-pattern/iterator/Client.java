package iterator;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        Fruit fruits[] = {
                new Fruit("Apple"),
                new Fruit("Banana"),
                new Fruit("Berry"),
                new Fruit("Grape"),
        };

        for (int i = 0; i < fruits.length; i++) {
            System.out.println(fruits[i].getName());
        }
//        List<Fruit> fruits = new ArrayList<>(
//                new Fruit("Apple"),
//        )
    }
}
