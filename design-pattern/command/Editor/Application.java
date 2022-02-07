package command.Editor;

import command.Editor.ConcreteCommand.CopyCommand;
import command.Editor.ConcreteCommand.CutCommand;
import command.Editor.ConcreteCommand.PasteCommand;

class Application {
    String clipboard;
    Editor editors;
    Editor activeEditor;
    CommandHistory history;

    void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    void undo() {
        Command command = history.pop();
        if (command != null)
        command.undo();
    }


    public static void main(String[] args) {
        Application docs = new Application();

        Editor editor = new Editor(docs);
        editor.selection = "design pattern : command!";

        Command copy = new CopyCommand(editor);

        Button copyButton = new Button();
        copyButton.setCommand(copy);
        copyButton.click();

        Command paste = new PasteCommand(editor);

        Button pasteButton = new Button();
        pasteButton.setCommand(paste);
        pasteButton.click();

        Command cut = new CutCommand(editor);

        Button cutButton = new Button();
        cutButton.setCommand(cut);
        cutButton.click();
    }
}