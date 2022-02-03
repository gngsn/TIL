package Decorator.message;

public class Reverse extends MessageDecorator {

    public Reverse(Message message) {
        super(message);
    }

    public String reverser(String messageToReverse) {
        return new StringBuilder(messageToReverse).reverse().toString();
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("reverse : " + message);
        this.getDecoratedMessage().sendMessage(reverser(message));
    }
}
