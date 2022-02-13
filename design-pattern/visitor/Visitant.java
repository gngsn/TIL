package visitor;

public class Visitant implements Visitor {
    private int total;
    private int num;

    public Visitant() {
        System.out.println("주문을 처리합니다.");
        this.total = 0;
        this.num = 0;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String order(Visitable visitable) {
        System.out.println("[ 상품 내역 ]");

        if (visitable instanceof Cart ) {
            Cart cart = (Cart) visitable;

            int total = cart.getPrice() * cart.getNum();
            this.num += cart.getNum();
            this.total += total;

            return cart.list() +
                    "\n주문 개수 : " + num +
                    ", 합계 : " + total;
        }

        throw new IllegalStateException();
    }
}
