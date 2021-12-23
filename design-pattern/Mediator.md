# Mediator Pattern
p.424

| 한 줄 소개 | 중재자 패턴은 상태 변화가 있을 때 직접 처리하지 않고, 변화에 대한 행위를 독립된 객체로 분리하고 상채를 통보. 수신자는 전달 받은 상태의 객체를 호출



## 19.1 중재

어떤 문제를 해결하거나 조정을 돕는 것. 분산된 객체의 행동을 중재

<br/>

### 19.1.1 분산

객체지향의 특징은 모든 행동을 하나의 객체에 집중하여 처리하지 않음

행동은 작은 단위로 분리되고, 목적 동작을 수행하기 위해 분리된 행동을 연결

분리된 객체는 각각의 독립된 행동을 가진 객체

<br/>

### 19.1.2 상호 작용

객체지향은 하나의 커다란 행동을 작은 단위의 객체로 분산

객체의 역할을 보다 작은 객체로 분할하는 이유는 동작을 재사용하기 위함

객체는 목적으로 하는 행동을 수행하기 위해 객체 간에 의존 관계가 발생함. 이렇게 분리된 객체의 의존 관계는 구조적으로 복잡한 연결 고리를 생성

하나의 목적을 완전히 이루기 위해서는 연돤된 모든 객체의 행동이 필요. 분산된 작은 객체들 간에 정보를 주고받기 위해 복잡한 메시지 호출 동작이 발생

<br/>

### 19.1.3 행동 제약

분산된 객체는 각각 세분화된 행동을 가지며, 분리된 객체는 서로 강력한 의존적 결합 구조를 가짐

또한 분리된 객체 수가 늘어날수록 구조는 더욱 복잡해지고 결합도는 단단해지지만, 객체의 행동을 분산시키는 목적은 객체의 구성단위를 재사용하기 위함

객체의 행동 제약 : 강력한 결합 구조는 객체의 재사용을 방해하는 요인. 이런 이유로 객체를 분리했지만, 분리된 객체는 강한 의존성 때문에 독립적인 행동을 할 수 없음

객체를 단순히 분할하는 것은 복합 객체와 같은 큰 덩어리

<br/>

### 19.1.4 중재자 패턴

mediator는 ‘중재인, 조정관, 중재기관’이라는 뜻이 있음.

> 1. 회의, 토론 등에서는 여러 사람의 패털과 사회자를 볼 수 있는데, 사회자는 여러 패널의 발언권을 제어하고 이를 정리하는 역할을 함.

> 2. 각종 모임에서 총무가 존재하는데, 총무는 각 회원에게 회식 비용을 받아 결제를 대행함.  회원이 일일이 계산하는 것은 복잡하기 때문.

<br/>

이해 관계자가 많은 경우 이를 정리하는 중개인이 있으면 일을 보다 편리하게 처리할 수 있음 

<br/>

### 19.1.5 중재를 위해 관계 정리하기

객체의 행동을 중재하기 위해서는 복잡한 구조적 관계를 개선해야 함

분산된 객체는 매우 복잡하게 엮여 있음. 객체는 상호 관계를 가지며 호출되면서 결합도가 급속히 증가함

중재자는 객체 간 복잡한 상호 관계를 중재하며 객체 간에 복잡한 관계로 묶인 것을 재구성함

서로 의존적인 M:N 의 관계를 가진 객체를 느슨한 1:1 관계로 변경함

중재자 패턴은 객체의 관계를 하나의 객체로 정리하는 패턴임

<br/>

### 19.1.6 소결합

복잡하게 얽히는 객체의 연관 관계를 중재자 객체에게 집중합니다. 중간 매개 객체에 집중함으로써 관계의 결합도를 해소함

여러 클래스가 서로 직접적으로 호출해 복잡한 관계를 느슨한 결합으로 만들기 때문에 객체의 재사용과 유연성을 높이며, 복잡한 통신과 제어를 한 곳에 집중하여 처리하는 효과도 있음

<br/>

### 19.2 중재자

중재자는 하나의 중재자와 여러 동료 객체 Colleague로 구성되어 있으며, 동료 객체의 강력한 결합 구조를 느슨한 결합 구조로 개선

동료 객체가 서로 통신하는 것이 아니라 중재자를 통해 통신

<br/>

### 19.2.2 인터페이스

중재자는 여러 동료 객체의 관계를 중재하기 때문에 다양한 메서드가 필요

관계를 중개하는 모든 동료 객체에 적용되면 이 경우 공통괸 인터페이스가 필요

```java
public interface Mediator {
	void createColleague();
}
```

중재자는 관리할 동료 객체의 목록을 관리하며 다음과 같이 Client 실행 코드에서 동적으로 동료를 생성하고 중재 목록에 추가할 수 있음.

<br/>

```java
// 중재자의 공통된 부분을 추상화로 변경하여 개선한 코드

public abstract class Mediator {
	protected ArrayList<Colleague> colleagues;
	
	public void addColleague(Colleague obj) {
		colleague.add(obj);
	}

	public void createColleague();
}
```

<br/>

추상 클래스의 `colleagues` 를 이용해 복수의 동료 객체를 관리. 접근 속성은 추상 클래스를 상속할 수 있도록 `protected` 로 설정 

`colleagues` 는 복수의 객체를 보관하는 스택구조. `addColleague` 메서드는 스택 배열에 동료 객체를 추가함

<br/>

### 19.2.3 concreteMediator 클래스

실제적인 중재 동작은 concreteMediator 클래스에 선언


```java
class Server extends Mediator {
	public Server {
		System.out.println("중재자 생성");
		this.colleague = new ArrayList<Colleague>();
	}

	public void createColleague() {
		// 동료 객체 목록을 초기화
	}

	// 중재 기능
	public void mediate(String data, String user) {
		System.out.println(user + "의 중재자 생성");
		for (Colleague colleague: colleagues) {
			colleague.message(data);
		}
	}
}
```

<br/>

### 19.2.4 서브 클래스 제한

중개하는 행동마다 concreteMediator 클래스를 생성합니다. 변경되는 경우 mediator 인터페이스를 적용 받은 또 다른 서브 클래스를 생성함. 이를 위해 미리 인터페이스/추상화를 실시한 것

하나의 concreteMediator 클래스에는 하나의 mediate() 메서드만 구현하고 분산된 객체는 하나의 서브 객체로 제한함

<br/>

### 19.2.5 집중적 통제

<br/>

## 19.3 동료 객체

동료 객체는 분산된 행동들의 독립된 객체. 동료 객체는 직접 통신하지 않고 중재자를 통해서만 상호 통신하도록 제한

<br/>

### 19.3.1 통신 경로

중재자 패턴은 객체의 강력한 구조적 결합 문제점을 해결

중재자 패턴을 이용하더라도 처리할 동료의 객체가 많으면 또 다른 문제가 발생. 중재자는 하나의 객체 요청에 대해 모든 객체로 통보를 처리해야 하므로 경로의 수가 증가함 중재자 체턴을 설계할 때는 경로의 수가 증가함에 따라 성능이 저하되지 않도록 신경 써서 구상해야 함

<br/>

### 19.3.2 인터페이스

```java
interface Colleague {
	public void setMediator(Mediator mediator);
}
```

복합 객체의 구조를 갖고 있으며, 공통된 기능을 포함하기 위해 추상화로 변경

```java
public abstract class Colleague {
	protected Mediator mediator;

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}
}
```

<br/>

### 19.3.3 concreteColleague

추상 클래스의 Colleague를 상속받아 실제적인 concreteColleague를 생성한다. 처리할 행동에 따라 다수의 concreteColleague를 생성할 수도 있음

```java
class User extends Colleague {
	protected String name;

	public User(String name) {
		this.name = name;
	}

	// 사용자 이름 확인
	public String getName() {
		return this.name;
	}

	// 메시지를 전달
	public void send(String data) {
		// 중개 서버로 메시지를 전송함
		this.mediator.mediate(data, this.name);
	}

	public void message(String data) {
		System.out.println(data);
	}
}
```

Mediator 패턴은 요청과 부여를 통해 Colleague 객체 간의 종속성을 제거

중개 기능을 캡슐화하고 각각의 객체는 행동보다 객체 연결에만 관심을 갖게 됨

<br/>

## 19.4 기본 실습

<br/>

### 19.4.1 관계 설정

```java
public class MediatorPattern {
  public static void main(String[] args) {
		Mediator mediator = new Server();

		User user1 = new User("james");
		user1.setMediator(mediator);
		mediator.addColleague(user1);

		User user2 = new User("jiny");
		user2.setMediator(mediator);
		mediator.addColleague(user2);

		User user3 = new User("eric");
		user3.setMediator(mediator);
		mediator.addColleague(user3);

		user1.send("james colleague 데이터 전송");
		user2.send("jiny colleague 데이터 전송");
		user3.send("eric colleague 데이터 전송");
  }
}
```

<br/>

19.4.2 양방향

중재자 패턴은 다른 패턴들과 달리 양방향 통신을 처리함

<br/>

1. concreteColleague에서 send() 메서드를 호출

    ```java
    // 중재자로 데이터를 전송
    user1.send("james colleague 데이터 전송");
    ```

2. 전달 받은 동료 객체는 수신한 메시지를 다시 중개 객체로 전송

    ```java
    public void send(String data) {
        this.mediator.mediate(data, this.name);
    }
    ```

3. 전송 받은 Mediator는 동작을 분석한 후 처리 의무가 있는 다른 동료 객체에게 행위를 요청

    ```java
        // 중재 기능
        public void mediate(String data, String user) {
            System.out.println(user + "의 중재자 생성");

            // 모든 colleagues에게 전달 받은 메시지를 통보
            for (Colleague colleague: colleagues) {
                colleague.message(data);
            }
        }
    ```


Mediator 패턴은 Mediator와 Colleague 사이를 양방향 통신하면서 요청한 행위를 조정

<br/>

## 19.5 관련 패턴

|  | 퍼사드 패턴 | 감시자 패턴 |
| --- | --- | --- |
| 유사성 | 퍼사드 패턴은 더 편리한 인터페이스를 제공하는 추상화 작업. 통신을 위해 높은 레벨의 인터페이스를 만든다는 점에서 중재자 패턴과 비슷하게 볼 수 있음 | 중재자가 Colleague 의 요청을 감지할 경우 감시자Observer 패턴을 같이 활용할 수 있음 |
| 차별성 | 중재자 패턴은 양방향 통신이고 퍼사드는 단방향 통신 |  |

<br/>

## 19.6 정리

객체지향에서는 수많은 객체들이 생성되고 관계를 맺음. 객체들은 강력한 결합 구조와 상호 의존 관계를 가지며, 복잡한 의존 관계를 M:N 관계로 풀어가는 것은 어려움.

중재자 패턴은 여러 객체들의 복잡한 상호 의존성을 느슨한 소결합으로 재조정함. 또한 1:1 관계로 통신하여 보다 간단히 처리합니다. 중재자 패턴은 생성된 객체의 재사용이 용이하도록 만들수도 있지만, **잘못된 중재자 설계는 더 복잡한 객체를 생성할 수 있으므로 주의할 필요**가 있습니다.