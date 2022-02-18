package chainOfResponsibility;

public class Client {
    public static void main(String[] args) {
        Handler handler = new Handler();
//        System.out.println(handler.event("order"));
        System.out.println(handler.event("cancel"));
    }
}
