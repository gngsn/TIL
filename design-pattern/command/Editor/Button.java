package command.Editor;

public class Button {
    Command cmd;

    public void setCommand(Command command) {
        this.cmd = command;
    }

    public void click() {
        this.cmd.execute();
    }
}
