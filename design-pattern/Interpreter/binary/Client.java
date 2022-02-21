package Interpreter.binary;

public class Client {
    public Context ic;

    public Client(Context ic) {
        this.ic = ic;
    }

    public String interpret(String str) {
        Expression exp;
        int expNum = Integer.parseInt(str.substring(0,str.indexOf(" ")));

        if(str.contains("16진수")) {
            exp = new IntToHex(expNum);
        } else if (str.contains("2진수")) {
            exp = new IntToBinary(expNum);
        } else return str;

        return exp.interpret(ic);
    }

    public static void main(String[] args) {
        String str1 = "28 의 2진수 ";
        String str2 = "28 의 16진수 ";

        Client ec = new Client(new Context());
        System.out.println(str1+"= "+ec.interpret(str1));
        System.out.println(str2+"= "+ec.interpret(str2));
    }
}