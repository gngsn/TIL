package Decorator.message;

public class BaseMessage implements Message {
    @Override
    public void sendMessage(String message) {
        System.out.println("Message to send : " + message);
    }
}
