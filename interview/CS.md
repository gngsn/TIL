# CS


## REST vs RPC

### RPC (Remote Procedure Call) ?
RPC(Remote Procedure Call)란 프로세스간 통신을 위해 사용하는 IPC(Inter Process Communication) 방법의 한 종류. 원격지의 프로세스에 접근하여 프로시저 또는 함수를 호출하여 사용. 말 그대로 원격지의 프로시저를 호출하는 것.

일반적으로는 프로세스는 자신의 주소공간 안에 존재하는 함수만 호출하여 실행 가능하다. 하지만, RPC를 이용하면 다른 주소공간에서 동작하는 프로세스의 함수를 실행할 수 있게 된다. 위에도 언급했듯, 분산 컴퓨팅 환경에서 프로세스 간 상호 통신 및 컴퓨팅 자원의 효율적인 사용을 위해서 발전된 기술.

#### 안쓰이는 이유

로컬에서 제공하는 빠른 속도, 가용성 등을 분산 프로그래밍에서도 제공하고 있다고 홍보를 했지만, 정작 구현의 어려움/지원 기능의 한계 등으로 제대로 활용되지 못함 -> REST의 등장

<img src="https://media.vlpt.us/images/jakeseo_me/post/16327fcc-4da1-4a4b-8dbc-b5b84a933900/image.png" alt="img" style="zoom:45%;" />

#### RPC의 목표

- 클라이언트-서버 간의 커뮤니케이션에 필요한 상세정보는 최대한 감춘다.
- 클라이언트는 일반 메소드를 호출하는 것처럼 원격지의 프로지서를 호출할 수 있다.
- 서버도 마찬가지로 일반 메소드를 다루는 것처럼 원격 메소드를 다룰 수 있다.



###  REST (REpresentational State Transfer)

**URI를 통해 모든 자원(Resource)을 명시하고 HTTP Method를 통해 CRUD Operation을 처리하는 아키텍쳐.**

자원 그 자체를 표현하기에 직관적이고, HTTP를 그대로 계승하였기에 별도 작업 없이도 쉽게 사용할 수 있다는 장점으로 현대에 매우 보편화. 

사람이 읽을 수 있는 API. HTTP를 사용하기 때문에 HTTP의 특성을 그대로 반영. 또한 별도의 인프라 구축이 필요없음.

#### 단점

- REST는 일종의 스타일이지 **표준이 아님** -> parameter와 응답 값이 명시적이지 않음. 
- HTTP 메소드의 형태가 제한적이기 때문에 **세부 기능 구현에는 제약**.

- 복잡하고 비효율적인 데이터 구조로 **속도가 느림**
  - 웹 데이터 전달 format으로 xml, json을 많이 사용. XML은 html과 같이 tag 기반이지만 미리 정의된 태그가 없어(no pre-defined tags) 높은 확장성을 인정 받아 이기종간 데이터 전송의 표준이었으나, 다소 복잡하고 비효율적인 데이터 구조탓에 속도가 느리다는 단점



### gRPC (google Remote Procedure Call)

google 사에서 개발한 오픈소스 RPC(Remote Procedure Call) 프레임워크. 이전까지는 RPC 기능은 지원하지 않고, 메세지(JSON 등)을 Serialize할 수 있는 프레임워크인 PB(Protocol Buffer, 프로토콜 버퍼)만을 제공해왔는데, **PB 기반 Serizlaizer에 HTTP/2를 결합하여 RPC 프레임워크를 탄생**시킨 것.

-> 핵심 : HTTP/2를 사용 + PB사용



### ProtoBuf (Protocol Buffer, 프로토콜 버퍼)

Protocol Buffer는 google 사에서 개발한 구조화된 데이터를 직렬화(Serialization)하는 기법.

직렬화란, 데이터 표현을 바이트 단위로 변환하는 작업을 의미합니다. 아래 예제처럼 같은 정보를 저장해도 text 기반인 json인 경우 82 byte가 소요되는데 반해, 직렬화 된 protocol buffer는 필드 번호, 필드 유형 등을 1byte로 받아서 식별하고, 주어진 length 만큼만 읽도록 하여 단지 33 byte만 필요하게 됩니다.

![img](https://miro.medium.com/max/1400/0*EqWBu3VDbav3svJk)



가벼워서 더 빠르게 전달할 수 있고 처리할 수 있으며 별도의 파싱없이 사용할 수 없어서 더 빠르다는 장점이 있습니다.

하지만, 프로토콜 버퍼에서 쓰이는 스키마 파일 즉, .proto 파일을 작성할 줄 알아야하며 정의된 proto파일은 수정을 포함하여 지속적으로 공유되어야 합니다.



[Naver Cloude Platform - 시대의 흐름, gRPC 깊게 파고들기](https://medium.com/naver-cloud-platform/nbp-%EA%B8%B0%EC%88%A0-%EA%B2%BD%ED%97%98-%EC%8B%9C%EB%8C%80%EC%9D%98-%ED%9D%90%EB%A6%84-grpc-%EA%B9%8A%EA%B2%8C-%ED%8C%8C%EA%B3%A0%EB%93%A4%EA%B8%B0-1-39e97cb3460)





## Fault tolerance

Fault-tolerant(무정지) 시스템으로 가기 위해 필요한 방법에 대한 생각을 말해주세요.





### 메세지 큐(Message Queue)란?

메세지 큐(Message Queue)란 Queue 자료구조를 이용하여 데이터(메세지)를 관리하는 시스템으로, 비동기 통신 프로토콜을 제공하여 메세지를 빠르게 주고 받을 수 있게 해준다. 메세지 큐에서는 Producer(생산자)가 Message를 Queue에 넣어두면, Consumer가 Message를 가져와 처리하게 된다. 메세지 큐에는 Kafka, Rabbit MQ, AMPQ 등이 있다.

 

### Docker(도커)와 Kubernates(쿠버네티스)

Docker는 컨테이너 기반의 가상화 기술입니다. 기존에는 OS를 가상화하였기 때문에 Host OS 위에 Guest OS를 설치해야 했습니다. 하지만 이러한 방식은 상당히 무겁고 느려 한계가 많이 있었습니다.

그래서 이를 극복하고자 프로세스를 격리시킨 컨테이너를 통해 가상화를 하는 Docker(도커)와 같은 기술들이 등장하게 되었고, 도커를 통해 구동되는 컨테이너를 관리하기 위한 Kubernates(쿠버네티스)가 등장하게 되었습니다.

 

