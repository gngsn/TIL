package Decorator.message;

public abstract class MessageDecorator implements Message {
    protected Message base;

    public MessageDecorator(Message message) {
        this.base = message;
    }

    public abstract void sendMessage(String message);

    public Message getDecoratedMessage() {
        return this.base;
    }
}
