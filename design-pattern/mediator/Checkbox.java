package mediator;

public class Checkbox extends Component {
    private boolean checked;

    public Checkbox(Mediator dialog) {
        super(dialog);

        checked = false;
    }

    public boolean checked() {
        return checked;
    }

    @Override
    public void click() {
        checked = !checked;
        dialog.notify(this, "check");
        super.click();
    }
}