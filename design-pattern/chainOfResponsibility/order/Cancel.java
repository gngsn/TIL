package chainOfResponsibility.order;

public class Cancel extends Chain {
    public String execute(String event) {
        if (event.equals("cancel")) {
            return "주문을 취소합니다.";
        }

        return this.next.execute(event);
    }
}
