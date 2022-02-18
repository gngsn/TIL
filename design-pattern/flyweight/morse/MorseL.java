package flyweight.morse;

public class MorseL implements Morse {
    public MorseL() {
        System.out.print("[Create MorseL] ");
    }

    @Override
    public void code() {
        System.out.println("*-** ");
    }
}
