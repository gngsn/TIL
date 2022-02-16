package state.order;

import java.util.HashMap;

public class Order {
    private HashMap<String, State> state;

    public Order() {
        this.state = new HashMap<>() {{
            put("ORDER", new StateOrder());
            put("PAY", new StatePay());
            put("ORDERED", new StateOrdered());
            put("FINISH", new StateFinish());
        }};
    }

    public void process(String status) {
        this.state.get(status).process();
    }
}
