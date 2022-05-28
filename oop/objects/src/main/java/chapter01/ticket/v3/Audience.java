package chapter01.ticket.v3;

import chapter01.ticket.v1.Bag;
import chapter01.ticket.v1.Ticket;

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }

    public long buy(Ticket ticket) {
        if (bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else  {
            bag.minusCash(ticket.getFee());
            bag.setTicket(ticket);
            return ticket.getFee();
        }
    }
}
