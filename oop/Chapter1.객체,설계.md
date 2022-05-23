<small><i>22.05.22 SUN<i></small>

# Chapter 1. 객체, 설계


> "이론이 먼저일까, 실무가 먼저일까?"

> 실무가 어느정도 발전하고 난 다음에야 비로소 실무의 실용성을 입증할 수 잇는 이론이 서서히 그 목습을 갖춰가기 시작하고, 해당 분야가 충분히 성숙해지는 시점에 이르러서야 이론이 실무를 추워하게 된다. 
> 
> *로버트 L. 글래스*


필자는 추상적인 개념과 이론은 훌륭한 코드를 작성하는 데 필요한 도구일 뿐, **프로그래밍을 통해 개념과 이론을 배우는 것**이 개념과 이론을 통해 프로그래밍을 배우는 것보다 더 훌륭한 학습 방법이라고 주장한다.

## 1. 티켓 판매 앱 구현

**공연 입장 고객 입장 방법**

- 이벤트 당첨 : 당첨 여부 확인 후 무료 관람
- 그 외     : 티켓 판매 후 입장

chapter01의 ticket.v1 package를 구현해보자.

<br/>

<details>
<summary>ticket v1</summary>

``` java
public class Invitation {
    private LocalDateTime when;
}
```

```java
public class Ticket {
   private Long fee;

   public Long getFee() {
      return fee;
   }
}
```

```java
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
```

```java
public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }
}
```

```java
public class TicketBooth {
   private Long price;
   private List<Ticket> tickets = new ArrayList<>();

   public TicketBooth(Long price, Ticket ...tickets) {
      this.price = price;
      this.tickets.addAll(Arrays.asList(tickets));
   }

   public Ticket getTicket() {
      return this.tickets.get(0);
   }

   public void minusPrice(Long cash) {
      this.price -= cash;
   }

   public void plusPrice(Long cash) {
      this.price += cash;
   }
}
```

```java
public class TicketSeller {
    private TicketBooth ticketBooth;

    public TicketSeller(TicketBooth ticketBooth) {
        this.ticketBooth = ticketBooth;
    }

    public TicketBooth getTicketBooth() {
        return ticketBooth;
    }
}
```

```java
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
```
</details>

<br/><br/>


2. 무엇이 문제인가
   1. 예상을 빗나가는 코드
   2. 변경에 취약한 코드

<br/>

### 모듈의 목적

Robert C.Martin - ⌜클린 소프트웨어: 애자일 원칙과 패턴, 그리고 실천 방법⌟에서 소프트웨어 모듈이 가져야 하는 세 가지 기능에 관해 설명한다. 
여기서 모듈이란 크기와 상관없이 클래스나 패키지, 라이브러리와 같이 프로그램을 구성하는 임의의 요소를 의미한다.



> 모든 소프트웨어 모듈에는 세 가지 목적이 있다.
> 
> 
> 첫 번째 목적은 실행 중에 제대로 동작하는 것이다. 이것은 모듈의 존재 이유라고 할 수 있다.
> 
> 두 번째 목적은 변경을 위해 존재하는 것이다. 대부분의 모듈은 생명주기 동안 변경되기 때문에 간단한 작업만으로도 변경이 가능해야 한다. 변경하기 어려운 모듈은 제대로 동작하더라도 개선해야 한다.
> 
> 세 번째 목적은 코드를 읽는 사람과 의사소통하는 것이다. 모듈은 특별한 훈련 없이도 개발자가 쉽게 읽고 이해할 수 있어야 한다.
> 읽는 사람과 의사소통할 수 없는 모듈은 개선해야 한다.
> 

즉, 모듈은 제대로 실행되어야 하고, 변경에 용이하며, 이해하기 쉬워야 한다.

<br/><br/>

위의 코드에는 두 가지 문제가 있다.

-  

### 예상을 빗나가는 코드

ticket.v1은 어떤 문제가 있을까?

실행 코드인 `Theater`의 `enter` 메소드를 살펴보면,

``` java
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
```

관람객(audience)과 판매원(seller)이 소극장의 통제를 받은 수동적인 객체로 구현되었다는 문제가 볼 수 있다.
관람객의 입장에서 보면 자신의 가방 안의 내용물을 확인해서 돈을 가져간다.
가장 큰 문제는 이렇게 티켓을 처리하는 것이 Theater 내에서 발생한다는 것이다.

객체의 이름만 보면 TicketSeller가 표를 확인하고 계산을 해야 하며, Theater에 입장해야 한다.
하지만 v1 코드는 예상과는 다르며, **코드를 읽는 사람과 제대로 의사소통하지 못하고 있다**. 

또 

3. 설계 개선하기
   1. 자율성을 높이자 
   2. 무엇이 개선됐는가
   3. 어떻게 한 것인가
   4. 캡슐화와 응집도
   5. 절차지향과 객체지향
   6. 책임의 이동
4. 객체지향 설계
   1. 설계가 왜 필요한가
   2. 객체지향 설계 
