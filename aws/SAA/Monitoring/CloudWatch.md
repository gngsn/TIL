# Amazon CloudWatch

*모니터링 서비스*

- 시스템의 성능 변경에 따라 반응, 리소스 활용 최적화, 운영 상태를 통합적으로 볼 수 있음
- 애플리케이션의 성능과 지표를 모니터링하는 데에 사용
- 모든 서비스에 대한 지표를 제공

<br/>

#### **Metrics**
: 지표. 모니터링할 변수
  - EC2 인스턴스 지표: CPUUtilization, NetworkIn 등
  - S3 지표: 버킷 크기 등
- Metrics belong to **namespaces**: 서비스당 namespace 하나
- Metric Streams: 측정 기준 & 지표의 속성 (instance id, environment, etc…)
- 지표당 최대 측정 기준은 10개
- 지표는 시간 기반 → 타임스탬프 필수
- 지표를 **CloudWatch 대시보드**에 추가해 모든 지표를 한 번에 볼 수 있음
- CloudWatch 사용자 지정 지표 생성 가능 (예, EC2 인스턴스로부터 메모리 사용량을 추출)

<br/>

## Metric Streams
- CloudWatch 지표를 CloudWatch 외부로 스트리밍 할 수 있음
- near-real-time 전송 + low latency
- 전송 대상: Amazon Kinesis Data Firehose / 타사 서비스 제공자 (Datadog, Dynatrace New Relic, Splunk, Sumo Logic ...)
- 

EX. 
1\. **CloudWatch 지표** ->  **Kinesis Data Firehose** (거의 실시간 스트리밍)
2.1 Kinesis Data Firehose -> **Amazon S3** -> **Amazon Athena** (지표 분석)
2.2 Kinesis Data Firehose -> **Amazon Redshift** (지표로 데이터 웨어하우스 생성)
2.3 Kinesis Data Firehose -> **Amazon OpenSearch** (대시보드를 생성 후 지표 분석)

- **모든 namespaces 지표** or **몇몇 namespaces의 지표만 필터링** 스트리밍

<br/>

## CloudWatch Logs
: AWS에 로그를 저장하는 최고의 장소, 유료

✔️ **Log groups**: 보통 애플리케이션을 나타내는 임의 이름을 가짐
    - 각 로그 그룹 내에는 로그 스트림이 있음
✔️ **Log stream**: instances within application / log files / containers
✔️ Log expiration policies: 로그 만료 정책. never expire, 30 days ..
✔️ 로그 전송 가능:
    - Amazon S3 (exports)
    - Kinesis Data Streams
    - Kinesis Data Firehose
    - AWS Lambda
    - ElasticSearch

<br/>

### CloudWatch Logs - Sources

✔️ SDK, CloudWatch Logs Agent, CloudWatch Unified Agent
✔️ Elastic Beanstalk: 애플리케이션 로그 컬렉션 전송
✔️ ECS: 컨테이너 로그 컬렉션 전송
✔️ AWS Lambda: 함수 자체에서 로그 컬렉션 전송
✔️ VPC Flow Logs: VPC 메타데이터 네트워크 트래픽 로그 전송
✔️ API Gateway: 받은 모든 요청 전송
✔️ CloudTrail 필터링한 로그 전송
✔️ Route53: 모든 DNS 쿼리를 로그로 저장

<br/>

### CloudWatch Logs Metric Filter
**필터 표현식**

- 출현 빈도를 계산해 지표 생성 가능
- 만들어진 지표를 CloudWatch 경보로 연동 가능


EX. 특정 IP가 찍힌 로그를 찾거나 또는 'ERROR'라는 문구를 가진 모든 로그를 찾을 수 있음

<br/>

### CloudWatch Logs Insights

- 로그를 쿼리 후, 이 쿼리를 대시보드에 바로 추가할 수 있음
- AWS에서 자주 쓰이는 쿼리들을 추가해 둠
- 간편한 쿼리 언어

<br/>

### S3 Export

- 내보내기가 가능해질 때까지 최대 12시간은 걸릴 수 있음
- API 호출: CreateExportTask
- 실시간 X: 시간이 따로 지정되어 있음
- CloudWatch Logs에서 로그를 스트림하고 싶다면 구독 필터를 사용해야 함

<br/>

### CloudWatch Logs Subscriptions
**구독 필터**

- CloudWatch Logs 상단에 적용하여 이를 목적지로 보내는 필터

CloudWatch Logs → Subscriptions Filter ...

1. Subscriptions Filter → Lambda --<small> <i>Real Time</i> </small>→ ES
2. Subscriptions Filter → Kinesis Data Firehose → ES
3. Subscriptions Filter → Kinesis Data Firehose --<small> <i>Near Real Time</i> </small>→ S3
4. Subscriptions Filter → Kinesis Data Stream → KDF, KDA, Lambda...

### Logs Aggregation Multi-Account & Multi Region

Multi-Account { CloudWatch Logs → Subscriptions Filter } => Kinesis Data Stream → Kinesis Data Firehose --<small> <i>Near Real Time</i> </small>→ S3

### CloudWatch Logs for EC2

EC2 인스턴스에서 CloudWatch로는 기본적으로 어떤 로그도 옮겨지지 않음
-> **EC2 인스턴스에서 CloudWatch Logs 에이전트 실행 후 로그 파일을 푸시**
- EC2 인스턴스에 로그 전송 용 IAM 역할 필요
- CloudWatch Logs 에이전트는 온프레미스 환경에서도 사용 가능

### CloudWatch Logs Agent

- 좀 더 오래된 CloudWatch Logs 에이전트
- CloudWatch Logs로 로그만 보냄

### CloudWatch Unified Agent

- 프로세스나 RAM 같은 추가적인 시스템 단계 지표를 수집
- CloudWatch Logs에 로그 전송
- 지표와 로그를 둘 다 사용하기 때문에 통합 에이전트

- SSM Parameter Store를 이용해 에이전트를 쉽게 구성할 수 있음
- 모든 통합 에이전트를 대상으로 중앙 집중식 환경 구성을 할 수 있음

**수집가능한 지표**

훨씬 세분화된 수준
- CPU (active, guest, idle, system, user, steal)
- Disk metrics (free, used, total), Disk IO (writes, reads, bytes, iops)
- RAM (free, inactive, used, total, cached)
- Netstat (number of TCP and UDP connections, net packets, bytes)
- Processes (total, dead, bloqued, idle, running, sleep)
- Swap Space (free, used, used %)
- Reminder: out-of-the box metrics for EC2 – disk, CPU, network (high level)


### CloudWatch Alarms

Alarms are used to trigger notifications for any metric
- Various options (sampling, %, max, min, etc…)
- Alarm States: OK, INSUFFICIENT_DATA, ALARM
- Period:
  - Length of time in seconds to evaluate the metric
  - High resolution custom metrics: 10 sec, 30 sec or multiples of 60 sec

#### CloudWatch Alarm Targets

- EC2 Instance의 상태를 Stop, Terminate, Reboot, Recover로 만들 수 있음
- Trigger Auto Scaling Action
- Send notification to SNS

### CloudWatch Alarms – Composite Alarms

- CloudWatch Alarms are on a single metric
- Composite Alarms are monitoring the states of multiple other alarms
- AND and OR conditions
- Helpful to reduce “alarm noise” by creating complex composite alarms

### EC2 Instance Recovery

Recovery: Same Private, Public, Elastic IP, metadata, placement group

