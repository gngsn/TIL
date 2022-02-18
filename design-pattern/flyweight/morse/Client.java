package flyweight.morse;

public class Client {
    public static void main(String[] args) {
        Factory factory = new Factory();

        String name = "ggooogoole";
        System.out.println("원본 이름 : " + name);

        for (int i = 0; i < name.length(); i++) {
            System.out.print(name.charAt(i) + " => ");
            factory.getCode(name.charAt(i)).code();
        }

    }
}
