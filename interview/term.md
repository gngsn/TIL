## 알아둘 용어들

### Network

#### IPC (Inter Process Communication)

프로세스들은 기본적으로 상호독립적입니다. 메모리를 공유하지 않기 때문에 각자 자신의 일만 하며 서로 간섭을 하지 않아요. 하지만 필요에 따라 프로세스간 정보를 교환해야하는 경우가 있겠죠? 이때 별도 수단을이용하여 프로세스 통신하는 방법론을 통칭하여 IPC(Inter Process Communication) 라고 합니다.



#### HTTP/2

http/1.1은 기본적으로 클라이언트의 요청이 올때만 서버가 응답을 하는 구조로 매 요청마다 connection을 생성해야만 합니다. cookie 등 많은 메타 정보들을 저장하는 무거운 header가 요청마다 중복 전달되어 비효율적이고 느린 속도를 보여주었습니다. 이에 http/2에서는 한 connection으로 동시에 여러 개 메시지를 주고 받으며, header를 압축하여 중복 제거 후 전달하기에 version1에 비해 훨씬 효율적입니다. 또한, 필요 시 클라이언트 요청 없이도 서버가 리소스를 전달할 수도 있기 때문에 클라이언트 요청을 최소화 할 수 있습니다.

![img](https://miro.medium.com/max/542/0*RbY-0HC7iCxiAJCI)
