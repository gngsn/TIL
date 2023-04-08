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


