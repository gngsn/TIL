package chapter01.ticket;

public class TicketSeller {
    private TicketBooth ticketBooth;

    public TicketSeller(TicketBooth ticketBooth) {
        this.ticketBooth = ticketBooth;
    }

    public TicketBooth getTicketBooth() {
        return ticketBooth;
    }
}
