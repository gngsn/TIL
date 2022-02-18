package flyweight.morse;

public class MorseG implements Morse {
    public MorseG() {
        System.out.print("[Create MorseG] ");
    }

    @Override
    public void code() {
        System.out.println("**-* ");
    }
}
