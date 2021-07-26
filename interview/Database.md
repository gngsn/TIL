## Database

### 클러스터링(Clustering)

클러스터링이란 여러 개의 DB를 수평적인 구조로 구축하는 방식



![img](https://blog.kakaocdn.net/dn/oaVae/btqKOCg14ow/kkpZDYChulrTJvyqRVKLbk/img.png)

1. 1개의 노드에 쓰기 트랜잭션이 수행되고, COMMIT을 실행한다.
2. 실제 디스크에 내용을 쓰기 전에 다른 노드로 데이터의 복제를 요청한다.
3. 다른 노드에서 복제 요청을 수락했다는 신호(OK)를 보내고, 디스크에 쓰기를 시작한다.
4. 다른 노드로부터 신호(OK)를 받으면 실제 디스크에 데이터를 저장한다.

#### 장점

- 노드들 간의 데이터를 동기화하여 항상 일관성있는 데이터를 얻을 수 있다.
- 1개의 노드가 죽어도 다른 노드가 살아 있어 시스템을 계속 장애없이 운영할 수 있다.

#### 단점

- 여러 노드들 간의 데이터를 **동기화하는 시간**이 필요하므로 Replication에 비해 쓰기 성능이 떨어진다.
- 장애가 전파된 경우 처리가 까다로우며, 데이터 동기화에 의해 **스케일링에 한계**가 있다.

 

**Active-Active** : 클러스터를 항상 가동하여 가용가능한 상태로 두는 구성 방식

**Active-Standby** : 일부 클러스터는 가동하고, 일부 클러스터는 대기 상태로 구성



<img src="https://blog.kakaocdn.net/dn/KYdFx/btqJvS5Jkzv/qe0c198IbxfUKKWzzo2oUK/img.png" alt="img" style="zoom:67%;" />



동일한 DB 서버를 두 대를 묶고 두 DB 서버를 **Active-Active** 상태로 운영하면, 하나의 DB 서버가 죽더라도 나머지 DB 서버가 살아있기 때문에 정상적으로 서비스가 가능. 또한 이전에는 하나의 서버가 부담하던 부하를 두 개의 DB가 나눠서 감당하므로 CPU, Memory 자원의 부하도 적어짐.

**BUT**, DB 스토리지를 두 DB 서버가 공유하기 때문에 **병목이 생길 수 있다는 점**과 이전보다 **많은 비용이 투자**되어야 한다는 점.

-> 해결 : DB 서버 중 한 대를 **Stand-by**로 두는 것. 말 그대로 준비 상태로 두고 Active 상태의 DB 서버에 문제가 생겼을 때 Fail over를 통해 두 서버가 Active와 Stand-by의 상태를 상호 전환함으로써 장애를 대응 할 수 있음. DB 스토리지 병목 현상이 해결.

<img src="https://blog.kakaocdn.net/dn/kyN23/btqJpgUooV2/J6zPktniXhJdTkJdttO8i0/img.png" alt="img" style="zoom:67%;" />



클러스터링의 설명을 보다 보면 DB 스토리지는 하나만 둬도 되는걸까? 하나 뿐인 DB 스토리지에 문제가 생기면 데이터를 복구할 수 없게 되기 때문입니다. -> **레플리케이션 (Replication)**



### Replication

### 리플리케이션(Replication)

리플리케이션이란 여러 개의 **DB를 권한에 따라 수직적인 구조(Master-Slave)로 구축**하는 방식

Master Node - 쓰기 작업 만 처리 

Slave Node - 읽기 작업 만 처리



![img](https://blog.kakaocdn.net/dn/bHW2YF/btqKRO16Oln/UrvZZeMCO20q9xY0XKuKSK/img.png)

1. Master 노드에 쓰기 트랜잭션이 수행된다.
2. Master 노드는 데이터를 저장하고 트랜잭션에 대한 로그를 파일에 기록한다.(BIN LOG)
3. Slave 노드의 IO Thread는 Master 노드의 로그 파일(BIN LOG)를 파일(Replay Log)에 복사한다.
4. Slave 노드의 SQL Thread는 파일(Replay Log)를 한 줄씩 읽으며 데이터를 저장한다.

#### 장점

- DB 요청의 60~80% 정도가 읽기 작업이기 때문에 Replication만으로도 충분히 성능을 높일 수 있다.
- 비동기 방식으로 운영되어 지연 시간이 거의 없다.

#### 단점

- 노드들 간의 데이터 동기화가 보장되지 않아 일관성있는 데이터를 얻지 못할 수 있다.
- Master 노드가 다운되면 복구 및 대처가 까다롭다.





### Sharding

Horizontal Partitioning -> 같은 테이블 스키마를 가진 데이터를 다수의 데이터베이스에 분산하여 저장하는 방법

하나의 DB에 데이터가 늘어나면 용량 이슈도 생기고, 느려지는 CRUD는 자연스레 서비스 성능에 영향을 주게 됨. DB 트래픽을 분산할 목적 -> 샤딩

application level에서도 가능하지만 database level에서도 가능



#### Sharding VS Partitioning

공통점 : 둘 다 더 작은 하위 집합으로 분할하는 것. 

**샤딩**은 데이터가 여러 **컴퓨터에 분산**되어 있지만 **파티셔닝**은 **분산되어 있지 않다는 것**

Partitioning: 단일 데이터베이스 인스턴스 내에서 데이터의 하위 집합을 그룹화하는 것.

horizontal sharding = horizontal partitioning 은 동일하게 사용하기도 함.



#### 장점

DB를 분산하면 특정 DB의 장애가 전면장애로 이어지지 않음



#### Modular sharding 

PK를 모듈러 연산한 결과로 DB를 특정하는 방식

<img src="https://techblog.woowahan.com/wp-content/uploads/img/2020-07-06/thiiing-db-modular-sharding.png" alt="properties" style="zoom:50%;" />

- 장점 : 레인지샤딩에 비해 데이터가 균일하게 분산
- 단점 : DB를 추가 증설하는 과정에서 이미 적재된 데이터의 재정렬이 필요



#### Range sharding

PK의 범위를 기준으로 DB를 특정하는 방식

<img src="https://techblog.woowahan.com/wp-content/uploads/img/2020-07-06/thiiing-db-range-sharding.png" alt="properties" style="zoom:50%;" />

- 장점 : 모듈러샤딩에 비해 기본적으로 증설에 재정렬 비용이 들지 않음
- 단점 : 일부 DB에 데이터가 몰릴 수 있음





### 인덱스

데이터베이스에서 인덱스를 사용하는 이유는 검색성능을 향상시키기 위함

일반적인 경우의 장점으로는 **빠른 검색 성능**을 들 수 있고, 단점으로는 인덱스를 구성하는 비용 즉, 추가, 수정, 삭제 연산시에 **인덱스를 형성하기 위한 추가적인 연산**이 수행.

따라서, 인덱스를 생성할 때에는 **트레이드 오프 관계에 놓여있는 요소들을 종합적으로 고려하여 생성**해야 함.



### 트랜잭션

트랜잭션이란 데이터베이스의 상태를 변화시키는 하나의 논리적인 작업 단위

트랜잭션에는 여러개의 연산이 수행될 수 있음. 트랜잭션은 수행중에 한 작업이라도 실패하면 전부 실패하고, 모두 성공해야 성공



#### ACID

트랜잭션이 안전하게 수행된다는 것을 보장하기 위한 성질

**Atomicity**(원자성): 트랜잭션의 연산은 모든 연산이 완벽히 수행되어야 하며, 한 연산이라도 실패하면 트랜잭션은 실패해야 함.

**Consistency**(일관성): 트랜잭션은 유효한 상태로만 변경될 수 있음.

**Isolation**(고립성): 트랜잭션은 동시에 실행될 경우 다른 트랜잭션에 의해 영향을 받지 않고 독립적으로 실행.

**Durability**(내구성): 트랜잭션이 커밋된 이후에는 시스템 오류가 발생하더라도 커밋된 상태로 유지되는 것을 보장. (일반적으로 비휘발성 메모리에 데이터가 저장되는 것을 의미)



#### 트랜잭션 격리 수준(Transaction Isolation Levels)

트랜잭션 격리수준은 고립도와 성능의 트레이드 오프를 조절

**READ UNCOMMITTED**: 다른 트랜잭션에서 커밋되지 않은 내용도 참조할 수 있음.

**READ COMMITTED**: 다른 트랜잭션에서 **커밋된 내용만** 참조할 수 있음. (SQL server, Oracle default)

**REPEATABLE READ**: 트랜잭션에 **진입하기 이전에 커밋된 내용**만 참조할 수 있음. (MySQL, MariaDB default)

**SERIALIZABLE**: 트랜잭션에 진입하면 락을 걸어 **다른 트랜잭션이 접근하지 못하게 함**. (성능 매우 떨어짐)



### 정규화

**정규화**: 데이터의 중복방지, 무결성을 충족시키기 위해 데이터베이스를 설계하는 것을 의미





### JOIN

![img](https://t1.daumcdn.net/cfile/tistory/99473C435C0D1ECD07)



+ 교차 조인

  ![img](https://t1.daumcdn.net/cfile/tistory/99FE21365C0D1ECE14)



클러스터드 인덱스 VS 언클러스터드 인덱스









