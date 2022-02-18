package chainOfResponsibility;

public class Handler {
    public String event(String message) {
        if (message.equals("order")) {
            return (new Order()).execute();
        } else if (message.equals("cancel")) {
            return (new Cancel()).execute();
        }
        return "동작이 없습니다.";
    }
}
