package flyweight.morse;

public class MorseE implements Morse {
    public MorseE() {
        System.out.print("[Create MorseE] ");
    }

    @Override
    public void code() {
        System.out.println("* ");
    }
}
