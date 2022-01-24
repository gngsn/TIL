package mediator;

public class Component {

    protected Mediator dialog;

    public Component(Mediator dialog) {
        super();
        this.dialog = dialog;
    }

    public void click() {
        dialog.notify(this, "click");
    }

    public void keypress() {
        dialog.notify(this, "keypress");
    }
}