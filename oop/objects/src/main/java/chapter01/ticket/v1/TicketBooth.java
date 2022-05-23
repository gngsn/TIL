package chapter01.ticket.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketBooth {
    private Long price;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketBooth(Long price, Ticket ...tickets) {
        this.price = price;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public Ticket getTicket() {
        return this.tickets.get(0);
    }

    public void minusPrice(Long cash) {
        this.price -= cash;
    }

    public void plusPrice(Long cash) {
        this.price += cash;
    }
}
