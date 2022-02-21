package Interpreter.prefix;

// NonTerminalExpression
public class Add implements Expression {
    private Expression left;
    private Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String interpret() {
        int left = Integer.parseInt(this.left.interpret());
        int right = Integer.parseInt(this.right.interpret());
        return Integer.toString(left + right);
    }
}
