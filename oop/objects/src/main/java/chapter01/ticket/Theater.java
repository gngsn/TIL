package chapter01.ticket;

public class Theater {
    private TicketSeller seller;

    public Theater(TicketSeller seller) {
        this.seller = seller;
    }

    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = seller.getTicketBooth().getTicket();
            audience.getBag().setTicket(ticket);
        } else  {
            Ticket ticket = seller.getTicketBooth().getTicket();
            audience.getBag().minusCash(ticket.getFee());
            seller.getTicketBooth().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
