package Interpreter.prefix;

import java.util.Stack;

public class Client {
    public static void main(String[] args) {
        String lex = "{ 1 1 + 4 + }";
        Stack<Terminal> stack = new Stack<>();
        Context context = new Context(lex);

        if (context.isStart()) {
            System.out.println("수식 : " + lex);
            while (context.hasNext()) {
                String token = context.next();

                if (token.chars().allMatch(Character::isDigit)) {
                    stack.push(new Terminal(token));
                } else if (token.equals("+")) {
                    Terminal left = stack.pop();
                    Terminal right = stack.pop();

                    Expression add = new Add(left, right);
                    String value = add.interpret();

                    System.out.println(left.interpret() + " + " + right.interpret() + " = " + value);
                    stack.add(new Terminal(value));
                }
            }
        }
        System.out.println("최종 결과 = " + stack.pop().interpret());
    }
}
