package command.Editor.ConcreteCommand;

import command.Editor.Command;
import command.Editor.Editor;

public class CutCommand extends Command {

    public CutCommand(Editor editor) {
        super(editor);
    }

    @Override
    public void execute() {
        editor.copy();
        editor.delete();
    }
}
