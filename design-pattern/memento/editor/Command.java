package memento.editor;

import java.util.Stack;

public class Command {
    protected Stack<Editor.Snapshot> stack;

    public Command() {
        this.stack = new Stack<>();
    }

    public void makeBackup(Editor origin) {
        Editor.Snapshot memento = origin.create();
        stack.push(memento);
    }

    public Editor undo(Editor editor) {
        Editor.Snapshot memento = stack.pop();
        editor.restore(memento);
        return editor;
    }
}
