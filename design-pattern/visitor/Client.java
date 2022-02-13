package visitor;

public class Client {
    public static void main(String[] args) {
        // 하나의 element
        System.out.println("\n====== Cart ======");
        Cart cart = new Cart("coffee", 4500, 2);
        System.out.println(cart.accept(new Visitant()));

        System.out.println("\n====== Cart List ======");

        Cart[] list = {
                new Cart("noodle", 900, 2),
                new Cart("icecream", 1500, 1),
                new Cart("drink", 2800, 1)
        };
        Visitant visitant = new Visitant();

        for (Cart elem : list) {
            System.out.println(elem.accept(visitant));
        }

        System.out.println("\n주문 합계: " + visitant.getTotal());
    }

}
