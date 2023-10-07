/**
 * 📌 JDK 17
 * compile: ~/Library/Java/JavaVirtualMachines/temurin-17.0.8/Contents/Home/bin/javac DoubleBug.java
 * running: cd ./Java/jdk21 || ~/Library/Java/JavaVirtualMachines/temurin-17.0.8/Contents/Home/bin/java DoubleBug
 * -
 * Result: 9.999999999999999E22
 *
 * 📌 JDK 21
 * compile: ~/Library/Java/JavaVirtualMachines/openjdk-21/Contents/Home/bin/javac DoubleBug.java
 * running: ~/Library/Java/JavaVirtualMachines/openjdk-21/Contents/Home/bin/java DoubleBug
 * -
 * Result: 1.0E23
 */
public class DoubleBug {
    public static void main(String[] args) {
        String str = Double.toString(1e23);
        System.out.println(str);
    }
}