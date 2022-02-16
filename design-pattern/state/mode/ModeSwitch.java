package state.mode;

class ModeSwitch {
    private State modeState = new StateDark();

    ModeSwitch() {
        System.out.println("Switch was created.\n");
    }

    void setState(State modeState) {
        this.modeState = modeState;
    }

    void onSwitch() {
        modeState.toggle(this);
    }
}
