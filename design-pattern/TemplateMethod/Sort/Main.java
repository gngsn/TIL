package TemplateMethod.Sort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Duck[] ducks = {
                new Duck("Daffy", 8),
                new Duck("Dewey", 2),
                new Duck("Howard", 7),
                new Duck("Louie", 2),
                new Duck("Donald", 10),
                new Duck("Huey", 2),
        };

        System.out.println("-- Before sorting --");
        display(ducks);

        // sort - Template Method
        Arrays.sort(ducks);

        System.out.println("\n-- After sorting --");
        display(ducks);
    }

    public static void display(Duck[] ducks) {
        for (Duck d : ducks) {
            System.out.println(d);
        }
    }
}
