package chapter01.ticket.v3;

import chapter01.ticket.v1.Ticket;
import chapter01.ticket.v1.TicketBooth;

public class TicketSeller {
    private TicketBooth ticketBooth;

    public TicketSeller(TicketBooth ticketBooth) {
        this.ticketBooth = ticketBooth;
    }

//    public TicketBooth getTicketBooth() {
//        return ticketBooth;
//    }

    /**
     * v2: from v1 Theater Class - enter()
     *
     * @param audience
     */
    public void sellTo(Audience audience) {
        ticketBooth.plusPrice(audience.buy(ticketBooth.getTicket()));
    }
}
