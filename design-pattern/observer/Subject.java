package observer;

public interface Subject {
    void addObserver(Observer obs);
    void deleteObserver(Observer obs);
    void notiObserver();
}
