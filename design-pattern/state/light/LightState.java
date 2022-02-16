package state.light;

public interface LightState {
    public LightState lightOn();
    public LightState lightOff();
    public boolean lightState();
}
