package state.order;

public class Client {
    public static void main(String[] args) {
        Order order = new Order();
        order.process("ORDER");
        order.process("PAY");
        order.process("ORDERED");
        order.process("FINISH");
    }
}
