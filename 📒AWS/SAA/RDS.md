# AWS RDS

- **RDS: 관리형 서비스**

    - 데이터베이스 프로비저닝, OS 패치가 완전 자동화

    - 지속적으로 특정 타임스탬프의 백업 생성으로, 특정 시점 복원 가능

    - 대시보드 모니터링

    - 읽기 전용 복제본 -> 읽기 성능 개선

    - 다중 AZ: 재해 복구

    - 업그레이드로 유지 관리 (Maintenance window)

    - 인스턴스 유형 확장성: 수직/수평 확장

    - EBS (gp2/io1) 에 백업

- **SSH 지원 X**

## RDS Storage Auto Scaling ⭐️

- RDS 스토리지 오토 스케일링 기능이 활성화되어 있을 때, 초기 스토리지 용량보다 데이터베이스를 많이 사용하면 RDS가 이를 감지해서 자동으로 스토리지를 확장

  - DB를 다운시키는 등의 작업을 할 필요가 없음

  - ex. 애플리케이션이 RDS 데이터베이스에서 읽기와 쓰기 작업을 많이 하면 임곗값에 도달하게 되고 RDS가 자동으로 스토리지를 오토 스케일링

- 최대 스토리지 임곗값(Maximum Storage Threshold) 지정: 스토리지를 확장할 최대치를 지정

- 다음과 같은 상황에서 자동으로 스토리지가 자동 확장 (오토 스케일링이 활성화되어 있을 경우)

  - 남은 공간이 10% 미만이 될 때

  - 스토리지 부족 상태가 5분 이상 지속 될 때

  - 지난 수정으로부터 6시간이 지났을 때

=> 워크로드를 예측할 수 없는 애플리케이션에서 굉장히 유용

- 모든 RDS 데이터베이스 엔진에서 지원: MariaDB, MySQL PostgreSQL, SQL Server, Oracle


<small>⭐️⭐️ RDS 읽기 전용 복제본(Read Replicas)과 다중 AZ(Multi-AZ)의 차이, 각 사례를 아는 것이 중요 ⭐️⭐️</small>

애플리케이션 - 데이터베이스 인스턴스에 대해 읽기와 쓰기를 수행

- 특정 데이터베이스 인스턴스가 너무 많은 요청을 받아서 충분히 스케일링할 수가 없음 

  -> 읽기 스케일링

- 읽기 전용 복제본을 최대 다섯 개까지 생성 가능

- ⭐️⭐️ 세 가지 다른 옵션: 동일한 가용 영역(Within AZ) / 가용 영역(Cross AZ) / 리전을 걸쳐서(Cross Region) 

<br/>

### Read Replica ⭐️⭐️⭐️
- 최대 5 개

<img src="./img/../../img/rds1.png" />

- 동일 AZ, 서로 다른 AZ, 혹은 서로 다른 리전 Within AZ, Cross AZ or Cross Region
- 메인 RDS 데이터베이스 인스턴스와 두 개의 읽기 전용 복제본 사이에 비동기식 복제가 발생
- 비동기식: 결론적으로 읽기가 일관적으로 유지된다는 것을 의미 (Asynchronous: the reads are eventually consistent)
  - 애플리케이션에서 데이터를 복제하기 전, 읽기 전용 복제본을 읽어들이면 모든 데이터를 얻을 수 있다는 의미

### Read Replica(읽기 복제본)의 승격

- Good for read for scaling reads. 읽기 스케일링에 적합한 조회에 탁월

- 데이터베이스로 승격하여 사용할 수도 있음

- 복제본 중 하나를 데이터베이스로 사용하고자 설정하면, 그에 대한 권한을 획득하면 해당 복제본을 데이터베이스로 승격시킬 수 있음 => 그 후, 이 복제본은 복제 메커니즘을 완전히 탈피하여 자체적인 생애 주기를 갖음

### 사례

1. 일반 프로덕션 애플리케이션에 연결된 RDS 데이터베이스를 가지고 보고 및 분석을 진행하려고 함

2. 프로덕션과 동일한 RDS를 사용하면 많은 부하를 끼칠 수 있고, 장애를 발생시킬 수 있음

3. Read Replica를 생성하면 (비동기식 복제 발생) 애플리케이션이 생성한 읽기 전용 복제본에서 읽기 작업과 분석을 실행

4. 생산 애플리케이션은 전혀 영향을 받지 않음

- Read replicas are used for SELECT (=read) only kind of statements (not INSERT, UPDATE, DELETE)

### Network Cost ⭐️

- AWS에서는 하나의 가용 영역에서 다른 가용 영역으로 데이터가 이동할 때에 비용이 발생
- But, 예외 존재: **동일한 리전**에 위치한 읽기 전용 복제본과의 네트워크 비용은 무료 (다른 AZ라도)

✔️ Same Region / Different AZ -> Async Replication Free
✔️ Different Region -> Cross-Region: Async Replication incur a replication fee for network 

### Multi AZ (Diaster Recovery)

- 주로 재해 복구에 사용: Availability

- AZ A에 위치한 Master DB instance와 AZ B에 위치한 Standby DB로 Sync Replication (동기 복제)

  - Master에 쓰이는 변경 사항이 Standby 인스턴스에도 그대로 복제

- Master DB instance와 Standby DB는 하나의 DNS 이름을 갖음

  - 애플리케이션이 해당 DNS 이름으로 통신하며, Master에 문제가 생길 때 Standby DB에 자동으로 장애 조치가 수행

- Master DB의 인스턴스 또는 스토리지에 장애가 발생할 때 Standby DB가 새로운 마스터가 될 수 있음