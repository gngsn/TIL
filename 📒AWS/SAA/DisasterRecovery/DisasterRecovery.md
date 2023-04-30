# Disaster Recovery

⭐️⭐️⭐️ 솔루션 아키텍트라면 재해 복구를 꼭 알아 둬야 함

Disaster Recovery? 회사의 사업이나 재정에 부적적인 영향을 미치는 재해를 **대비**하고 **복구**

- On-premise: 온프레미스 간 재해 복구. 전형적인 재해 복구 유형 / 비용 큼
- On-premise + AWS Cloud: 클라우드 재해 복구. **온프레미스를 기본 데이터 센터**로 두고 재해 발생 시 클라우드를 사용하는 방식을 하이브리드 복구
- AWS Cloud: 완전 클라우드 유형

**핵심 용어**

- RPO: Recovery Point Objective.복구 시점 목표. 얼마나 자주 백업을 실행할지 시간상 어느 정도 과거로 되돌릴 수 있는지를 결정
  - 재해 발생 시: RPO ~ 재해 발생 시점 사이에 데이터 손실이 발생
  - 예를 들어 데이터를 매시간 백업한다면 재해가 발생했을 때 한 시간 전으로 돌아갈 수 있으니까 그만큼의 데이터를 잃게 됨
  - RPO는 한 시간, 1분 등 원하는 대로 설정 가능

- RTO: Recovery Time Objective. 복구 시간 목표. 재해 발생 후 복구할 때 사용
  - 재해 발생 시점과 RTO의 시간 차: **애플리케이션 다운타임**
  - 다운타임을 24시간 or 1분 설정


### 재해 복구 전략

- 먼저 백업 및 복구 : RTO가 작음
- 파일럿 라이트 
- 웜 대기
- 핫 사이트 / 다중 사이트 접근
(아래 세 개는 비용이 더 들지만 RTO가 빠름 = 전반적으로 다운타임이 줄어든다는 뜻)


### Backup and Restore (High RPO)

#### Backup
AWS 백업을 사용하면 AWS 서비스 전반에 걸친 데이터 보호를 중앙 집중화하고 자동화 가능. 이는 데이터 보호를 위한 규정 준수, 혹은 기업의 정책을 지원하는 데에 도움이 됨.

- 기업 데이터 센터 ==> AWS Cloud(S3 버킷)
  - **AWS Storage Gateway**: 시간에 따른 데이터를 백업. ie. 수명 주기 정책을 만들어 비용 최적화 목적으로 Glacier에 데이터를 입력
  - **AWS Snowball**: RPO는 대략 일주일. 일주일에 한 번씩 많은 양의 데이터를 Glacier로 전송할 수도 있음
    - 데이터 센터의 데이터가 전부 사라진다면 Snowball 장치로 일주일에 한 번만 보냈기에 한 주 치 데이터를 통째로 잃게 됨
- AWS Cloud ==> AWS Cloud
  - EBS 볼륨 / Redshift / RDS: 정기적으로 스냅샷을 예약하고 백업해 두면 스냅샷을 만드는 간격에 따라 RPO가 달라짐

#### Restore
- AMI를 사용해서 EC2 인스턴스를 다시 만들거나 / 애플리케이션을 스핀 업하거나 
- 스냅샷에서 Amazon RDS 데이터베이스 EBS 볼륨, Redshift 등을 바로 복원 및 재생산

**=> 백업 및 복구는 아주 쉽고 비용이 저렴한데 RPO와 RTO가 높음**


### Pilot Light

- 애플리케이션 축소 버전이 클라우드에서 항상 실행되고 보통 크리티컬 코어가 되는 구조
- 백업 및 복구와 아주 비슷하지만 크리티컬 시스템이 한창 작동 -> 복구 시 여타 시스템만 추가: 속도가 더 빠름

<pre>
Example. 서버와 데이터베이스를 갖춘 데이터 센터와 AWS Cloud가 있을 때
✔️ <b>DC의 메인 DB</b>에서 <b>RDS로 데이터를 계속 복제</b>한다면 언제든 실행할 수 있는 RDS 데이터베이스를 확보
-> RPO와 RTO가 낮아짐

✔️ 서버는 Route 53가 데이터 센터 서버에서 클라우드 EC2 인스턴스를 재생산 및 실행 처리, 중요한 건 데이터라 서버는 조금 뒷 전
<b>⭐️ 크리티컬 코어 보조에만 사용한다는 점을 기억</b>
</pre>


### Warm Standby

- 웜 대기는 시스템 전체를 실행하되 최소한의 규모로 가동해서 대기하는 방법
- 재해가 발생하면 프로덕션 로드로 확장 가능


<pre>
Example. 각 데이터 센터, AWS Cloud가 아래 처럼 있을 때
- Customer DC: Reverse Proxy -> App Server -> Master DB
- AWS Cloud: Route 53 / ELB / EC2 Auto Scaling (minimum) / RDS Slave (running)

<b>재해 발생 시</b>
- Route 53로 ELB 장애 조치 -> 애플리케이션이 데이터를 조회 위치 변경 가능
- 오토 스케일링 사용해서 애플리케이션 빠르게 확장 가능
- RDS Slave에서 데이터를 취하도록 변경
<b>⭐️ 최소한의 시스템 전체 기능 실행:  RPO / RTO 줄어듦</b>
</pre>


### Multi Site / Hot Site Approach

다중 사이트 혹은 핫 사이트 접근
- 몇 분, 몇 초 정도로 RTO가 낮지만 비용이 상당
- AWS와 온프레미스에서 두 완전 프로덕션 스케일을 얻음 => **액티브-액티브 유형 설정**
- 온프레미스 데이터 센터 완전 프로덕션 스케일과 데이터 복제를 진행하는 동시에 AWS 데이터 센터 완전 프로덕션 스케일이 가능
- 이미 실행 중인 핫 사이트가 있어서 Route 53가 기업 DC와 AWS Cloud 둘 다 요청 라우팅 가능


### All AWS Multi Region

#### Backup
- EBS Snapshots, RDS automated backups / Snapshots, etc…
- Regular pushes to S3 / S3 IA / Glacier, Lifecycle Policy, Cross Region Replication
- From On-Premise: Snowball or Storage Gateway

#### High Availability
- Use Route53 to migrate DNS over from Region to Region
- RDS Multi-AZ, ElastiCache Multi-AZ, EFS, S3
- Direct Connect 복구: Site to Site VPN

#### Replication
- RDS Replication (Cross Region), AWS Aurora + Global Databases
- Database replication from on-premises to RDS
- Storage Gateway

#### Automation
- CloudFormation / Elastic Beanstalk to re-create a whole new environment
- Recover / Reboot EC2 instances with CloudWatch if alarms fail
- AWS Lambda functions for customized automations

#### Chaos
- Netflix has a “simian-army” randomly terminating EC2
  - 넷플릭스 예제: 모든 걸 AWS에서 실행하고 Simian Army를 만들어 EC2 인스턴스를 무작위로 종료 -> 무작위로 가동 중인 애플리케이션 서버를 중단 (우회 테스트가 아니라 실제로 가동 중인 걸 종료) -> 장애가 발생해도 인프라가 무사하도록 카오스 몽키를 실행해 이것저것 무작위로 종료


## SCT: Schema Conversion Tool
- 소스 데이터베이스와 대상 데이터베이스가 다른 엔진을 갖고 있을 때, 데이터베이스의 스키마를 다른 엔진으로 변환


<pre>
<b>Q. AWS로, 특히 Amazon Aurora로 이전시키려는 온프레미스 Oracle 데이터베이스가 있습니다. 어떻게 이전해야 할까요?</b>
데이터베이스 스키마를 변환하기 위해 AWS 스키마 변환 도구(AWS SCT)을 사용한 후, AWS 데이터베이스 이전 서비스(AWS DMS)를 사용해 데이터를 이전
</pre>

## AWS Application Discovery Service

- **온프레미스 서버**나 **데이터 센터**에서 클라우드로 마이그레이션 할 때, 서버를 스캔하고 마이그레이션에 중요한 서버 설치 데이터 및 종속성 매핑에 대한 정보를 수집
- 어떻게 마이그레이션할지, 무엇을 먼저 마이그레이션할지 알 수 있음

**1. AWS Agentless Discovery Connector**
- Agentless Discovery
- VM inventory, configuration, and performance history such as CPU, memory, and disk usage
: 가상 머신, 구성, CPU와 메모리 및 디스크 사용량과 같은 성능 기록에 대한 정보를 제공

**2. AWS Application Discovery Agent**
- Agent-based Discovery
- System configuration, system performance, running processes, and details of the network connections between systems
: 시스템 구성, 성능, 실행 중인 프로세스, 시스템 사이의 네트워크 연결에 대한 세부 정보 얻음, 종속성 매핑을 얻는 데 좋음


### AWS Application Migration Service (MGN)
: Lift-and-shift(rehost) 솔루션. 물리적, 가상, 또는 클라우드에 있는 다른 서버를 AWS 클라우드 네이티브로 실행

<pre>회사에서 자사의 기존 웹 사이트, 애플리케이션, 서버, 가상 머신 및 데이터를 AWS로 이전하는 계획을 세울 때, 최소 다운타임과 최소 비용으로 리프트 앤 시프트(Lift and Shift) 마이그레이션을 진행 가능</pre>

