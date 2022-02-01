복합체 패턴은 객체 간의 계층적 구조화를 통해 객체를 확장하는 패턴. 복합체는 재귀적으로 결합된 계층화된 트리 구조의 객체

<br/>

### 객체를 포함하는 관계

복합 객체는 객체가 또 다른 객체를 포함하는 것을 말한다. 복합적인 객체 관계르르 복합화composition또는 집단화Aggregation라고 한다.

<br/>

### 복합 객체로 구조 확장하기

객체의 복합화는 객체를 더 큰 구조의 객체로 확장하는 방법. 복합 객체는 강력한 결합 구조를 가진 상속과 달리 느슨한 결합을 갖고 있으며, 이러한 결합은 의존체 주입 방식을 사용함

다음은 컴퓨터의 구조를 나타내는 클래스

```java
public class Computer {
	public Monitor monitor;
	public String name = "구성";

  public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}
}
```

Computer 클래스는 다른 객체의 정보를 설정할 수 있는 setter 메서드를 갖고 있음.

모니터 클래스를 다음과 같이 선언함

```java
public class Monitor {
	public String name = "모니터";
}
```

다음에는 앞의 두 객체를 복합 객체로 결합한다. 2개의 객체를 생성한 후 setter 메서드를 이용해 의존성을 주입한다.

```java
class Client {
	public static void main(String[] args) {
		Computer obj = new Computer();
		obj.setMonitor(new Monitor);

		System.out.println(obj.name);
		System.out.println(obj.monitor.name);
	}
}
```

<br/>

#### 수평으로 객체 확장하기

```java
public class Disk {
	public String name = "디스크";
}
public class Memory {
	public String name = "메모리";
}
public class Computer {
    private String name = "구성";

    private Monitor monitor;
    private Memory memory;
    private Disk disk;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public String getName() {
        return name;
    }

    public Monitor getMonitor() {
        return monitor;
    }
}
```

객체를 합성하여 하나의 큰 객체를 생성하는 것은 매우 복잡함

의존하는 객체가 많을수록 설정을 위해 여러개의 setter가 필요함

이처럼 집단화된 객체는 부분-전체 계층art-whole hierarchy 구조가 된다.

또한 객체들은 트리 구조 현태의 계층화 구조를 가짐

<br/>

#### 수직으로 객체 확장하기

수평으로 확장하는 것은 하나의 객체가 여러 객체를 포함하는 것을 말함.

즉 자신이하나씩만 존재함

자식 객체로는 일반 객체뿐만 아니라 복합 객체도 확장 가능.

복합 객체를 가식 객체로 사용할 때는 수직적 확장 구조를 갖습니다. 복합 객체는 수직적 확장을 통해 계층적이고 복잡한 트리 구조를 갖게 됨

컴퓨터는 여러대의 모니터를 가질 수 있기 때문에 Monitor 클래스를 복합 객체로 변경함

```java
public class Monitor {
    public String name = "모니터";
    public ArrayList<Monitor> screen;

    public void addMonitor(Monitor monitor) {
        screen.add(monitor);
    }

    public void show() {
        screen.forEach(s -> System.out.println(s.getName()));
    }

    public String getName() {
        return name;
    }
}
```

복합 객체는 일반 객체와 복합 객체를 구분하지 않고 포함함

이러한 계층적 구조는 마치 트리 구조와 비슷한 모습이다.

<br/>

### 복합체의 구조적 특징

복합 객체는 하나의 객체가 다른 객체를 포함한다.

또한 복합체 패턴은 복합 객체의 특성을 이용한 구조적 패턴

<br/>

### 재귀적 결합

#### 객체 저장

중간 노드인 복합체 패턴 클래스는 복합 객체이다. 여러 개의 리프 객체를 가질 수 있으며 다른 중간 노드의 복합체 패턴을 포함할 수도 있다.

복합체 패턴 객체도 동일한 접속과 처리를 위해 공통된 인터페이스인 추상 클래스를 상속받는다.

<br/><br/>

### 장점

복합체 패턴으로 트리 구조를 구현하면 트리를 추가하거나 이동, 삭제하여 전체적인 구조를 유지하는 데 매우 유용.

복합체 패턴에서 트리 구조의 재귀적인 특징을 잘 응용하는 것이라고 볼 수 있음. 복합체 패턴은 투명성을 이용해 클라이언트의 사용을 단순화할 수 있음. 투명성은 if 문을 사용하지 않고도 Composite 와 leaf를 판단할 수 있음.

단일 객체와 복합 객체(그룹)를 동일하게 여기기 때문에 **묶어서 연산하거나 관리할 때 편하다.**

<br/>

#### 단점

복합체 패턴은 설계의 범용성이 뛰어남. 예를 들어 복합체 패턴은 수평적, 수직적 모든 방향으로 객체를 확장할 수 있음. 하지만 수평적 방향으로만 확장이 가능하도록 Leaf를 제한하는 Composite를 만들기는 어려움.

디버깅 어려움. 재귀호출의 특징 상 **트리의 Depth가 깊어질 수록 라인 단위의 디버깅에 어려움**이 생김.

<br/><br/>

### 관련 패턴

#### 체인 패턴

복합체와 유사한 패턴으로 체인 패턴Chain pattern이 있다. 체인 패턴은 다음 객체를 호출하기 위해 부모 자식 관계를 갖고 있음.

<br/>

#### 명령 패턴

명령 패턴에서 객체를 실행할 때 복합체 패턴을 응용함

<br/>

#### 방문자 패턴

방문자 패턴에서는 복합체 패턴을 응용하여 방문자 객체를 생성함. 방문자 패턴은 복합체로 생성된 객체를 순회하면서 하위 객체를 처리함.

<br/>

#### 장식자 패턴

복합체 패턴은 장식자 패턴과 같이 응용함. 장식자 패턴도 새로운 기능을 확장하는 과정에서 재귀적인 결합이 이르어지는데 이는 복합테의 재귀적 모습과 비슷함

차이점은 객체의 확장 유형이 약간 다르다는 것. 장식자는 상하 구조로 확장되지만 복합체는 상하, 좌우 형태로 확장됨

<br/>

### 정리

복합체 패턴은 분할 디자인 패턴 partitioning design pattern의 하나임. 복합체 패턴을 이용하면 객체의 상위, 하위 체계를 파악할 수 있고 일대일, 다대일을 처리하는 데도 매우 유용함. 하나의 객체를 호출하면 서브로 갖고 있는 자식의 객체 메서드를 호출할 수 있음.