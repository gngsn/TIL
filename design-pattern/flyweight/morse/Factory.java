package flyweight.morse;

import java.util.HashMap;

public class Factory {
    private HashMap<Character, Morse> pool = new HashMap<>();

    public Morse getCode(Character ch) {
        if (!pool.containsKey(ch)) {
            if (ch == 'e' || ch == 'E') {
                pool.put(ch, new MorseE());
            } else if (ch == 'g' || ch == 'G') {
                pool.put(ch, new MorseG());
            } else if (ch == 'l' || ch == 'L') {
                pool.put(ch, new MorseL());
            } else if (ch == 'o' || ch == 'O') {
                pool.put(ch, new MorseO());
            } else {
                throw new IllegalArgumentException("Not Support Code");
            }
        }
        return this.pool.get(ch);
    }
}
