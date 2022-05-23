package chapter01.ticket.v1;

public class TicketSeller {
    private TicketBooth ticketBooth;

    public TicketSeller(TicketBooth ticketBooth) {
        this.ticketBooth = ticketBooth;
    }

    public TicketBooth getTicketBooth() {
        return ticketBooth;
    }
}
