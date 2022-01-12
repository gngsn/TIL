package proxy.example;

public class RealSubject implements Subject {
    public RealSubject() {
        System.out.println("RealSubject 객체가 생성되었습니다.");
    }

    public String action1() {
        System.out.println("RealSubject::action1을 호출합니다.");
        return "실제 action1 작업을 처리합니다.";
    }

    public String action2() {
        System.out.println("RealSubject::action2을 호출합니다.");
        return "실제 action2 작업을 처리합니다.";
    }

    public String action3() {
        System.out.println("RealSubject::action3을 호출합니다.");
        return "실제 action3 작업을 처리합니다.";
    }

    public String action4() {
        System.out.println("RealSubject::action4을 호출합니다.");
        return "실제 action4 작업을 처리합니다.";
    }

    public String action5() {
        System.out.println("RealSubject::action5을 호출합니다.");
        return "실제 action5 작업을 처리합니다.";
    }
}
