package Interpreter.binary;

public class IntToHex implements Expression {
    private int i;

    public IntToHex(int i) {
        this.i = i;
    }

    @Override
    public String interpret(Context ic) {
        return ic.getHexadecimalFormat(i);
    }
}