# Auto Scaling Group

## Automatic Scaling

Automatic Scaling의 세 가지 설정 :
1. Dynamic Scaling Policies
2. Predictive Scaling Policies
3. Scheduled Actions

### 1. Dynamic Scaling

#### 1.1 Target Tracking Scaling

- 가장 단순, 설정 간단

<pre>
Example. <b>오토 스케일링 그룹의 평균 CPU 사용률을 추적</b>
- Metric Type : Average CPU utilization(평균 CPU 사용률)
- Target value: 40%

평균 CPU 사용률을 추적하여 40%대에 머무를 수 있도록 조절
</pre>

#### 1.2 Simple Scaling

- CloudWatch alarm 옵션 -> 경보가 발동될 때마다 용량을 스케일링
    - 그전에 미리 CloudWatch alarm를 설정 해야함
- 경보 발동 시 -> 용량 조절
  - Take the action: Set/Add/Delete...10/20/
  - ex. 용량 유닛 두 개를 추가하도록 설정하거나 그룹의 10%를 추가하도록 설정
  - N개의 유닛의 용량을 합친 만큼을 계산할 수도 있음

<pre>
Example. <b>오토 스케일링 그룹의 평균 CPU 사용률을 추적</b>
- CloudWatch alarm: Average CPU utilization(평균 CPU 사용률)
- Take the action: Set ... 40

평균 CPU 사용률을 추적하여 40%대에 머무를 수 있도록 조절
</pre>

#### 1.3 Step Scaling

- 경보 값을 기준으로 여러 단계를 갖도록 다수의 경보를 설정
  - CloudWatch 경보 설정 후, 단계 별 한 번에 유닛의 수를 추가/제거 설정
  - Simple Scaling에서 스텝이 추가됨
- Example. 경보 값이 아주 높은 경우, 10개의 용량 유닛을 추가하도록 설정하면서, 너무 높지 않은 경우에는 하나만 추가

<br/>

### 2. Predictive Scaling Policies
*예정된 작업 설정 - 예측 스케일링*

- **AWS 내 오토 스케일링 서비스를 활용하여 지속적으로 예측을 생성**
- 시간에 걸쳐 과거 로드를 분석하고, 다음 스케일링을 예측 생성
- 해당 예측을 기반으로 사전에 스케일링 작업이 예약

#### Metrics Types
: 설정 중 *Metrics and target utilization*에서 설정할 수 있는 **Metrics Type**

**1. CPUUtilization**
*CPU 사용률*

**2. RequestCountPerTarget**
*대상별 요청 수 지표*
: 가령, 세 개의 EC2 인스턴스를 갖는 오토 스케일링 그룹이 있으며 현재 ALB에서 이들 전체로 요청이 분산되어 나가고 있을 때, EC2에 3씩의 요청이 전달된다면, 현재 RequestCountPerTarget(대상별 요청 수 지표)는 **3**.

**3. Average Network In/Out**
*평균 네트워크 입출력량*
: 가령, 업로드와 다운로드가 많아 EC2 인스턴스에 대해 해당 **네트워크에서 병목 현상이 발생**할 것으로 판단된다면, **평균 네트워크 입출력량을 기반**으로 스케일링을 수행해서 특정 임계값에 도달할 때 스케일링을 수행하도록 설정할 수 있음

**4. Any custom metric**
*대상별*
: 직접 CloudWatch에서 애플리케이션 별로 지표를 설정하고 이를 기반으로 스케일링 정책을 바꿀 수 있음

<br/>

### 3. Scheduled Actions
*예약된 작업*

사전에 알고있는 사용 패턴을 바탕으로 스케일링을 예상

<pre>
Example. <b>금요일 오후 5시에 큰 이벤트가 예정</b>
-> ASG 최소 용량을 <b>매주 금요일 오후 5시마다</b> 자동으로 <b>10까지</b> 늘리도록 설정
</pre>

<br/>

## Scaling Cooldown
: 스케일링 휴지

- 스케일링 작업이 끝날 때마다 인스턴스의 추가 / 삭제 상관없이, 기본적으로 **5분 혹은 300초의 휴지 기간을 갖음**
- 휴지 기간에는 ASG가 추가 인스턴스를 실행 또는 종료할 수 없음
- 새 인스턴스가 안정화되도록 새로운 지표의 양상을 살펴보기 위함
  - 스케일링 작업이 발생할 때에 기본으로 설정된 휴지가 있는지 확인해볼 필요가 있음
  - 휴지 기간이면 실행 작업을 무시 / 아닐 경우에는 스케일링 작업을 수행

- AMI를 이용하여 EC2 인스턴스 **구성 시간을 단축(요청을 좀 더 신속히 처리)**하는 것이 좋음
  - 휴지 기간 단축 -> ASG 상에서 더 많은 동적 스케일링이 가능
