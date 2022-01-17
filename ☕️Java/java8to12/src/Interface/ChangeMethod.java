package Interface;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChangeMethod {
    public static void main(String[] args) {
        List<String> name = new ArrayList<>();
        name.add("gngsn");
        name.add("whiteship");
        name.add("toby");
        name.add("foo");

//        name.forEach(s -> System.out.println(s));
//        name.forEach(System.out::println);

//        Spliterator<String> spliterator = name.spliterator();
//        Spliterator<String> spliterator1 = spliterator.trySplit();
//        while(spliterator.tryAdvance(System.out::println));
//        System.out.printf("==================");
//        while(spliterator1.tryAdvance(System.out::println));

//        Stream stream = name.stream().map(String::toUpperCase)
//                .filter(s -> s.startsWith("G"));
//
//        stream.forEach(System.out::println);
//        System.out.println(stream.toString());

//        name.removeIf(s -> s.startsWith("g"));
//        System.out.printf(name.toString());

        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        name.sort(compareToIgnoreCase.reversed());
    }
}
