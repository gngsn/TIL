# Site-to-Site VPN

⭐️ ✍🏻 **온프레미스와 AWS 사이의 Site-to-Site VPN 연결을 생성하는 과정**
<small>고객 게이트웨이와 가상 프라이빗 게이트웨이를 AWS에서 생성하고 둘을 Site-to-Site VPN 연결로 연결하는 방법</small>

**Corporate Data Center**

- 공용 인터넷을 통해 사설 Site-to-Site VPN을 연결해야 함
- VPN 연결이라서 공용 인터넷을 거치지만 암호화되어 있음
- 특정 기업 데이터 센터를 AWS와 비공개로 연결하려면 기업은 고객 게이트웨이, VPC는 VPN 게이트웨이를 가져야 함

Site-to-Site VPN을 구축하려면 두 가지 Gateway가 필요: VGW, CGW

## VGW: Virtual Private Gateway
*가상 프라이빗 게이트웨이*

- Virtual Private Gateway: VPN 연결에서 AWS 측에 있는 VPN concentrator
- VGW는 생성 시 Site-to-Site VPN 연결을 생성하는 VPC에 연결
- ASN 지정 가능

## CGW: Customer Gateway
*고객 게이트웨이*

- Customer Gateway: VPN 연결에서 데이터 센터 측에 있는 고객이 갖춰야 할 소프트웨어 혹은 물리적 장치


## Site-to-Site VPN Connections ⭐️⭐️

- Customer Gateway Device
고객 게이트웨이가 있는 기업 데이터 센터와 가상 프라이빗 게이트웨이를 갖춘 VPC가 있습니다

**⭐️ IP 주소**
- **고객 게이트웨이가 공용public일 때**: 고객 게이트웨이 장치 내에 있는 IP 주소
- **고객 게이트웨이가 사설private일 때**: NAT-T를 활성화하는 NAT 장치 뒤에 있는 NAT 장치의 공용 IP 주소

**⭐️ Route Propagation**
- 서브넷의 VPC에서 라우트 전파를 활성화해야 Site-to-Site VPN 연결이 실제로 작동

**⭐️ Security Group - ICMP**
- 온프레미스에서 AWS로 EC2 인스턴스 상태를 진단할 때, 보안 그룹 인바운드 ICMP 프로토콜이 활성화됐는지 확인
  - 가령, EC2 인스턴스로 ping을 사용해 상태 체크를 하고 싶을 때
  - 비활성인 경우 연결 안 됨


## AWS VPN CloudHub ⭐️⭐️⭐️⭐️⭐️

: 여러 VPN 연결을 통해 모든 사이트 간 안전한 소통을 보장

**Example Scenario**

<img src="../../img/cloudhub.png" />

*VPC::**VGW** [1] ---- ? ---- [N] **CGW**::데이터 센터*

- 비용이 적게 드는 허브 및 스포크 모델(hub&spoke)로 VPN만을 활용해 서로 다른 지역 사이 기본 및 보조 네트워크 연결성에 사용
- VPC 내 CGW와 VGW 하나 사이에 Site-to-Site VPN을 생성
- VPN 연결이므로 모든 트래픽이 암호화되어 공용 인터넷을 통함 (사설 네트워크로는 연결 안됨)
- 설치 방법: 
  - VPG(가상 프라이빗 게이트웨이) 하나에 Site-to-Site VPN 연결을 여러 개 생성 -> 동적 라우팅을 활성화 -> 라우팅 테이블 구성

<br />