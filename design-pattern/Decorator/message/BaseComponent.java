package Decorator.message;

public class BaseComponent implements Message {
    @Override
    public void sendMessage(String message) {
        System.out.println("Message to send : " + message);
    }
}
