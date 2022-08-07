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





