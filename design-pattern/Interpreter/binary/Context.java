package Interpreter.binary;

public class Context {
    public String getBinaryFormat(int i) {
        return Integer.toBinaryString(i);
    }
    public String getHexadecimalFormat(int i) {
        return Integer.toHexString(i);
    }
}