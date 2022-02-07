package command.Editor.ConcreteCommand;

import command.Editor.Command;
import command.Editor.Editor;

public class PasteCommand extends Command {
    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public void execute() {
        editor.paste();
    }
}
