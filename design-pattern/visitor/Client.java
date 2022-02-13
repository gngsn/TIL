package visitor;

public class Client {
    public static void main(String[] args) {
        Cart[] list = {
                new Cart("noodle", 900, 2),
                new Cart("icecream", 1500, 1),
                new Cart("drink", 2800, 1)
        };
        Visitant visitant = new Visitant();

        for (Cart cart : list) {
            System.out.println(cart.accept(visitant));
        }

        System.out.println("\n주문 합계: " + visitant.getTotal());
    }

}
