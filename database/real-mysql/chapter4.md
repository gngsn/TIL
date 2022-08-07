## MySQL Engine Architecture

*p.77p*

<br/>

MySQL Server - MySQL engine + storage engine
사람과 비교하자면 - Brain(MySQL engine) + Hands, foot(Storage engine)

Goal 1. What is MySQL engine?

Goal 2. Default support storage engine: InnoDB, MyISAM storage engine


---


## 4.1 MySQL Engine Architecture

<img src="./MySQL_Architecture.png" alt="MySQL_Architecture" />

MySQL 고유의 C API나 JDBC, ODBC, .NET 등의 표준 드라이버 제공

모든 언어로 MySQL 서버에서 쿼리를 사용할 수 있게 지원

- MySQL Engine: 요청된 SQL 문장을 분석하거나 최적화하는 등 DBMS의 두뇌에 해당하는 처리를 수행
- Storage Engine: 실제 데이터를 디스크 스토리지에 저장하거나 디스크 스토리지로부터 데이터를 읽어옴

<br/>

### 4.1.1.1 MySQL Engine

MySQL 엔진은 **클라이언트로부터의 접속 및 쿼리 요청을 처리하는 커넥션 핸들러와 SQL 파서 및 전처리기, 쿼리의 최적화된 실행을 위한 옵티마이저로 (중심)구성**

MySQL은 표준 SQL(ANSI SQL) 문법을 지원하기 때문에 표준 문법에 따라 작성된 쿼리는 타 DBMS와 호환되어 실행할 수 있다.

<br/>

### 4.1.1.2 Storage Engine

MySQL 서버에서 MySQL 엔진은 하나지만 스토리지 엔진은 여러 개를 동시에 사용할 수 있다.

아래와 같이 스토리지 엔진을 지정하면 이후 해당 테이블의 모든 읽기 작업이나 변경 작업은 정의된 스토리지 엔진이 처리

``` sql
CREATE TABLE test_table (fd1 INT, td2 INT) ENGINE=INNODB;
```

test_table 테이블은 InnoDB 스토리지 엔진을 사용하도록 지정했다. 

test_table 테이블에 INSERT, UPDATE, DELETE, SELECT, ... 등의 작업이 발생하면 InnoDB 엔진이 처리한다. 그리고 각 스토리지 엔진은 성능 향상을 위해 키 캐시(MyISAM 스토리지 엔진)나 InnoDB 버퍼 풀(InnoDB 스토리지 엔진)과 같은 기능을 내장한다.

<br/>

### 4.1.1.3 Handler API

Handler 요청: MySQL 엔진의 쿼리 실행기에서 데이터를 쓰거나 읽어야 할 때, 각 스토리지 엔진에 쓰기 또는 읽기 시 요청. 

Handler API: Handler 요청에서 사용하는 API.

InnoDB 스토리지 엔진 또한 이 Handler API를 이용해 MySQL 엔진과 데이터를 주고받는다.

Handler API를 통해 얼마나 많은 데이터(레코드) 작업이 있었는지는 `SHOW GLOBAL STATUS LIKE 'Handler%';` 로 확인할 수 있다.

``` sql
mysql> show global status like 'Handler%';
+----------------------------+-----------+
| Variable_name              | Value     |
+----------------------------+-----------+
| Handler_commit             | 606730    |
| Handler_delete             | 5308      |
| Handler_discover           | 0         |
| Handler_external_lock      | 1010387   |
| Handler_mrr_init           | 0         |
| Handler_prepare            | 338212    |
| Handler_read_first         | 6827      |
| Handler_read_key           | 4125708   |
| Handler_read_last          | 27        |
| Handler_read_next          | 81047252  |
| Handler_read_prev          | 122826260 |
| Handler_read_rnd           | 830719    |
| Handler_read_rnd_next      | 357593844 |
| Handler_rollback           | 2566      |
| Handler_savepoint          | 0         |
| Handler_savepoint_rollback | 0         |
| Handler_update             | 2965355   |
| Handler_write              | 126536909 |
+----------------------------+-----------+
18 rows in set (0.46 sec)
```




