# NAT

NAT: Network Address Translation, 네트워크 주소 변환

## NAT Instance

: 네트워크 패킷 재작성

- 사설 서브넷 EC2 인스턴스가 인터넷에 연결되도록 허용
- NAT 인스턴스는 공용 서브넷에서 실행되어야 하고, 공용 및 사설 서브넷을 연결
- 소스/목적지 확인 설정을 비활성화해야 함 => 소스/목적지 IP가 NAT Instance에 의해 변경되기 때문
- NAT 인스턴스에는 고정된 탄력적 IP가 연결되어야 함


### NAT Instance Overview

<img src="../../img/natInstance.png" style="max-width: 60%" />

**사설 서브넷에 위치한 인스턴스가 IP는 외부 서버로 액세스하기 위해 NAT 인스턴스를 통과해야 함**
접속하려는 서버 IP: 50.60.4.10

1. 사설 서브넷에서 외부 인터넷 액세스 시도 Dest: 50.60.4.10
2. 공용 서브넷에 보안 그룹이 있는 NAT 인스턴스를 실행
3. NAT 인스턴스에 탄력적 IP를 연결
4. 사설 서브넷의 라우팅 테이블을 수정: 사설 서브넷의 모든 대역이 NAT 인스턴스로 트래픽으로 전송되게 설정
   - `0.0.0.0/0` -> NAT instance
5. 사설 서브넷의 인스턴스가 외부 서버로 요청을 보내기 위해 NAT 인스턴스로 요청 전달: 
   - **소스 IP: `10.0.0.20` (Private IP)**
   - 목적지 IP: `50.60.4.10` (외부 서버 IP)
6. NAT 인스턴스가 외부 서버로 요청을 전달
   - **소스 IP: `12.34.56.78` (NAT 인스턴스 Public IP)**
   - 목적지 IP: `50.60.4.10` (외부 서버 IP)
   - ⭐️ 네트워크 패킷이 NAT 인스턴스로 재작성됨 (NAT 인스턴스의 역할)
7. 서버에서 응답
   - 소스 IP: `50.60.4.10` (외부 서버 IP)
   - 목적지 IP: `12.34.56.78` (NAT 인스턴스 Public IP)
8. NAT 인스턴스가 다시 EC2 인스턴스로 트래픽을 회신
   - 소스 IP: `50.60.4.10` (외부 서버 IP)
   - 목적지 IP: `10.0.0.20` (NAT 인스턴스 Public IP)


### NAT 인스턴스 단점
- 가용성이 높지 않음
- 초기화 설정으로 복원할 수 없어 여러 AZ에 ASG를 생성해야 함
- 복원되는 사용자 데이터 스크립트가 필요하기에 복잡함
- 작은 인스턴스는 큰 인스턴스에 비교해서 대역폭이 더 작음
- 보안 그룹과 규칙을 관리해야 함
- 인바운드에서는, 사설 서브넷의 HTTP/HTTPS 트래픽을 허용하고, SSH도 허용해야 함



## NAT Gateway

- AWS의 관리형 NAT 인스턴스로 가용성이 높음
- 사용량 및 NAT Gateway의 대역폭에 따라 요금 청구
- NAT Gateway는 특정 AZ에서 생성되고 탄력적 IP를 이어받음
- EC2 인스턴스와 **같은 서브넷에서 사용할 수 없음**, 다른 서브넷에서 액세스할 때만 사용
- 인터넷 게이트웨이 필요
- 높은 대역폭: 초당 5GB이며 자동으로 초당 45GB까지 확장 가능
- 사설 서브넷에서 NAT Gateway로 다시 인터넷 게이트웨이까지입니다
- 보안 그룹을 관리할 필요가 없음 (포트 활성화 고민 X)

### 작동 구조

1. 사설 인스턴스와 서브넷이 있고 인터넷에 액세스가 없는 상태
2. 공용 서브넷을 인터넷 게이트웨이에 연결
3. NAT Gateway를 공용 서브넷에 배포 -> NAT Gateway에 인터넷 연결이 생김
4. 사설 서브넷의 루트를 수정: EC2 인스턴스를 NAT Gateway에 연결


### NAT Gateway 고가용성

- NAT Gateway은 단일 AZ에서 복원 가능
- 다중 NAT Gateway를 여러 AZ에 배치: AZ가 중지 시 결함 허용 (resilient, 장애 대응)

**배치 방식**

- 하나의 NAT Gateway가 하나의 특정 AZ(AZ-A)에 위치
- 두 번째 AZ(AZ-B)에 추가 NAT Gateway를 생성
- 각 네트워크 트래픽은 AZ 안에 제한

**작동 방식**

AZ-A가 중지 -> AZ-B는 계속 작동
- AZ-B에도 NAT Gateway가 있기 때문
- 라우팅 테이블을 통해 AZ를 서로 연결할 필요는 없음: 그 AZ의 EC2 인스턴스가 액세스 불가 상태


## NAT Gateway vs. NAT Instance

|Attribute	| NAT gateway |	NAT instance |
|---|---|---|
|Availability|	Highly available within AZ (create in another AZ) |	Use a script to manage failover between instances |
| Bandwidth |	Up to 45 Gbps |	Depends on EC2 instance type |
| Maintenance |	Managed by AWS | Managed by you (e.g., software, OS patches, …) |
| Cost | Per hour & amount of data transferred | Per hour, EC2 instance type and size, + network $ |
| Public IPv4 | ✔️ | ✔️ |
| Private IPv4 | ✔️ | ✔️ |
| Security groups | ❌ (관리 필요 X) | ✔️ |
| Use as Bastion Host? | ❌ | ✔️ (Bastion Host로도 사용 가능) |
