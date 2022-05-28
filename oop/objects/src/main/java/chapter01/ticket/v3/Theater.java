package chapter01.ticket.v3;

public class Theater {
    private TicketSeller seller;

    public Theater(TicketSeller seller) {
        this.seller = seller;
    }

    public void enter(Audience audience) {
        seller.sellTo(audience);
    }
}
