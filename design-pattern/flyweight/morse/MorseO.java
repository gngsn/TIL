package flyweight.morse;

public class MorseO implements Morse {
    public MorseO() {
        System.out.print("[Create MorseO] ");
    }

    @Override
    public void code() {
        System.out.println("--- ");
    }
}
