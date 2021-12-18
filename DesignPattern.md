### **디자인 패턴**

많은 사람이 겪은 문제점과 해결 방법을 정리해둔 것. 디자인 패턴은 객체지향 문제를 해결하기 위한 일련의 코드 스타일. 객체 지향 개발에서 디자인 패턴이 해결하는 주요 문제는 객체 간 관계와 소통 처리 부분.

### **패턴**

일정한 형태나 양식 및 유형으로 반복되는 것. 

패턴의 요소 : 이름(pattern name), 문제(problem), 해법(solution), 결과(consequence)

### 설계 원칙 SOLID

✔️ SRP, Single Responsibility Principle. 단일 책임 원칙

✔️ OCP, Open/Closed Principle. 개방 폐쇄 원칙

✔️ LSP, Liskov Substitution Principle. 리스코브 치환 원칙

✔️ ISP, Interface Segregation Principle. 인터페이스 분리의 원칙

✔️ DIP, Dependency Inversion Principle. 의존 관계 역전의 원칙

각각의 원칙을 코드의 목적에 맞게 적절히 사용할줄 알아야 함.

### GoF

Gang of Four - Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides, Grady Booch. 처음으로 소프트웨어 공학에서 사용되는 패턴을 정리한 사람들의 별칭

Pattern

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/188c3f4c-cf92-4ba2-a9c4-296fd9250021/Untitled.png)

|  | Creational Pattern | Structural Pattern | Behavioral Pattern |
| --- | --- | --- | --- |
| Class | Factory |  |  |
| Object | Singleton
Factory Method
Builder
Abstract Factory
Prototype |  |  |

## Creational Pattern

생성 패턴은 선언된 클래스로 객체를 생성하는 방법에 대한 문제점과 해결책을 제안

생성 패턴에서는 객체 생성을 위임하여 별개의 클래스로 분리하고, 객체 생성 과정을 담당할 별도의 클래스를 선언. 프로그램 내에서 필요한 객체를 생성하고 관리하는 캡슐화된 클래스를 선언하는 것.

![IMG_453879F8C0CC-1.jpeg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c17bf1a0-cbd4-42e6-abc5-900f0f36c269/IMG_453879F8C0CC-1.jpeg)

### Factory

*객체의 생성 동작을 별도 클래스로 분리하여 처리하거나 별도의 메서드를 호출하여 객체의 생성 동작을 처리.*

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/baeac81f-941b-4d09-bf8c-c7d784446f25/Untitled.png)

프로그램 내에서 객체 생성이 필요한 경우 분리 설계된 팩토리 객체에 생성을 위임. 즉, 실제 코드가 new 키워드를 직접 사용하지 않고 팩토리의 객체를 호출하는 것으로 대체 → **느슨한 결합 관계로 변경**

객체를 생성하고 캡슐화하여 위임하는 것 → 공장

**단순 팩토리 simple factory**

팩토리 패턴의 특징과 처리 로직을 간략하게 작성. 별개의 Factory 객체를 생성하지 않고 자신의 객체에 필요한 객체를 생성하는 전용 메서드를 추가

### Singleton

선언된 클래스로 복수릐 객체를 생성할 수 없도록 제한. 제한된 단일 객체는 공유와 충돌을 방지.

### Factory Method

팩토리 확장. 팩토리 패턴에 추상화를 결합. 객체의 생성과 사용을 분리. 선언된 클래스의 객체를 직접 코드로 생성하지 않고 별도로 준비한 추상 메서드에 생성을 위임.

**Abstract Factory**

팩토리 메서드를 확장. 팩토리 메서드보다 좀 더 큰 그룹 단위 객체를 생성 관리. 팩토리에 인터페이스를 적용하여 객체 생성. 팩토리를 팩토리의 군(family)으로 변경

**Builder**

추상 팩토리를 확장한 패턴. 복잡한 구조의 복합 객체를 빌더 패턴으로 생성. 복합 객체를 생성하기 위한 단계를 정의하고 각 단계별 수행 동작을 변경할 수 있음.

**Prototype**

새로운 객체를 생성하지 않고 기존의 객체를 복제. 복잡한 구조의 객체를 새로 생성하는 것은 많은 자원을 소모. 객체를 새로 생성하지 않고 자원을 절약하는 패턴