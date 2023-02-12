Sticky Sessions (Session Affinity)

- stickiness: the same client is always redirected to the same instance.

  -> 로드 밸런서에서 2회 이상의 요청을 보낸 Client에게 동일한 인스턴스를 갖게 하는 것

- CLB와 ALB에서 설정할 수 있음

- how it works? "Cookie", Client에서 로드 밸런서로 요청의 일부로 전송

  - has stickiness and expiration date

- 사례: 사용자의 세션 정보 유지

- Caution: EC2 인스턴스 부하에 불균형을 초래할 수 있음


## Sticky Session: 쿠키 이름

### 1. Application-based Cookie

  - 애플리케이션 기반 쿠키

**1.1 Custom Cookie**

  - Target의, Application 자체에서, 생성되는 Custom 쿠키

  - Custom 속성 어느 것이든 추가할 수 있음

  - 쿠키 이름은 각 타겟 그룹 별 개별적으로 이름을 지정해야만 함

  - AWSALB, AWSLBAPP, AWSAKBTG과 같이 ELB에서 이미 사용중인 이름을 사용하면 안됨

**1.2 Application Cookie**

  - Load Balancer에서 생성됨

  - 쿠키 이름은 AWSALBAPP

### 2. Duration-based Cookie

  - 로드 밸런서에서 생성되는 쿠키

  - 쿠키 이름은 ALB에서는 AWSALB, CLB에서는 AWSELB를 사용

  - 만료 시간은 특정 기간 동안을 로드밸런서에서 알아서 지정