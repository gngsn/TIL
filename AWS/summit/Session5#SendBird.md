# 글로벌 대화형 서비스 개발 플랫폼 Sendbird가 AWS와 함께한 여정

Q. Sendbird는 어떤 AWS 서비스를 어떻게 잘 효과적으로 사용하고 있을까?

## Sendbird 소개
: 대화형 서비스 개발 플랫폼

- 제공 서비스: 채팅/영상통화/음성통화/라이브 스티리밍
<small>일대일/그룹 채팅 및 라이브 스트리밍/고객 상감/고객 관리 및 영업 메세지 등</small>

## Sendbird의 AWS 서비스 별 활용 소개

Sendbird Regions (VPC로 구분하고 있음)
AWS Region 내에 따로 구분해둠
- shared, dedicated


#### 1. Organization

**용도 별 Account 분리 및 관리: Team / Product / Environment**
- 권한 및 데이터, 리소스 불니 
- 별도의 Policy 적용
- 비용 분석

#### 2. Aurora RDS
초당 200L QPS, Data 200TB


#### 3. Auto Scaling Group 
- 자동화된 인스턴스 수 관리
- Scaling policy 와 Scheduled Action(트래픽이 예층되는 경우)을 조합하여 자동화 관리


#### 4. Container Orchestration - EKS
- Control plane 관리형 클러스터 생성
- Managed node group을 이용한 노드 관리
- IAM OIDC provider 기반 service account 관리

#### 5. Cost Explorer
*비용 모니터링 및 분석*
<small>최근 다양한 막강한 기능을 지원하고 있어서 적극적으로 사용하고 있다.</small>

- 기간, 그룹화, 필터 등 다양한 조건으로 검색 및 분석 가능
- 비용 효율화 제안: Rightsizing, Savings Plan 및 Reservation

<small>Slack에 Integration해서 사용 중</small>


#### 6. RI/SP/EDP
*리소스 약정을 통한 비용 할인*

- Reserved Instance 
- Savings Plan
- Enterprise Discount Program: 연 단위로 약정을 걸어 비용 절감

=> **On-Demand 대비 30% 이상 할인**
그로스 마진에 큰 영향을 줌


#### 7. Graviton
고성능 인스턴스 적용

ARM 기반으로 EC2 사용 -> EC2, Elastic Cache CPU 사용량 절감
서비스 인스턴스를 최대 33% 적게 사용/ 42% 비용 절감


## Sendbird 제품 소개 및 업데이트

### Sendbird Notification
- 고객들의 니즈를 확인하면서 디벨롭한 기능 - Notification 출시



