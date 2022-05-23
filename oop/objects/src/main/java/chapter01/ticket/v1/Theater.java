package chapter01.ticket.v1;

public class Theater {
    private TicketSeller seller;

    public Theater(TicketSeller seller) {
        this.seller = seller;
    }

    public void enter(Audience audience) {
        Ticket ticket = seller.getTicketBooth().getTicket();

        if (audience.getBag().hasInvitation()) {
            audience.getBag().setTicket(ticket);
        } else  {
            audience.getBag().minusCash(ticket.getFee());
            seller.getTicketBooth().plusPrice(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
