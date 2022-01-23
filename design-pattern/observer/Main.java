package observer;

import java.util.Enumeration;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Members subject = new Members();

        UserA userA = new UserA("Jiny");
        UserB userB = new UserB("Reilly");

        subject.attach(userA);
        subject.attach(userB);

        subject.noti();
    }
}
