package memento.basic;

public class Client {
    public static void main(String[] args) {
        Originator origin = new Originator();
        CareTaker care = new CareTaker();

        origin.setState("상태1 : 안녕하세요.");
        care.push(origin);
        System.out.println(origin.getState());

        origin.setState("상태2 : Hello, nice to meet you");
        care.push(origin);
        System.out.println(origin.getState());


        origin.setState("상태3 : 다시 안녕 ~");
        care.push(origin);
        System.out.println(origin.getState());

        System.out.println("\n=== 복구 ===\n");

        String obj = care.undo(origin);
        System.out.println(obj);

        obj = care.undo(origin);
        System.out.println(obj);

        obj = care.undo(origin);
        System.out.println(obj);
    }
}
