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

* 만료 시간: 쿠키가 만료되는 특정 기간을 Duration-based Cookie의 경우 로드밸런서에서, Application-based Cookie의 경우에는 Application이 알아서 지정

<small>다 알지 않아도 되고 애플리케이션 기반 쿠키와 기간 기반 쿠키가 있고 특정 이름이 있다는 것만 알면 된다.</small>

### 실습 
Target groups > Action(Edit attributes) > Load balancing algorithm을 보면 Stickiness 섹션에 Stickiness type으로 'Load balancer generated cookie' 와 'Application-based cookie'가 존재하며, Stickiness duration 지정 항목이 보임. 마지막으로 Application-based cookie을 선택할 경우, App cookie name 을 지정하여 쿠키 설정을 할 수 있다.

이후 계속 동일한 인스턴스로만 redirect 되는 것을 확인할 수 있으며, Chrome의 웹 개발자 도구의 Network > Cookies 탭에서 확인할 수 있다.

## Auto Scaling Group

- ASG: 클라우드 환경에서는 (API 호출을 포함하여) 서버를 빠르게 생성하고 제거할 수 있고, 이를 자동화하기 위한 목적으로 생성되는 그룹

- 목적: Scale Out (add EC2 instances) / Scale In (remove EC2 instances) 

- 인스턴스 최소, 최대 개수 보장

- 로드 밸런서와 페어링하는 경우 ASG 이하의 모든 EC2 인스턴스가 로드 밸런서에 연결

  - 인스턴스 비정상(Unhealthy)이면 종료 후 새 EC2 인스턴스 생성

- 무료, 하위 생성 리소스에 대해서만 지불

### ELB + ASG

- ASG Launch Template 설정 

  - AMI + Instance Type, EC2 User Data, EBS Volumes

  - Security Groups, SSH Key Pair, IAM Roles (for EC2), Network + Subnets Information

  - Load Balancer 정보

- ASG의 최소, 최대 크기, 초기 용량(Initial Capacity)

- Scaling Policy

