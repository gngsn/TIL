## Kinesis vs SQS ordering

가정: 100 trucks, 5 kinesis shards, 1 SQS FIFO
<small>(트럭은 데이터를 제공/수신하는 주체)</small>

### Kinesis 데이터 스트림
- 평균적으로 가지는 값은 샤드당 트럭 20대
- 해시 기능으로, 각 트럭은 하나의 샤드에 지정되고 해당 샤드에 고정
- 트럭 데이터는 각 샤드에 순서대로 정렬
- 동시에 가질 수 있는 최대 소비자 개수는 5개 뿐 - 샤드가 5개이고, 샤드마다 하나의 소비자가 필요하기 때문
- Kinesis 데이터 스트림은 샤드가 5개인 경우에 초당 최대 5MB의 데이터를 수신할 수 있으며 처리량이 꽤 많은 편

### SQS FIFO

- 대기열은 하나 (샤드 및 파티션을 정의할 필요 없이 SQS FIFO 대기열이 하나만 있음)
- 각 트럭 ID에 상응하는 그룹 ID를 100개 생성 (그룹 ID가 100개가 되고 소비자도 최대 100개가 될 수 있음)
- 규모: 최대 초당 300, 배치를 사용하면 3,000개의 메시지를 가짐

⭐️ Kinesis 데이터 스트림을 사용할 경우는 데이터 생산을 여러 곳에서 많은 데이터를 전송하고 샤드당 데이터를 정렬할 때
⭐️ SQS FIFO는 그룹 ID 숫자에 따른 동적 소비자 수를 원할 때


## SQS vs SNS vs Kinesis ⭐️⭐️⭐️⭐️⭐️

### SQS 
- 소비자가 메시지를 요청해서 데이터를 가져오는(pull) 모델
- 소비자가 대기열에서 데이터를 소비(consumed)하면 삭제되면서 다른 소비자가 읽을 수 없게 됨
- 작업자(Consumer) 수 제한 없음
- 처리량(throughput)을 프로비저닝할 필요가 없음 (관리형 서비스)
  - 수백 수천 개의 메시지로 확장할 수 있음
- FIFO 대기열만 메세지 순서 보장
- 각 메세지 지연 기능 지원 (각 메시지를 30초 등 일정 시간 뒤 대기열에 명시)

### SNS
- 게시/구독 모델
- 데이터를 푸시해서 **다수의 구독자**가 메시지를 받음
- SNS 주제별로 1,250만 명의 구독자까지 가능
- 데이터는 지속적이지 않음: 데이터 잃을 가능성 존재 (한 번 SNS에 전송될 때 실패하면 잃음)
- 게시-구독 모델은 최대 10만 개의 주제로 확장 가능
- 처리량(throughput)을 프로비저닝할 필요가 없음
- fan-out 아키텍처 패턴을 위해 SQS와 결합 가능
- SNS FIFO 주제를 SQS FIFO 대기열과 결합 가능

### Kinesis
- Standard: pull data (소비자 <--pull-- Kinesis), 샤드당 2 MB/s 속도 지원
- Enhanced-fan out: push (Kinesis --push--> 소비자), 샤드 하나에 소비자당 2 MB/s의 속도
  - 처리량이 훨씬 높을 테니 Kinesis 스트림에서 더 많은 애플리케이션 읽기가 가능
- 데이터를 다시 재생 가능 (데이터 지속): 실시간 빅 데이터 분석, ETL 적합
- 샤드 레벨 지정 가능
  - Kinesis 데이터 스트림마다 원하는 샤드 양을 지정해야 함
- 데이터 만료 시점 지정
  - 녹화하는 시점, 1에서 365일까지 데이터 보존 가능
- 두 가지 용량 모드:
  - Provisioned mode: 프로비저닝 용량 모드. Kinesis 데이터 스트림으로부터 원하는 샤드 양을 미리 지정
  - on-demand capacity mode: 온디맨드 용량 모드. 샤드 수가 Kinesis 데이터 스트림에 따라 자동으로 조정
