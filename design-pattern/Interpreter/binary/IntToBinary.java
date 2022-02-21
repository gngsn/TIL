package Interpreter.binary;

public class IntToBinary implements Expression {
    private int i;

    public IntToBinary(int i) {
        this.i = i;
    }

    @Override
    public String interpret(Context ic) {
        return ic.getBinaryFormat(i);
    }
}