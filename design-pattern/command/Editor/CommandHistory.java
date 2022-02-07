package command.Editor;

import java.util.Stack;

public class CommandHistory {
    Stack<Command> history = new Stack<>();

    void push(Command command) {
        history.push(command);
    }

    Command pop() {
        return history.pop();
    }
}
