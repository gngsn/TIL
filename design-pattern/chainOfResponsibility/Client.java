package chainOfResponsibility;

public class Client {
    public static void main(String[] args) {
        Handler handler = new Handler();
        handler.event("order");
    }
}
