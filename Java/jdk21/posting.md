Spring에 DB를 통한 저장(Persistence)을 위해 JPA(Jakarta Persistence API), Hibernate, Spring Data와 같은 기술들을 사용하곤 합니다.

이번 포스팅에서는 각 기술의 개념과 사용에 대해 알아보도록 하겠습니다.

참고로, 이 계층을 Persistence 라고 부르며, 그 의미인 ‘지속되는 저장’을 본 포스팅에서는 단순히 ‘지속성’ 혹은 ‘저장’이라고 지칭하겠습니다.

JPA(Jakarta Persistence API)

- JPA는 이전의 Java Persistence API
- 객체 저장 및 객체/관계 매핑을 관리하는 API를 정의한 스펙
- 객체를 저장하기 위해 수행해야 할 작업을 지정

이해를 위해 단순히 말해보면, 데이터베이스에서 데이터를 관리하기 위해 사용될 기능들을 정의한 인터페이스 모음이라고 생각할 수 있습니다.

“데이터베이스에서 데이터를 관리하려면 이러한 동작(메소드)들을 만들어!”라는 지침이죠.

Hibernate

- JPA 스펙의 가장 인기있는 구현체
- 실제 Persistence 기능을 구현

Spring Data

- Persistence 계층 구현체가 더욱 효율적이게 동작하도록 만듦
- 스프링 프레임워크 원리를 준수하는 아주 간단한 접근 방식 제공
