# VPC 

계정에 생성되는 VPC인 기본 VPC를 살펴봅시다 
새로운 AWS 계정은 모두 기본 VPC가 있고 바로 사용할 수 있어요 
새로운 EC2 인스턴스는 서브셋을 지정하지 않으면 기본 VPC에 실행됩니다 
어쨌든 계정을 시작하면 VPC는 하나만 생깁니다 

기본 VPC는 처음부터 인터넷에 연결돼 있어서 인스턴스가 인터넷에 액세스하고 또 내부의 EC2 인스턴스는 
공용 IPv4 주소를 얻습니다 EC2 인스턴스를 생성하자마자 연결할 수 있는 이유가 바로 이 때문입니다 
또한 EC2 인스턴스를 위한 공용 및 사설 IPv4 DNS 이름도 얻고요 


## 1. VPC
## 2. Subnet
## 3. Internet Gateway
## 4. Bastion Host
## 5. NAT Gateway

## 6. NACL

### Security Groups & Network Access Control List

**EX. 서브넷 보안 단계**

#### #1. Incoming Request
*요청이 EC2 인스턴스 내부로 이동*

**1/ 요청이 서브넷에 들어가기 전, 먼저 NACL로 이동**
- NACL에는 몇 가지의 인바운드 규칙이 정의되며 요청이 허용되지 않으면 내부로 들어갈 수 없음

**2/ 첫 번째 요청이 NACL을 통과 후 서브넷 내부에 도달**
- 보안 그룹 인바운드 규칙 검증
- 요청이 명시적으로 허용 받지 못하면 거부


**만약 요청이 보안 그룹의 인바운드 규칙을 통과하면, EC2 인스턴스에 도달**

- 다시 서브넷을 나갈 땐, 아웃바운드 규칙은 자동으로 허용 ← Security Group: Stateful

```
NACL: Stateless. 무상태
Security Groups: Stateful. 상태 유지
```
- 보안 그룹의 특징이 상태 유지이기 때문, 다시 말해 들어간 것은 전부 나올 수 있음

**3/ 서브넷 밖으로 이동**
- NACL 아웃바운드의 규칙이 평가 ← NACL 무상태
- 규칙이 통과하지 못하면 요청도 통과하지 못함

<br/>

#### #2. Outgoing Request

*보안 그룹 내 EC2 인스턴스가 아웃바운드 요청*

**1/ 보안 그룹 내 EC2 인스턴스가 서브넷으로 이동**
- 보안 그룹 아웃바운드 규칙 평가

**2/ 서브넷 밖으로 이동**
- NACL 아웃바운드 규칙 검증: 규칙이 적절하고 요청이 허가될 때

<small>EC2 내에서 `www.google.com`로 ping을 보내면, 이런 식으로 평가된 요청이 `www.google.com`에 도달하고 Amazon 웹 서비스로 다시 돌아오는 것</small>

**3/ 서브넷 내로 응답이 돌아옴**
- NACL 인바운드 규칙이 평가될 수 있음 ← NACL 무상태

**서브넷에서 Security Group 내로 응답이 돌아옴**
- 보안 그룹 수준에서 서브넷의 인바운드 규칙은 무조건 허용 ← Security Group: Stateful

<br/>

### NACL: 네트워크 액세스 제어 목록

NACL: Network Access Control List


- 서브넷을 오가는 트래픽을 제어하는 방화벽과 비슷
- One NACL per Subnet. 서브넷마다 하나의 NACL 존재. 새로운 서브넷에는 **Default NACL**이 할당됨
- NACL 규칙 정의, NACL Rules
  - 규칙에는 숫자가 있고 범위는 1 (Highest Priority) ~ 32,766 (Lowest Priority)
  - 규칙 비교 시 첫 번째 매칭만 적용 First rule match 
  - 별표(*): 일치하는 규칙이 없으면 모든 요청을 거부
  - AWS는 이 규칙을 100씩 추가하는 것을 권장 (사이에 규칙을 추가하기 용이)

- 새로 만들어진 NACL은 기본적으로 모두를 거부
- NACL의 아주 좋은 사용 사례를 들면 **서브넷 수준에서 특정한 IP 주소를 차단**하는데 적합
- NACL 위치: 서브넷 수준. 공용 서브넷 및 사설 서브넷 등에 위치

## Default NACL ⭐️⭐️⭐️

<img src="../img/DefaultNACL.png">

- 연결된 서브넷을 가지고 인바운드/아웃바운드의 모든 요청을 허용 → 기본 NACL은 매우 개방적
- 기본 NACL을 수정하지 않는 것 추천
  - NACL이 모든 것을 드나들도록 허용하지 않으면 AWS를 시작할 때 심각한 디버깅을 해야 함
- 사용자 정의 네트워크 ACL이 필요하다면 만들 수 있음

=> 즉, **⭐️ 기본 NACL이 기본적으로 서브넷과 연결된다면 모든 것이 드나들도록 허용된다는 뜻**


### Ephemeral Ports, 임시 포트 ⭐️⭐️⭐️

- 클라이언트와 서버가 연결되면 포트를 사용해야 함
- 클라이언트 - 규정된 포트 -> 서버 연결
  - ex. HTTP 포트는 80, HTTPS 포트는 443 SSH 포트는 22 등
- 서버 -(**임시 포트**)-> 클라이언트
  - 서버도 응답을 하려면 클라이언트에 연결해야 함
  - 클라이언트는 기본적으로 개방된 포트가 없기 때문에 클라이언트가 자체적으로 **특정 포트**를 열게 됨
  - 이 포트는 임시라서 클라이언트와 서버 간 연결이 유지되는 동안만 열려 있음

**임시 포트는 운영 체제에 따라 포트 범위가 다르고, 아래 범위 내 임시 포트로 선택**
- Windows 10: 49152 ~ 65,535
- Linux: 32,768 ~ 60,999


#### EX. 클라이언트 - 데이터베이스 연결

- Web Subnet (Public): EC2 Instance
- DB Subnet (Private): DB Instance - port 3306

**클라이언트가 데이터베이스 인스턴스에 연결을 시작하면 허용되어야 하는 규칙은?**
- 웹 NACL & DB NACL 서로 통신한다고 가정

1. Web Subnet NACL - TCP 아웃바운드 허용
   - 웹 NACL은 TCP부터 데이터베이스를 Port 3306를 통해 서브넷 CIDR까지 아웃바운드를 허용
2. DB SubnetNACL - TCP 인바운드 허용
   - 데이터베이스 측에서는 DB NACL가 웹 서브넷 CIDR에서 포트 3306으로 인입하도록 허용

*데이터베이스가 클라이언트로 요청에 회신을 할 때*

3. DB Subnet NACL - TCP 아웃바운드 허용
   - 클라이언트는 임시 포트를 가지게 되며, 요청에 대해 무작위 포트가 할당
   - DB NACL은 포트 및 임시 포트 범위: 1,024 ~ 65,535

4. Web Subnet NACL - TCP 인바운드 허용
   - 웹 NACL은 DB 서브넷 CIDR의 임시 포트 범위에서 인바운드 TCP를 허용해야 함


- ⭐️ **네트워크 ACL을 여러 서브넷과 연결할 수 있음**
  - 서브넷: NACL = N:1
  - 서브넷은 한 번에 하나의 네트워크 ACL에만 연결할 수 있으며, 네트워크 ACL을 서브넷과 연결하면 이전 연결이 제거됨
  - 즉, 다중 NACL 및 서브넷이 있다면 각 NACL 조합이 NACL 내에서 허용되어야 함
  - CIDR 사용 시, 서브넷이 고유의 CIDR을 갖기 때문
  - NACL에 서브넷을 추가하면 NACL 규칙도 업데이트해서 연결 조합이 가능한지 확인해야만 함


<a href="https://docs.aws.amazon.com/vpc/latest/userguide/vpc-network-acls.html#nacl-ephemeral-ports">
🔗 Link: nacl-ephemeral-ports
</a>

### Security Group vs. NACL

| | Security Group | NACL |
| -- | --- | --- |
| level | Instance | Subnet |
| supports | allow rules | allow rules and deny rules (특정 IP 주소 거부 가능) |
| state | **Stateful** | **Stateless** (임시 포트) |
| allow | 트래픽 허용이 될 때까지 규칙 검증 | 트래픽이 허용되는지 순서에 따라 가장 높은 우선순위로 매칭되는 룰에 검증 |
| range | EC2에 지정한 규칙이 존재할 때 검증 | NACL과 연결된 Subnet 내 모든 EC2 인스턴스에 적용 |



## 7. VPC Peering

- AWS 네트워크를 통해 프라이빗하게 연결
  - 다양한 리전과 계정에서 VPC를 생성할 수 있음
  - AWS 네트워크를 통해 연결하고 싶을 때 사용
- VPC가 모두 **같은 네트워크**에서 작동하도록 만들기 위함
- VPC 네트워크 CIDR가 겹치면 안됨
  - 연결 시 CIDR가 겹치면 통신 불가
- ⭐️⭐️⭐️ VPC 피어링은 두 VPC 간에 발생하며 **전이되지 않음, NOT transitive** 
  - 서로 다른 VPC가 통신하려면 VPC 피어링을 활성화해야 함

**VPC Peering is NOT transitive, 전이 불가**
```
VPC A, B, C가 있을 때, 
A -⭕️-> B: A와 B는 Peering 연결 생성 -> 통신 가능 상태
B -⭕️-> C: B와 C는 Peering 연결 생성 -> 통신 가능 상태
A -❌-> C: A와 C의 VPC 피어링 연결을 활성화해야 둘이 통신 가능
```

⚠️ 또한 VPC 피어링이 있을 때 1. 활성화, VPC 서브넷 상의 2. Root Table도 업데이트해서 EC2 인스턴스가 서로 통신할 수 있게 해야 함

### Good to know

- Cross Account / Cross Region: 가능 다른 계정 간, 리전 간에도 가능
- 동일 리전의 계정 간 Peered VPC에서 보안 그룹을 참조할 수도 있음
  - CIDR이나 IP를 소스로 가질 필요가 없고 보안 그룹을 참조할 수도 있음


## 8. VPC Endpoint
**TL;DR;**
- 모든 AWS 서비스는 공개적으로 열려 있음 (Public URL)
- VPC Endpoints(AWS의 PrivateLink 기반)는 private network를 사용하여 AWS 서비스에 연결하도록 도와줌 (public Internet이 아닌)
- 중복과 수평 확장이 가능
- AWS 서비스들에 접근할 때 IGW, NATGW의 필요성을 없앰
- 만약 이슈가 발생하면 아래 확인
  1. VPC 내의 DNS Setting Resolution 확인
  2. Route Tables 


#### Private -> Public 접근: 세 가지 시나리오

**요구사항.** AWS 서비스들에 퍼블릭 인터넷을 경유하지 않고 프라이빗 액세스를 원할 때

**Q. Public Subnet vs Private Subnet?**

1. (Public Subnet) EC2 Instance -> Internet Gateway (VPC-Public) -> (Public) Amazon SNS
   - 인터넷 게이트웨이에서 바로 Amazon SNS 서비스로
   - 비용이 많이 듦
2. (Private Subnet) EC2 Instance -> (Public Subnet) NAT Gateway -> Internet Gateway (VPC-Public) -> (Public) Amazon SNS
   - **EC2 인스턴스**에서 **NAT Gateway**를 거쳐 **인터넷 게이트웨이**로 향하도록 하고 Amazon SNS 서비스에 **퍼블릭**으로 액세스
   - 일단 NAT Gateway를 거칠 때 비용이 발생하고 그 다음 인터넷 게이트웨이에서는 비용이 발생하지 않음
   - 하지만, 허브가 여러 개 있을 테니 어쨌든 효율적이라고는 할 수 없음
3. (Private Subnet) EC2 Instance -> VPC Endpoint (VPC-Public) -> (Public) Amazon SNS
   - 퍼블릭 인터넷을 거치지 않고도, 프라이빗 AWS 네트워크만 거쳐서 인스턴스에 액세스할 수 있음
   - 네트워킹을 구성해서 프라이빗 서브넷에 있는 EC2 인스턴스를 VPC 엔드포인트를 거쳐 직접 Amazon SNS 서비스에 연결할 수 있는데 이때 **네트워크가 AWS 내에서만 이루어진다는 장점**이 있음
   - VPC 엔드포인트를 사용하면 AWS PrivateLink를 통해 프라이빗으로 액세스
   - 인터넷 게이트웨이나 NAT Gateway 없이도 AWS 서비스에 액세스 가능: 간단한 네트워크 인프라 설계 가능


### VPC 엔드포인트 유형

#### Interface Endpoint (PrivateLink를 이용)

- ENI 프로비저닝, ENI는 VPC의 Private IP 주소이자 AWS의 엔트리 포인트
  - ENI가 있으므로 반드시 보안 그룹을 연결해야 함
  - PrivateLink를 사용하는 인터페이스 유형 VPC 엔드포인트로 ENI를 통하여 이 서비스에 액세스
- 대부분의 AWS 서비스를 지원
- 청구 요금은 **시간 단위** 또는 **처리되는 데이터 GB 단위**

#### Gateway Endpoint

- 특이하게도 게이트웨이를 프로비저닝; 게이트웨이는 반드시 라우팅 테이블의 대상이 되어야 함
  - ⭐️ IP 주소를 사용하거나 보안 그룹을 사용하지 않고 라우팅 테이블의 대상이 될 뿐임
  - 라우팅 테이블 액세스일 뿐이므로 자동으로 확장
- ⭐️⭐️ Amazon S3와 DynamoDB 두 가지 사용 가능
- 무료
- 확장성이 더 높음 (라우팅 테이블만 수정하면 되기 때문에 Interface Endpoint보다 용이)

#### S3: Interface Endpoint vs. Gateway Endpoint
- 기본적으로 Gateway Endpoint가 무료이고 확장성이 높음
- 게이트웨이 엔드포인트보다 인터페이스 엔드포인트가 권장될 때?
  - 온프레미스에서 액세스해야 할 필요가 있을 때 (Site-to-Site VPN이나 직접 연결 방법)
      - 온프레미스에 있는 데이터 센터에 프라이빗으로 액세스해야 하는 경우 
  - 다른 VPC에 연결할 때

<br/>

## VPC Flow Logs

VPC Flow Logs
: VPC 흐름 로그. 인터페이스로 향하는 IP 트래픽 정보를 포착하는 것

- 연결 문제를 모니터링하고 트러블 슈팅하는 데 유용
- 로그 데이터는 Amazon S3와 CloudWatch Logs에 전송 가능
- AWS 관리형 인터페이스 정보를 포착
    - ELB, RDS, ElastiCache Redshift, Workspaces, NAT Gateway, Transit Gateway ...

**종류**
- VPC 계층 
- 서브넷 계층 
- Elastic Network Interface(ENI) 계층


### VPC Flow Logs Syntax

<img src="../im/../img/vpcFlowSyntax.png"/>

-> VPC를 통과하는 네트워크 패킷의 메타데이터: version, account-id, interface-id, 소스 주소(srcaddr), 대상 주소(dstaddr) 소스 포트(srcport), 대상 포트(dstport), 프로토콜(protocol), 패킷 수(packets), 바이트 수(bytes) 시작 동작(start action), 로그 상태(log-status)

- **srcaddr & dstaddr**: 문제가 있는 IP를 식별
  - 한 IP가 지속적으로 거부되면 여기서 확인 가능 
  - (문제가 있거나 특정 IP가 공격받았을 때)
- **srcport & dstport**: 문제가 있는 포트를 식별
- **action**: 요청이 수락되었는지 거부되었는지 식별
  - **보안 그룹**이나 **NACL** 계층에서 요청 성공/실패 확인


- 사용량 패턴 분석이나 악성 행동 감지 포트 스캔 등에 사용
- 흐름 로그를 쿼리 방법: 
  1. S3에서 Athena를 쓰는 방법 (Best)
  2. CloudWatch Logs Insight (스트리밍 분석을 원할 때)


#### 문제 트러블 슈팅
*이 흐름 로그를 보안 그룹과 NACL 문제 트러블 슈팅에 사용하는 방법은?*
\- **action 필드**

CASE1. action: 인바운드 REJECT가 표시된 경우
: EC2 외부에서 전송한 인바운드 요청이 거부되었다는 뜻. NACL이나 보안 그룹 중 하나가 요청 거부.

CASE2. 인바운드 ACCEPT + 아웃바운드 REJECT
: NACL 문제 (보안 그룹은 stateful, 상태 유지 중이므로)

CASE3. 아웃바운드 REJECT가 표시된 경우 (보내는 요청)
: NACL이나 보안 그룹의 문제라는 뜻

CASE4. 아웃바운드 ACCEPT 인바운드 REJECT
: 반드시 NACL가 원인


### Architectures

1/ VPC Flow Logs > CloudWatch Logs > CloudWatch Contributor Insights
: CloudWatch Logs로 전송된 흐름 로그는 CloudWatch Contributor Insights라는 기능을 사용하여, 상위 10위에 해당하는 VPC 네트워크에 가장 많이 기여하는 IP 주소나 ENI 등 식별 가능

2/ VPC Flow Logs > CloudWatch Logs > CW Alarm > Amazon SNS
: CloudWatch Logs에 흐름 로그를 보냄
    - SSH나 RDP 프로토콜에 관하여 지표 필터를 설정 가능
    - 평소보다 SSH나 RDP 프로토콜이 너무 많은 경우 CloudWatch 경보를 트리거하여 Amazon SNS 주제로 경보를 전송
    - 네트워크에 수상한 행동이 발생했을 수 있으므로

3/ VPC Flow Logs > S3 Bucket > Amazon Athena > Amazon QuickSight
: VPC 흐름 로그를 모두 S3 버킷에 전송 및 저장해서 Amazon Athena를 이용하여 SQL로 VPC 흐름 로그를 분석할 수 있음
    - Amazon QuickSight: 데이터 시각화
