package Interpreter.prefix;

import java.util.Iterator;

public class Context implements Iterator {
    private String[] tokens;
    private int position;
    private String start = "{";
    private String end = "}";

    public Context(String token) {
        this.tokens = token.split(" ");
        this.position = 0;
    }

    public boolean isStart() {
        return tokens[position].equals(start);
    }

    @Override
    public boolean hasNext() {
        return tokens.length > position && !tokens[position].equals(end);
    }

    @Override
    public String next() {
        return tokens[position++];
    }
}
