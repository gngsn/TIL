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
        System.out.println("== 상품 내역 ==");

        if (visitable instanceof Cart ) {
            this.num++;
            this.total += ((Cart) visitable).getPrice() * ((Cart) visitable).getNum();
            String msg = ((Cart) visitable).list() +
                    "\n합계 : " + total;
            return msg;
        }

        throw new IllegalStateException();
    }
}
