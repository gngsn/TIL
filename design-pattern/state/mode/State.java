package state.mode;

public interface State {
    void toggle(ModeSwitch modeSwitch);
}

class StateLight implements State {
    @Override
    public void toggle(ModeSwitch modeSwitch) {
        System.out.println("Turn off the Switch");
        modeSwitch.setState(new StateDark());
    }
}

class StateDark implements State {
    @Override
    public void toggle(ModeSwitch modeSwitch) {
        System.out.println("Turn on the Switch");
        modeSwitch.setState(new StateLight());
    }
}