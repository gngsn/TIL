# Structure Pattern

<br/><br/>

## Web System

기업에서 자주 이용하는 시스템을 예로들어 AWS 서비스를 어떻게 조합하여 사용하여 설계하면 좋을지 시나리오를 통해 알아보자

<br/>

### Pattern 1. Event Site

*목표: 간단한 이벤트 사이트에 사용할 공개 웹 서버를 최소한의 요소로 구성하여 만드는 방법*

<br/>

> #### Event Site Condition
>
>
> - 1개월 한정
> - 사용자는 인터넷으로 접속하는 개인 사용자
> - 접속자 수는 많지 않아 고사양은 필요하지 않음
> - 웹서버로 LAMP(Linux, Apache, MySql, PHP) 환경 사용
> - 비용 우선, 다중화나 백업 고려 안함

<br/>

```
#### 핵심 설계 사항

1. 리전 선택
이벤트 사이트를 구동시킬 최적의 리전을 선택

2. EC2 인스턴스 설정
이벤트 사이트의 웹 서버에 적합한 EC2 인스턴스를 설정

3. 도메인을 통한 접속
고정 IP 주소와 접속한다. (CNAME 설정을 통해 고정 IP 주소가 아닌 도메일 이름을 이용하여 도메인에 접속할 수도 있다.)

4. 네트워크 구성
인터넷 접속을 위해 간단한 네트워크 구성을 설정한다.

5. 웹 서버로서의 OS 환경 설정
아마존 리눅스에 LAMP 환경을 설정한다.
```

<br/><br/>

### AWS 아이콘 조합으로 아키텍처 표현

AWS 아이콘 제공 사이트 : https://aws.amazon.com/ko/architecture/icons/

<br/>

#### 서버를 구성하는 가장 기본적인 서비스

: EC2(Amazon Elastic Compute Cloud, 아마존 일래스틱 컴퓨트 클라우드) + 가상 스토리지 볼륨 - EBS (Amazon Elastic Block Store, 아마존 일래스틱 블록 스토어)
네트워크에 필요한 기본적이 서비스는 표준 서비스에 포함되어 있다. (e.g. VPC의 내부에 **가상 라우터**를 설치할 수 있음. 인터넷과 접속하는 데 **인터넷 게이트웨이**도 이용할 수 있음)

<br/>

##### ✔️ 리전에 따른 응답 속도와 비용 차이

구축 시 가장 리전 선택이 우선.

<br/>

**응답 속도**

응답 속도는 AWS 데이터 센터와 이벤트 사이트에 접속하는 사용자가 **지리적으로 가까울수록 빠르며 멀수록 느리다**.

즉, 국내에 거주하는 사용자를 대상으로 하는 이벤트 사이트의 응답 속도를 빠르게 하고 싶다면 서울리전 선택

<br/>

**서비스 요금**

서비스 요금은 리전에 따라 다르다. 

가상서버인 EC2 타입 중 하나인 t2.medium 인스턴스는 1시간 당 
서울 리전이 온디맨드는 0.0576달러 1년 표준 예약 당 0.0344달러,
미국 동부 (버지니아) 온디맨드는 0.0464달러 리전이 0.0287달러이다. (2022.06.01 기준)

(참고: https://calculator.aws/#/createCalculator/EC2)

<br/>

인터넷으로의 데이터 전송 요금도 미국 동부 리전이 더 싸다. 응답속도보다도 비용이 중요하다면, 미국 동부 리전이 유리하다.

리전을 선택했다면 VPC(Virtual Private Cloud, 가상 프라이빗 클라우드)와 서브넥으로 구성하는 가상 네트워크를 작성해야 한다.

VPC는 논리적으로 격리된 사용자 전용 네트워크 구역이다. 복수의 가용 영역 (Availability Zones, 이하 AZ)에 걸친 형태로 VPC 하나를 작성할 수 있다.
단, 복수의 리전에 걸쳐 작성할 수는 없다. 서브넷은 VPC를 논리적으로 분리한 서브 네트워크로 AWS 환경 내의 네트워크 최소 단위이다. 

서브넷은 단일 AZ 안에 작성한다. 

VPC와 AZ, 서브넷의 관계는 아래와 같으며, 서버를 비롯한 각종 AWS 서비스는 서브넷에 배치된다.

<br/>

<img src="./img/archi1-vpc-az-subnet.jpg" width="40%" />

서브넷을 나누는 방법은 온프레미스 환경과 비슷하다.

논리적인 네트워크를 설계해서 AWS의 서브넷 구성에 대입한다.

예를 들어 인터넷으로 HTTP 수신이 가능한 웹 서버와, 웹 서버로부터 데이터베이스 접속만 허가하는 데이터베이스 서버는 필터링 정책이 다르기 때문에 서브넷을 분리해야한다.

다중화하는 경우에는 AWS 특유의 기법을 사용한다. 

별도의 AZ에 서브넷을 포함하여 여러 웹 서버를 분산 배치해야 한다. (다중화)

<br/>

##### ✔️ EC2 인스턴스 작성

인스턴스는 AWS 상의 가상 서버. 

```
 +-- AWS ----------------------+
 |   +---- VPC -------------+  |
 |   |  +--------subnet--+  |  |
 |   |  |     EC2        |  |  |
 |   |  +----------------+  |  |
 |   +----------------------+  |
 +-----------------------------+
```

AWS 환경에서 인스턴스를 하나만 작성하면 위와 같은 형식을 보인다. 

EC2 인스턴스는 웹 설정 화면에서 미리 정의된 옵션을 선택하여 설정할 수 있다.

우선 AMI (Amazon Machine Image, 아마존 머신 이미지)를 선택하면 되는데, AMI는 즉시 사용이 가능한 상태의 OS 및 패키지의 조합이다.

이용 목적에 가까운 AMI를 선택하여 필요에 따라 최적화한다.

<br/>

AMI의 가상화 타입으로 완전가상화인 HVM과 반가상화인 PV가 있다.
일반적으로 HVM을 선택하는데, AWS는 PV의 지원을 축소하면서 HVM으로 전환을 진행하고 있어서 HVM의 성능이 더 좋다.


인스턴스는 서버 규모에 해당하며, CPU, 메모리, 스토리지, 네트워크 성능의 조합을 할 수 있다. 메모리만을 늘린다든지 하는 세세한 사용자 정의는 불가능한다.
그래서 목적에 가장 가까운 인스턴스 유형을 선택해야 한다.

인스턴스 유형 확인: https://aws.amazon.com/ko/ec2/instance-types/

<br/><br/>

##### ✔️ 네트워크 및 셧다운 동작 설정 주의사항

**고정 퍼블릭 IP**

퍼블릭 IP 주소를 자동 할당하는 퍼블릭 IP 자동 할당 (Auto-assign Public IP) 기능을 비활성화(Disable)한다.
활성화(Enable)하면 동적 퍼블릭 IP 주소가 부여되어 인터넷에서 접속이 가능해진다.
이 설정으로 부여된 퍼블릭 IP 주소가 부여되어 인터넷에서 접속이 간으해진다.
단, 이 설정으로 부여된 퍼블릭 IP 주소나 퍼블릭 DNS는 EC2 인스턴스가 다시 시작할 때마다 자동으로 변경된다. 
따라서 재시작할 때마다 IP 주소 또는 DNS를 다시 지정해주어야만 한다.

<br/>

**셧다운**

EC2 인스턴스를 정지하는 경우의 동작 설정에도 주의가 필요.

- 정지(Stop): 셧다운 시 OS가 정지되어 OS 이미지가 보존되고 재시작 시 같은 상태로 시작됨
- 종료(Terminate): OS가 정지되며 EC2 인스턴스가 삭제됨. 

<br/>

**셧다운 종료 보호**

EC2 인스턴스를 유지해야하는 시스템에서는 'Enable termination protection(종료 보호 활성화)'를 체크

-> 관리 화면에서 조작 실수로 인스턴스가 삭제되는 것을 막을 수 있다.

<br/><br/>

##### ✔️ 보안 그룹 설정으로 통신 필터링

EC2 인스턴스 설정의 마지막 단계는 **보안 그룹 설정**

보안 그룹은 OS 레벨에서 네트워크 통신 필터링 룰을 정하는 것으로 허가할 프로토콜을 설정한다.

보안그룹 Source의 초기 설정값이 Anywhere로 되어 있는데, 이는 모든 IP 주소로부터의 SSH 접속을 허가한다는 의미이다.

퍼블릭 IP 주소가 여러개인 경우엔 주소 범위를 CIDR 표기법 (클래스 없는 도메인 간 라우팅 방법)으로 적는다.

<br/>

| Type | Protocol | Port Range | Source |
|---|---|---|---|
| SSH | TCP | 22 | 집 또는 회사의 퍼블릭 IP 주소 | 
| HTTP | TCP | 80 | 0.0.0.0/0 | 
| HTTPS | TCP | 443 | 0.0.0.0/0 |

<br/>

위와 같은 설정이 끝나면 인스턴스 생성 끝!

<br/><br/>

##### ✔️ 고정 IP와 호스트명으로 접속


**AWS EIP (Elastic IP)**

인터넷을 통해 접속하려면 고정 퍼블릭 IP 주소와 FQDN(호스트명)을 부여해야한다.

**AWS Route53**

DNS 서비스. DNS를 설정하면 FDQN으로 접속이 가능


<br/><br/>

##### ✔️ VPC 설정으로 인터넷 접속 설정

AWS에서는 VPC 이용 필수

VPC는 AWS 데이터 센터 내부에 마련된 가상의 폐쇄 네트워크, 외부와 통신할 수 있도록 설정해야 한다.

<br/>

1. DNS 관련 설정

DNS resolution: VPC 내에서 DNS 확인을 활성화할지 여부를 관리
DNS hostname: DNS 이름을 부여할지 여부를 관리, FQDN에 접속하는데 필요

```
* FQDN(Fully Qualified Domain Name)? 

도메인 전체 이름을 표기하는 방식을 의미한다. FQDN은 명확한 도메인 표기법을 칭한다.
예로 소프트웨어 설치 중 도메인명을 요구하면, YAHOO.COM. 을 입력할지, WWW.YAHOO.COM. 을 입력할지 모호하다.
그래서 이러한 모호성을 피하기 위해 FQDN이란 단어를 사용하며, 이는 Namespace 계층상에서 최종 호스트명을 포함하는 도메인명을 뜻한다.

www(호스트명), yahoo.com.(도메인명), www.yahoo.com.(FQDN)

원칙적으로 도메인의 표기는 네임스페이스상의 경로를 명확히 하기 위해 끝에 도트(‘.’ 루트 도메인)를 포함하여야 하지만, 보통 도트를 생략하고 사용한다.
```

<br/><br/>

##### ✔️ 서비스 종료

사이트를 닫는다면 알아야할 일이 있다.
서비스를 그대로 두면 계속해서 비용이 발생하기 때문에, 비용이 발생하는 서비스를 중지해야한다.

EC2 인스턴스는 중지해도 유지하는 용량만큼 청구된다. 재사용 계획이 없다면 데이터를 백업해놓은 후에 매니지먼트 콘솔의 EC2 인스턴스 설정 화면에서 Terminate를 클릭해서 삭제해야한다.
Enable termination protection(종료 보호 활성화)를 설정해두었다면 무효화시킨 후 삭제해야한다.

EBS도 마찬가지로 EC2 인스턴스에서 분리(Detach)하여 유지하더라도 비용이 청구되니, 필요없다면 삭제해야한다.

EIP는 조금 다르게, EC2에 연결되어있으면 과금이 되지 않지만 연결되어 있지않으면 과금된다. 따라서 Release address로 할당을 해제해야한다.

라우트 53은 DNS 존 단위로 월정액 과금된다. 

VPC는 연결 및 데이터 전송에 비용이 발생하지만, VPC 유지 자체 비용은 청구되지 않기 때문에 제거할 필요가 없다.


<br/><br/>

##### 관리형 서비스

AWS를 EC2로 대표되는 IaaS(Infrastructure as a Service)를 제공하는 클라우드 사업자로 인식하는데,
실제로는 OS와 미들웨어까지 관리하는 PaaS(Platform as a Service)와 
소프트웨어를 관리하는 SaaS(Software as a Service)도 제공하며 그 숫자가 점점 늘고 있다. 

이러한 서비스를 관리형 서비스라고 부른다.

주요 관리형 서비스로는 스토리지 서비스 아마존 S3, 데이터베이스 서비스 아마존 RDS, DNS 서비스 아마존 라우트 53, 서버리스 (Serverless) 코드 실행 서비스 아마존 람다(AWS Lambda) 등이 있다. 

관리형 서비스는 AWS가 인스턴스를 관리하기 때문에 사용자가 OS에 접속할 수 없다. 

장점: 사용자가 직접 버전업이나 다중화, 백업 등의 작업 할 필요가 없음
단점: 성능 문제가 발생해도 개선 방안이 많지 않음

변경할 수 있는 범위에 대한 트레이드오프 -> 사용자가 변경 가능한 범위가 적어지기 때문에 바꿀 수 있는 범위도 적기 때문 


<br/><br/><br/>
