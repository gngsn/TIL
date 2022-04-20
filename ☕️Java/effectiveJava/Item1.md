## 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라.

### 장점

#### 📌 1. 이름을 가질 수 있다. (동일한 시그니처의 생성자를 두개 가질 수 없다.)

<br/>


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

<br/><br/>

#### 📌 2. 호출될 때마다 인스턴트를 새로 생성하지 않아도 된다. (Boolean.valueOf)

<small>[2강]</small>

여러개의 인스턴스가 있어서는 안될 때, 인스턴스를 만드는 방법을 통제해야 할 때가 있다.

생성자를 `private` 으로 만들자.

``` java
public class Settings {
    private boolean useAutoSteering;

    private boolean useABS;

    private Difficulty difficulty;
    private Settings() {}
    private static final Settings SETTINGS = new Settings();
    public static Settings newInstance() {
        return SETTINGS;
    }
}

```

Settings의 인스턴스를 가져가는 방법이 오로지 정적 팩토리 메서드를 이용한 방법 밖에 남지 않음

가져가다 보면, 여러개의 인스턴스를 만들 수 없게 된다.

생성자로는 인스턴스의 생성을 컨트롤 할 수 없다. 

객체 생성을 사용하는 쪽에서 어떠한 제한없이 만들 수 있다.

정적 팩토리로는 객체 생성을 팩토리 내에서 제한하겠다는 의미가 된다.

<br/>

예를 들면, Java의 Boolean 객체를 들 수 있는데, Boolean은 아래와 같이 정의 되어있다(일부)

<br/>

``` java 

public final class Boolean implements java.io.Serializable,
                                      Comparable<Boolean>
{
    public static final Boolean TRUE = new Boolean(true);

    public static final Boolean FALSE = new Boolean(false);

    // ...
}

```


<br/>

**플라이웨이트 패턴?** 가령 자주 사용하는 인스턴스들 (e.g. 폰트) 
인스턴스를 통제하는 방법. 미리 자주 사용하는 객체들을 만들어두고 필요할 때 꺼내 쓸 수 있기 때문에 언급된다.

<br/><br/>

<small>[3강]</small>

#### 📌 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다. (인터페이스 기반 프레임워크, 인터페이스에 정적 메소드)


리턴하는 반환 타입에 호환 가능한 객체로 리턴을 할 수 있다.

``` java
public class HelloServiceFactory {
    public static HelloService of(String lang) {
        if (lang.equals("ko")) {
            return new KoreanHelloService();
        } else {
            return new EnglishHelloService();
        }
    }
}
```



#### 📌 4. 입력 매개변수가 따라 매번 다른 클래스의 객체를 반환할 수 있다. (EnumSet)

#### 📌 5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다. (서비스 제공자 프레임워크) 

<br/><br/>


### 단점

• 상속을 하려면 public이나 protected 생성하기 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없 다.

• 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.