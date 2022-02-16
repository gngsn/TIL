package state.light;

public class LightLamp implements LightState {
    private boolean lightState;

    public LightLamp() {
        this.lightState = false;
    }

    @Override
    public LightLamp lightOn() {
        System.out.println("전구를 on 합니다.");
        this.lightState = true;
        return this;
    }

    @Override
    public LightState lightOff() {
        System.out.println("전구를 off 합니다.");
        this.lightState = false;
        return this;
    }

    @Override
    public boolean lightState() {
        return this.lightState;
    }
}
