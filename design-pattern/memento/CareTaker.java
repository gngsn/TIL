package memento;

import java.util.Stack;

public class CareTaker {
    protected Stack<Memento> stack;

    public CareTaker() {
        this.stack = new Stack<>();
    }

    public void push(Originator origin) {
        Memento memento = origin.create();
        stack.push(memento);
    }

    public String undo(Originator origin) {
        Memento memento = stack.pop();
        origin.restore(memento);
        return origin.getState();
    }
}
