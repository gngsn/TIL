# Redis

대규모 서비스를 운영하는 업체는 항상 데이터의 안정적인 저장과 빠른 처리를 원하는데, 
이를 위해 NoSQL, Cache, Redis, Memcache, Sharding 등와 관련된 기술들이 필요하다.

레디스를 어떤 사람은 '캐시Cahce 솔루션'이라고 정의하고, 어떤 사람은 NoSQL의 Key-Value 스토어로 분류하기도 한다.
결국, 공통적인 특징은 **사용하기 쉽고 속도가 빠르다는 것**이다.

<br/><br/>

## Redis의 주요 특성

### ✔️ Key-Value Store

> 단순 스트링에 대한 Key/Value 구조를 지원

기본적으로 Redis는 Key-Value 형태의 데이터 저장소이며, 아래와 같이 데이터를 저장할 수 있다. 

``` bash
$> set id:username "username"
$> set id:email test@test.com
$> get id:username
```

<br/><br/>

### ✔️ 컬렉션 지원

> List, Set, Sorted Set, Hash 등의 자료 구조를 지원

Redis의 가장 중요한 특징이라고 할 수 있다. 
일반적으로 사용하는 컬렉션은 하나의 서버 내부에서 동작한다.
하지만, Redis를 이용하면 이런 특성을 분산 서버 환경에서 처리할 수 있어서, 전체적인 서비스를 설계하거나 구현할 때 많은 이점을 얻을 수 있다. 

<br/><br/>

### ✔️ Pub/Sub 지원

> Publish/Subscribe 모델을 지원

서버 간 통지가 필요할 때 매우 유용하다.

<br/><br/>

### ✔️ 디스크 저장 Persistent Layer

> 현재 메모리 상태를 디스크로 저장할 수 있는 기능
> 
> 현재까지의 업데이트 관련 명령을 저장할 수 있는 AOF 기능

현재의 메모리 상태를 디스크에 저장할 수 있다. 현재 메모리 상태의 스냅샷을 남지는 'RDB 기능'과 지금까지 실행된 업데이트 관련 명령어의 집합인 AOF가 있다.
덤프한 내용은 다시 메모리에 올려서 사용할 수 있다. AOF는 “Append Only File”의 약어로, set/del 등의 업데이트 관련 명령을 받으면 해당 명령어를 그대로 기록해둔다. Redis에서는 가능하면 이 두 개를 모두 사용 하는 것이 좋다고 이야기하지만, 디스크를 사용해서 저장하는 만큼 성능 손실은 어느 정도 감수해야 한다.

**⚠️ 주의**
단순히 이름만 ‘RDB’일 뿐 메모리 내용을 저장하는 기능 이외에는 아무것도 지원하지 않는다.
스냅샷을 남기는 기능의 이름이 ‘RDB’이다 보니 데이터베이스라고 생각해서 데이터베이스의 모든 기능 역시 지원되지 않을까 하지만 아니다.

<br/><br/>

### ✔️ 복제 Replication

> 마스터/슬레이브 구조를 지원

다른 노드에서 해당 내용을 복제할 수 있는 마스터/슬레이브의 Replication 지원한다. 마스터에 장애가 발생하면 슬레이브로 서비스하거나 마스터의 부하가 많을 때에는 슬레이브를 이용해서 읽기를 처리할 수도 있다. 
대규모 서비스에서 Redis를 저장소로 안정적으로 사용하려면 복제 기능응ㄹ 반드시 이용해야 한다. 

<br/><br/>

### ✔️ 빠른 속도

> 초당 100,000QPS(Queries Per Second) 수준의 높은 성능

위의 기능들을 모두 지원하면서도 아주 빠른 성능을 자랑한다. 
Redis를 선택하는 가장 큰 이유라고 할 수 있다. 
초당 50,000 ~ 60,000QPS 이상의 처리 속도가 필요하다면 Redis, Memcached의 선택지 밖에 남지 않을 것이다. 




## Redis 운영과 관리

### Redis 운영 핵심 : Single Thread

Redis를 사용하다 보면 의도하지 않은 장애가 발생하거나 성능이 예측한 대로 나오지 않는 경우가 종종 발생한다.

Redis는 싱글 스레드이기 때문에, **하나의 명령이 오랜 시간을 소모하는 작업에는 적합하지 않다**. 

시간이 오래 걸리는 Redis 명령을 호출하면, 명령을 처리하는 동안에는 Redis가 다른 클라이언트의 요청을 처리할 수 없다. 

일단 실무에서 흔히 하는 실수의 사례를 살펴보자. 

서버에서는 **keys** 명령을 사용하지 말자 Redis 명령어 중에, 현재 서버에 저장된 Key 목록을 볼 수 있는 keys 명령이 있다. 
이 명령을 사용하면 원하는 패턴에 맞는 명령들만 얻어올 수도 있다. 
keys 명령의 사용법은 다음과 같다(정규표현식의 간단한 형태를 지원한다). 

모든 Key를 가져올 때는 ‘ * ’를 사용한다.

``` bash
redis 127.0.0.1:6379> keys *
1) "gngsn:name"
2) "abcd"
3) "qwert"
4) "hi"
5) "gngsn:email"
6) "gngsn"
7) "sname"
```

"gngsn"이 포함된 모든 Key를 가져와보자.

``` bash
redis 127.0.0.1:6379> keys *gngsn*
1) "gngsn:name"
2) "abcd"
3) "qwert"
4) "hi"
5) "gngsn:email"
6) "gngsn"
7) "sname"
```

실제 실무에서 확인해보면 
데이터 양이 적을 대는 Redis가 굉장히 빠르다가, 데이터가 10만 개에서 20만개가 되면서는 속도가 느려지게 된다.



!! keys 를 꼭 사용하고 싶다면
Redis의 'list', 'set', 'sorted set'을 이용해서 "register_userlist_20220410"의 형태로 **키 값을 저장**해둘 수 있다.

👉🏻 장점

: keys 명령어를 사용하지 않는 해결책

👉🏻 단점

: "register_userlist_20220410", "register_userlist_20220411", "register_userlist_20220412"... 의 형태로 저장해야 함

: 너무 많은 데이터로 샤딩이 필요하면 "register_userlist_20220410_1", "register_userlist_20220410_2"의 형태로 관리하고 분할을 재고민해야한다.


<br/><br/>

### flushall/flushdb 명령 주의

Redis의 모든 데이터를 삭제하는 명령어.

Redis는 '0번 DB', '1번 DB',... 와 같이 DB에 가상 공간을 제공하고, `select` 명령으로 이동할 수 있다. 

이런 가상의 공간인 DB 하나를 통째로 지우는 것이 `flushdb`이고, 모든 DB의 내용을 모두 지울 수 있는 것이 `flushall`이다.

select명령을 따로 주지 않는다면, default는 0이다.


