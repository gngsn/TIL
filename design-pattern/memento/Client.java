package memento;

public class Client {
    public static void main(String[] args) {
        Originator origin = new Originator();
        CareTaker care = new CareTaker();

        String obj = "상태1 : 안녕하세요.";

        origin.setState(obj);
        care.push(origin);

        obj = "상태2 : Hello, nice to meet you";
        System.out.println(obj);
        origin.setState(obj);
        care.push(origin);

        obj = "상태3 : 다시 안녕 ~";
        System.out.println(obj);
        origin.setState(obj);
        care.push(origin);

        obj = care.undo(origin);
        System.out.println(obj);

        obj = care.undo(origin);
        System.out.println(obj);

        obj = care.undo(origin);
        System.out.println(obj);
    }
}
