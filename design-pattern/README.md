## GoF 디자인 패턴 도장깨기 👊🏻

<br/>

[쉽게 배워 바로 써먹는 디자인 패턴](https://www.hanbit.co.kr/store/books/look.php?p_code=B9696096335)

<br/>

### **디자인 패턴**

많은 사람이 겪은 문제점과 해결 방법을 정리해둔 것. 디자인 패턴은 객체지향 문제를 해결하기 위한 일련의 코드 스타일. 객체 지향 개발에서 디자인 패턴이 해결하는 주요 문제는 객체 간 관계와 소통 처리 부분.

<br/>

### **패턴**

일정한 형태나 양식 및 유형으로 반복되는 것. 

패턴의 요소 : 이름(pattern name), 문제(problem), 해법(solution), 결과(consequence)


<br/>

### 설계 원칙 SOLID

✔️ **SRP, Single Responsibility Principle. 단일 책임 원칙** 

하나의 클래스는 하나의 책임만을 가져야 한다

<br/>


✔️ **OCP, Open/Closed Principle. 개방 폐쇄 원칙**

소프웨어 요소는 확장에는 열려있으나, 변경에는 닫혀 있어야 한다

<br/>

✔️ **LSP, Liskov Substitution Principle. 리스코브 치환 원칙**

프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다

<br/>

✔️ **ISP, Interface Segregation Principle. 인터페이스 분리의 원칙**

특정 클라이언트를 위한 인터페이스 여러개가 범용 인터페이스 하나보다 낫다

<br/>

✔️ **DIP, Dependency Inversion Principle. 의존 관계 역전의 원칙**

추상화에 의존해야지 구체화에 의존하면 안된다

*cf. 의존성 주입*

<br/>

각각의 원칙을 코드의 목적에 맞게 적절히 사용할줄 알아야 함.

<br/><br/>

### GoF

Gang of Four - Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides, Grady Booch. 처음으로 소프트웨어 공학에서 사용되는 패턴을 정리한 사람들의 별칭


<br/><br/>


##Pattern



|        | Creational Pattern                                           | Structural Pattern                                           | Behavioral Pattern                                           |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Class  | Factory Method                                               | Adapter                                                      | Interpreter<br />Template Method                             |
| Object | Singleton<br />Builder <br />Abstract <br />Factory <br />Prototype | Bridge<br />Composite<br />Decorator <br />Facade <br />Flyweight <br />Proxy | Chain of Responsibility <br />Command <br />Iterator <br />Mediator <br />Observer <br />State <br />Strategy <br />Visitor |

<br/><br/>

## Creational Pattern

<br/>

생성 패턴은 선언된 클래스로 객체를 생성하는 방법에 대한 문제점과 해결책을 제안

생성 패턴에서는 객체 생성을 위임하여 별개의 클래스로 분리하고, 객체 생성 과정을 담당할 별도의 클래스를 선언. 프로그램 내에서 필요한 객체를 생성하고 관리하는 캡슐화된 클래스를 선언하는 것.

<br/>

**Factory**

객체의 생성 동작을 별도 클래스로 분리하여 처리하거나 별도의 메서드를 호출하여 객체의 생성 동작을 처리

<br/>

**Singleton**

선언된 클래스로 복수의 객체를 생성할 수 없도록 제한. 제한된 단일 객체는 공유와 충돌을 방지

<br/>

**Factory Method**

팩토리 확장 - 팩토리 패턴에 추상화를 결합

<br/>

**Abstract Factory**

팩토리 메서드를 확장 - 팩토리를 팩토리의 군(family)으로 변경

<br/>

**Builder**

추상 팩토리를 확장한 패턴. 복잡한 구조의 복합 객체를 빌더 패턴으로 생성. 복합 객체를 생성하기 위한 단계를 정의하고 각 단계별 수행 동작을 변경할 수 있음.

<br/>

**Prototype**

새로운 객체를 생성하지 않고 기존의 객체를 복제. 복잡한 구조의 객체를 새로 생성하는 것은 많은 자원을 소모. 객체를 새로 생성하지 않고 자원을 절약하는 패턴

<br/><br/>

## Structual Pattern

<br/>

**[Adapter](https://github.com/gngsn/TIL/blob/master/design-pattern/Adapter.md)**

인터페이스를 추상화하여 서로 다른 인터페이스를 통일화. 상속을 통한 어댑터와 합성을 통한 어댑터로 구분.

<br/>

**Bridge**

개념과 추상을 구부하여 처리. 또한 객체의 독립으로 확장과 변형이 가능

<br/>

**Composite**

객체의 구조를 이용해 객체를 확장. 트리 구조의 특징

<br/>

**Decorator**

객체에 기능을 동적으로 추가. 확장 시 객체에 새로운 책임을 부과할 수 있음. 재귀적 합성 방법을 응용하여 객체를 확장

<br/>

**Facade**

복잡한 객체의 구조와 접근을 간단하게 표현하는 방법

<br/>

**Flyweight**

객체를 공유. 공유를 통해 자원의 효율성과 공유된 객체의 일관성 보장

<br/>

**Proxy**

객체의 겁근을 제한. 액세스할 때 추가적인 책임을 부여하는 역할을 수행