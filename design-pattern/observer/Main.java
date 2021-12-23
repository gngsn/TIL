package observer;

public class Main {
    public static void main(String[] args) {
        Members subject = new Members();

        UserA userA = new UserA("Jiny");
        UserB userB = new UserB("Reilly");

        subject.addObserver(userA);
        subject.addObserver(userB);

        subject.notiObserver();
    }
}
