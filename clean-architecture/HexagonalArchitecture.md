# Hexagonal Architecture 정리

= Ports and Adapters Architecture

인터페이스나 기반 요소(infrastructure)의 변경에 영향을 받지 않는 핵심 코드를 만들고 이를 견고하게 관리하는 것이 목표

<br/>

### 모놀리식 시스템과 분산 시스템

_모놀리식 > 서비스 지향 아키텍처 (SOA, Service Oriented Architecture) > 마이크로서비스 아키텍처 (MSA, Microservice architecture)_

- **기술 부채 (technical debt)**: 소프트웨어 코드 내에 존재하는 불필요한 복잡성이 얼마나 존재하는지를 설명하는 데 사용 (워드 커닝햄 Ward Cunningham)
- **크러프트 (cruft)**: 기술 부채의 불필요한 복잡성. 코드에 남아있는 중복되고 방해되는 모든 것을 의미하는 전문 용어. 현재 코드와 이상적인 코드와의 차이.

- **커다란 진흙 덩어리** (big ball of mud): 인지할 수 있는 아키텍처가 없는 소프트웨어 시스템. 다수의 요소가 강하게 결합되어 변경이나 유지보수가 어려운 엉망인 상태의 코드

헥사고날 아키텍처는 이기종 기술을 사용하는 상황에 도움이 될 수 있다.

<br/>

## 헥사고날 아키텍처 이해

> 여러분의 애플리케이션을 UI나 데이터베이스 없이 동작하도록 만드십시오. 그러면 애플리케이션에 대해 자동화된 회귀 테스트를 실행하 있고, 데이터베이스를 사용할 수 없을 때도 동작합니다. 그리고 어떤 사용자의 개입 없이도 애플리케이션을 함께 연결할 수 있습니다.
>
> - 알리스테어 코크번 (Alistair Cockburn)

- 도메인 헥사곤에서는 소프트웨어가 해결하기를 원하는 핵심 문제를 설명하는 요소들을 결합
- 도메인 헥사곤에서 활용되는 주된 요소는 **엔티티(entity)**와 **값 객체(value object)**

<br/>

### 도메인 헥사곤

- 엔티티
  - 표현력 있는 코드를 작성하는 데 도움을 줌
  - 객체를 특징짓는 것은 연속성과 정체성에 대한 감각

<br/>

### 애플리케이션 헥사곤

<table>
<tr>
<th>유스케이스 (Use Case)</th><th>입력 포트 (Input Port)</th><th>출력 포트 (Output Port)</th>
</tr>
<tr>
<td>

- 도메인 제약사항을 위해 시스템의 동작을 소프트웨어 영역 내에 존재하는 애플리케이션 특화 오퍼레이션
- 엔티티 및 다른 유스케이스와 직접 상호작용하고 그것들을 유연한 컴포넌트로 만들 수 있음

```java
public interface RouterViewUseCase {
    List<Router> getRouters(Predicate<Router> filter);
}
```

- Predicate: 유스케이스를 구현할 때 라우터 리스트를 필터링하는 데 사용
</td>
<td>

- 유스케이스가 소프트웨어가 하는 일을 설명하는 인터페이스라면 여전히 유스케이스 인터페이스를 구현해야 함
- 애플리케이션 수준에서 유스케이스에 직접 연결되는 컴포넌트

```java
public class RouterViewInputPort implements RouterViewUseCase {

    private RouterViewOutputPort routerListOutputPort;

    public RouterViewInputPort(RouterViewOutputPort routerViewOutputPort) {
        this.routerListOutputPort = routerViewOutputPort;
    }


    @Override
    public List<Router> getRouters(Predicate<Router> filter) {
        var routers = routerListOutputPort.fetchRouters();
        return Router.retrieveRouter(routers, filter);
    }
}
```

</td>
<td>

유스케이스가 목표를 달설하기 위해 외부 리소스에서 데이터를 가져와야 할 때의 역할

```java
public interface RouterViewOutputPort {
    List<Router> fetchRouters();
}
```

</td></tr></table>
<br/>

## 프레임워크 헥사곤

소프트웨어와 통신할 수 있는 기술을 결정: 통신의 두 가지 방법

- **드라이버 관점**에서는 **입력 어댑터 (Input Adapter)** 사용
- **드리븐 관점**에서는 **출력 어댑터 (Output Adapter)** 사용

<br/>

### 드라이빙 오퍼레이션과 입력 어댑터

**드라이빙 오퍼레이션 (Driving operation)**: 소프트웨어에 동작을 요청

ex. 명령행 클라이언트를 갖는 사용자, 사용자를 대신하는 프런트엔드 애플리케이션

API (Application Programming Interface)

- 외부 엔티티가 시스템과 상호작용하고, 외부 엔티티의 요청을 개발자의 도메인 애플리케이션으로 변환하는 방법을 정의
- 입력 어댑터 상단에 구축

드라이빙 - 외부 엔티티들이 시스템의 동작을 유도(driving)하기 때문. 입력 어댑터는 아래와 같이 매핑될 수 있음

<table>
<tr><th>드라이버</th><th>입력 어댑터</th></tr>
<tr><td>CLI</td><td>STDIN</td></tr>
<tr><td>JS</td><td>REST</td></tr>
<tr><td>JAVA</td><td>GRPC</td></tr>
<tr><td>ASPX</td><td>SOAP</td></tr>
</table>

<br/>

## 엔티티를 활용한 문제 영역 모델링

- 지식 크런칭 (knowledge crunching): 지식 고속 처리를 의미하며, 개발자와 도메인 전문가로 이루어진 팀의 협업 활동으로 주로 개발자가 주도.
- 지식 크런칭된 지식은 **보편 언어 (Ubiquitous Language)** 를 통해 통합됨
- **보편 언어 (Ubiquitous Language)**: 프로젝트와 관련된 모든 사람 사이에서 공영어 (lingua franca) 역할을 하고, 문서, 일상 대화, 코드에도 존재.



문제 영역 모델링의 핵심은 엔티티를 만드는 것.

<br/>

## 아키텍처적으로 표현력 있는 패키지 구조

```
buckpal
└── account     ← Account와 관련된 유스케이스 구현
    ├── adapter     ← 애플리케이션 계층의 인커밍 포트를 호출하는 인커밍 어댑터와 애플리케이션 계층의 아웃고잉 포트에 대한 구현을 제공하는 아웃고잉 어댑터 포함
    │   ├── in
    │   │   ├── web
    │   │   │   └── AccountController
    │   └── out
    │       └── persistence
    │           ├── AccountPersistenceAdapter
    │           └── SpringDataAccountRepository
    ├── domain     ← 도메인 모델
    │   ├── Account
    │   └── Activity
    └── application     ← 도메인 모델을 둘러싼 서비스 계층 포함
        ├── SendMoneyService     ← 인커밍 포트 인터페이스인 SendMoneyUseCase 구현하고, 아웃고잉 포트 인터페이스이자 영속성 어댑터에 의해 구현된 LoadAccountPort와 UpdateaccountStatePort를 사용 
        └── port
            ├── in
            │   └── SendMoneyUseCase
            └── out
                ├── LoadAccountPort
                └── UpdateaccountStatePort
```

- 아키텍처적으로 표현력 있는 패키지 구조에서는 각 아키텍처 요소들에 정해진 위치가 있음
- 가령, Third Party API에 대한 클라이언트를 변경하는 작업의 경우 `adapter/out/<adapter-name>` 패키지에서 곧바로 찾을 수 있음

👉🏻 이 패키지 구조는 아키텍처-코드 갭 (architecture-code gap) 혹은 모델-코드 갭(model-code gap)을 효과적으로 다룰 수 있는 강력한 요소.

<small>모델-코드 갭(model-code gap): ≪Just Enough Software Architecture≫, Geroge Fairbanks, p.168</small>




<br/>








