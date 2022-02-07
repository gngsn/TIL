package command.Editor.ConcreteCommand;

import command.Editor.Command;
import command.Editor.Editor;

public class CopyCommand extends Command {
    public CopyCommand(Editor editor) {
        super(editor);
    }

    @Override
    public void execute() {
        editor.copy();
    }
}
