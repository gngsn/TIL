package state.light;

public class Client {
    public static void main(String[] args) {
        LightState obj = new LightLamp();
        System.out.println(obj.lightOn().lightState());
        System.out.println(obj.lightOff().lightState());
    }
}
