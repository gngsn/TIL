# AWS Auto Scaling

자동으로 EC2 의 숫자를 늘렸다 줄였다하는 것.

Auto scaling이란 정책에 따라서 시스템을 자동으로 늘리고 줄여주는 서비스다. 이를테면 이런식으로 동작한다. 갑자기 트래픽이 폭주하면 서비스에 참여하고 있던 서버들의 CPU 사용률이 점점 높아진다. 그러다 CPU 점유율이 80%를 넘게되면 미리 준비된 이미지(AMIs)를 이용해서 인스턴스를 생성하고, ELB에 연결해서 트래픽을 새로 생성한 EC2 인스턴스에 분산한다. 트래픽이 줄어들면서 CPU 사용률이 20% 아래로 떨어지면 EC2 인스턴스가 순차적으로 제거된다. 비용을 절감할 수 있다.

기존의 문제점 -> 해결 가상화 기술 (Auto Scaling)

트래픽에 비해 인프라자원이 넘쳐나거나 부족한 경우가 큼



## Auto Scaling

EC2 인스턴스의 규모를 자동으로 확대/축소

인스턴스의 규모를 변화시키는 다양한 조건이 있음

처리량의 증가에 빠르게 대응할 수 있음

CLI를 통해서만 제어 가능

미리 만들어진 이미지(AMI)를 이용해서 인스턴스를 자동으로 생성한다.



## Auto Scaling의 타입

부하에 따라 자동으로 규모 변경 ( CPU 점유율에 따른 인스턴스 규모 제어)

현재의 규모 유지 (한 대의 인스턴스에 장애가 생겨도 폐기 후 바로 다른 인스턴스 켜서 규모 유지)

시간에 따라 변경 (예상 트래픽 점유율에 따른 인스턴스 개수)



## Auto Scaling의 절차

1. launch configuration 설정 (어떤 서비스를 생성 - 인스턴스 속성)
   1. AMI (어떤 이미지를 기반?)
   2. Instance type (그 인스턴스가 마이크로 인스턴스인 스몰 인스턴스인지)
2. Auto Scaling Group 생성
   1. ELB (elastic load balancing에 자동으로 추가, optional.)
   2. 최소/최대 인스턴스의 수량 (트래픽이 엄청 늘었을 때 인스턴스를 엄청 만들면 돈 개깨짐)
   3. 가용성 존 ( 가장 중요)
3. 정책 (policy) 생성
   1. 인스턴스의 추가/제거의 방법과 수량 (몇 개가 어떻게 추가되고 얼마나 없앨 것 인가)
   2. cooldown (auto scaling이 다시 시작할 때 걸리는 시간)
4. Cloud Watch에서 Alarm을 생성하고 정책과 연결
   1. 높은 트래픽이 오래 유지되면 알람 뜨는 거



## ELB ( Elastic Load Balancing )

#### Application Load Balancer VS Network Load Balancer

Elastic Load Balancing 는 세 가지로 나눌 수 있음

- Application Load Balancer (HTTP, HTTPS) : OSI 7 계층

- Network Load Balancer (TCP, TLS) : OSI 4 계층

- Classic Load Balancer (HTTP, HTTPS, TCP)



> 급격한 트래픽 상승에 어떻게 확장을 시킬까?
> "여러대의 컴퓨터를 두면 되겠지."

단일 진입점에서 여러대의 컴퓨터에게 트래픽을 분산시켜주는 것을 로드 밸런서라고 합니다.

특징 (Scale Out)

- 트래픽 분산
- 자동 확장 
- 인스턴스의 상태를 자동 감지해서 오류가 있는 시스템은 배제 (하나의 컴퓨터에 장애가 생기면 장애가 해결될 때 까지 자동으로 배제해줌.)
- 사용자 세션을 특정 인스턴스에 고정
- SSL 암호화 지원
- IPv4, IPv6 지원
- CloudWatch를 통해 모니터링할 수 있음



## Application Load Balancer (OSI 7 계층)

![게시물 이미지](https://miro.medium.com/max/2432/1*Jt0zz4M14_D4Iall1IcXuA.png)

네트워크 통신은 애플리케이션 계층까지 다시 이동해야하며 Application load balancer는 HTTP 요청을 읽어 전달되어야하는 위치를 결정합니다. 



### SSL / TLS

클라이언트와 웹 서버 간의 네트워크 트래픽에 대해 SSL/TLS 암호화를 구현하는 몇 가지 방법이 있습니다.

예를 들어 레벨 7 애플리케이션로드 밸런서의 일반적으로 사용되는 한 가지 용도에 SSL / TLS 종료가 있습니다.

![Image for post](https://miro.medium.com/max/2392/1*wZOAKk5_S1F2gORHhXYKRw.png)

이 접근 방식은 네트워크 트래픽을로드 밸런서로 암호화하고 해독하는 무거운 작업을 덜어줌으로써 웹 서버의 리소스 사용률을 낮춥니다.

로드 밸런서 뒤의 웹 서버는  요청이 이미 로드 밸런서에 의해 해독되었으므로 일반 텍스트 HTTP 요청만 처리하면 됩니다.

![게시물 이미지](https://miro.medium.com/max/2474/1*Ai2jIdEsDmBlPugoHYPXtw.png)

하지만, 일부 응용 프로그램은 종단 간의 암호화가 필요할 수도 있는데요, 

애플리케이션 로드 밸런서는 레벨 7에서 작동하기 때문에 헤더를 검사하기 위해 HTTP 요청을 해독하고 요청을 다시 암호화하여 클라이언트로 전송해야 한다.
그러면 당신의 웹 서버는 그것을 읽기 위해 그것을 다시 해독한다. 이렇게 하면 SSL/TLS 오버헤드가 두 배로 증가하여 대기 시간이 더 길어질 뿐만 아니라 개인 키가 웹 서버 레벨뿐만 아니라 로드 밸런서 레벨에도 저장되어야 함을 의미한다.

이럴 때에는 4계층 LoadBalancer를 사용하는 게 낫다.

![게시물 이미지](https://miro.medium.com/max/2564/1*QN0bUHFUGDmvGbLiUo_iZw.png)



## Network Load Balancer (OSI 4 계층)

![게시물 이미지](https://miro.medium.com/max/2426/1*QHwnbA9mWakm1SS9atzk9g.png)

네트워크 통신은 스택을 통해 전송 계층으로 이동하고 network load balancer는 TCP 패킷 정보를 읽어 통신을 올바른 위치로 전달하지만 실제로 해당 패키에 포함된 정보를 읽지는 않습니다.


