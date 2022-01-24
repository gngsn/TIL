package mediator;

public interface Mediator {
    void notify(Component sender, String event);
}