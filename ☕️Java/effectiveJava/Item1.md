## 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라.

<small>[1강]</small>

생성자가 대부분의 상황에서는 적절할 수 있지만, 정적 팩토리 메서드가 유효한 경우가 있다. 

디자인 패턴의 팩터리 추상 팩토리나 상관이 전혀 없다.
이름 그대로 **정적인 팩토리** 그 자체.

✔️ 생성자의 시그니처가 겹치는 경우
만들어주는 인스턴스를 새로 만들 필요가 없다.

<br/>

<details>
<summary>BEFORE CODE: 생성자의 시그니처가 겹치는 경우</summary>

<br/>

``` java

public class Order {
    private boolean prime;
    private boolean urgent;
    private Product product;

    public Order(Product product, boolean prime) {
        this.product = product;
        this.prime = prime;
    }

    public Order(boolean urgent, Product product) {
        this.urgent = urgent;
        this.product = product;
    }
}
```

</details>
<br/>

<details>
<summary>AFTER CODE</summary>

<br/>

``` java

public class Order {
    private boolean prime;
    private boolean urgent;
    private Product product;

    public static Order primeOrder(Product product) {
        Order order = new Order();
        order.prime = true;
        order.product = product;
        return order;
    }

        public static Order urgentOrder(Product product) {
        Order order = new Order();
        order.urgent = true;
        order.product = product;
        return order;
    }
}
```

</details>
