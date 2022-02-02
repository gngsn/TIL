package Decorator.message;

public abstract class Decorator implements Message {
    protected Message base;

    public Decorator(Message message) {
        this.base = message;
    }

    public abstract void sendMessage(String message);

    public Message getDecoratedMessage() {
        return this.base;
    }
}
