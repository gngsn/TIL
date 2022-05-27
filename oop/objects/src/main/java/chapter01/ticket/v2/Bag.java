package chapter01.ticket.v2;

public class Bag {
    private Long cash;
    private Invitation invitation;
    private Ticket ticket;

    public Bag(Long cash) {
        this(cash, null);
    }

    public Bag(Long cash, Invitation invitation) {
        this.cash = cash;
        this.invitation = invitation;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public boolean hasInvitation() {
        return invitation != null;
    }

    public boolean hasTicket() {
        return ticket != null;
    }

    public void minusCash(Long cash) {
        this.cash -= cash;
    }

    public void plusCash(Long cash) {
        this.cash += cash;
    }
}
