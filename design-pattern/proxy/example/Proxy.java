package proxy.example;

public class Proxy implements Subject{
    private Subject object;
    private Integer permit;
    int countAction5 = 0;

    public Proxy() {}

    public Proxy(Subject real) {
        System.out.println("Proxy 객체가 생성되었습니다.");
        this.object = real;
    }

    public Proxy(Subject real, int permit) {
        System.out.println("Proxy 객체가 생성되었습니다.");
        this.object = real;
        this.permit = permit;
    }

    public Proxy(int permit) {
        System.out.println("Proxy 객체가 생성되었습니다.");
        this.permit = permit;
    }

    public String action1() {
        System.out.println("Proxy::action1을 호출합니다.");
        return "action1은 기능이 대체되었습니다.";
    }

    public String action2() {
        System.out.println("Proxy::action2을 호출합니다.");
        String msg = this.object.action2();

        if (msg == null) {
            return "실체 객체에서 문자열을 반환 받지 못했습니다.";
        }

        return msg;
    }

    // 가상 프록시
    public String action3() {
        System.out.println("Proxy::action3을 호출합니다.");
        if (this.object == null) this.real();
        String msg = this.object.action3();

        if (msg == null) {
            return "실체 객체에서 문자열을 반환 받지 못했습니다.";
        }

        return msg;
    }

    // 보호용 프록시
    public String action4() {
        System.out.println("Proxy::action4을 호출합니다.");

        if (this.permit != 1) {
            return "action4 실행 권한이 없습니다.";
        }

        return this.object.action4();
    }

    // 스마트 참조자
    public String action5() {
        System.out.println("Proxy::action5을 호출합니다.");
        countAction5++;  // 추가 동작
        return "action5 작업을 처리합니다.";
    }

    public void real() {
        System.out.println("실체 객체를 생성합니다.");
        this.object = new RealSubject();
    }

    public boolean isProxy() {
        return true;
    }

//    public void call(String method, String args) {
//        if (method_exists(this.object, method)) {
//            this.object.method(args);
//        } else {
//            System.out.println("method는 실제 존재하는 메서드가 아닙니다.");
//            var_dump(args);
//        }
//    }
}
