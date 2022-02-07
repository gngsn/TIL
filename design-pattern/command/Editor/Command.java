package command.Editor;


public abstract class Command {
    protected Editor editor;
//    protected String backup;

    public Command(Editor editor) {
        this.editor = editor;
    }

    public abstract void execute();

    public void undo() {
        // undo logic
    }
}

