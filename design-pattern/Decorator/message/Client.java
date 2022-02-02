package Decorator.message;

public class Client {
    public static void main(String[] args) {
        String message = "seCrEtMesSagE";
        System.out.println("Message to send : " + message);

        Message after = new Reverse(new Encript(new BaseComponent()));
        after.sendMessage(message);
    }
}
