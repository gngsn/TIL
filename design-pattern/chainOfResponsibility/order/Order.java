package chainOfResponsibility.order;

public class Order extends Chain {
    public String execute(String event) {
        if (event.equals("order")) {
            return "주문을 처리합니다.";
        }

        return this.next.execute(event);
    }
}
