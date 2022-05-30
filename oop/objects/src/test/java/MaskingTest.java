import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingTest {
    List<String> testSet = List.of("홍길동", "서 도윤", "동욱", "현", "제갈지유", "Hugh Grant", "Julia Fiona Roberts", "Robert");

    String NAME_MASKING_KO = "(?<=.{1})(?<masking>.*)(?=.$)";
    //	String NAME_MASKING_KO = "[a-zA-Z]";

    String NAME_MASKING_EN = "(?<=.{1})(?<masking>\\w*)(?=\\s)";

    String NAME_MASKING_EN_FULL = "(?<=.{1})(?<masking>.*)(?=\\s)";

    String MASKING_STAR = "*";


    @Test
    void makingTest() {
        testSet.stream().map(name -> {
            System.out.printf("%s → ", name);
            if (name.length() < 3) {
                return name.substring(0, name.length() - 1) + MASKING_STAR;
            }

            if (name.charAt(0) >= 65 && name.charAt(0) <= 122) {
                Pattern pattern = Pattern.compile(NAME_MASKING_EN);
                Matcher m = pattern.matcher(name);

//                System.out.println(m.group("masking"));

                if (m.find()) {
                    return name.replaceFirst(NAME_MASKING_EN, MASKING_STAR.repeat(m.group("masking").length()));
                }
            }

            return name.replaceFirst(NAME_MASKING_KO,
                MASKING_STAR.repeat(name.length() - 2));
        }).forEach(System.out::println);
    }

    @Test
    void makingV2() {
        testSet.stream().map(name -> {
            System.out.printf("%s → ", name);
            if (name.length() < 3) {
                return name.substring(0, name.length() - 1) + MASKING_STAR;
            }

            if (name.charAt(0) >= 65 && name.charAt(0) <= 122) {
                Pattern pattern = Pattern.compile(NAME_MASKING_EN_FULL);
                Matcher m = pattern.matcher(name);

                if (m.find()) {
                    return name.replaceFirst(NAME_MASKING_EN_FULL,
                        MASKING_STAR.repeat(m.group().length()));
                }
            }

            return name.replaceFirst(NAME_MASKING_KO,
                MASKING_STAR.repeat(name.length() - 2));
        }).forEach(System.out::println);
    }
}