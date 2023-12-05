## Worker Node
The Worker nodes are responsible for running containerized applications.
The worker Node has the following components.

- kubelet
- kube-proxy
- Container runtime

Kubernetes Worker Node Components
이제 각각의 워커 노드 구성요소를 살펴보도록 하겠습니다.


1. Kubelet

- Kubelet은 클러스터의 모든 노드에서 실행되는 에이전트 컴포넌트
- 컨테이너로 실행되지 않고, systemd에 의해 관리되는 데몬으로 실행
- API 서버에 워커 노드를 등록하고 주로 API 서버에서 podSpec(팟 사양 – YAML 또는 JSON)으로 작업하는 역할
    - podSpec: pod 내부에서 실행되어야 하는 컨테이너와 해당 컨테이너의 리소스(예: CPU 및 메모리 제한)나 환경 변수, 볼륨, 레이블 등을 정의
    - 정의 후 컨테이너를 생성하여 podSpec을 **원하는 상태** desired state 로 조정

### 역할

- Pod의 컨테이너 생성, 수정 및 삭제
- liveliness, readiness, startup 프로브 처리
- Pod 구성을 읽고 볼륨 마운팅을 담당하고 볼륨 마운트를 위한 호스트에 각각의 디렉토리 생성
- `CAdvisor` 및 `CRI` 같은 구현체로 API 서버 호출을 통해 노드 및 Pod 상태를 수집 및 보고

Kubelet은 Pod 변경을 지켜보고, 노드의 컨테이너 런타임을 활용하여 이미지를 pull 받거나, 컨테이너를 실행하는 등의 작업을 수행하는 컨트롤러이기도 함

- API 서버의 PodSpec 외에 kubelet은 파일, HTTP endpoint, HTTP 서버의 podSpec 수용 가능

파일과 관련된 podSpec의 좋은 예가 바로 Kubernetes static pods.
정적 Pod 들은 API 서버가 아닌 Kubelet에 의해 관리됨.
Kubelet 컴포넌트에 Pod YAML 위치를 제공하면 Pod를 생성할 수 있음을 의미.
하지만, Kubelet이 생성하는 정적 Pod는 API 서버에서 관리하지 않음

실제 정적 Pod 예시를 확인해보면,
컨트롤 플레인을 부트스트래핑하는 동안 kubelet은 `/etc/kubernetes/manifest`에 있는 podSpecs에서 api-server, scheduler 및 controller Manager를 정적 포드로 시작.

Kubelet의 몇 가지 주요 사항을 확인해보면,

- Kubelet은 container runtime interface(CRI) gRPC 인터페이스를 사용하여 컨테이너 런타임과 통신
- 또한 HTTP 엔드포인트를 노출하여 로그를 스트리밍하고 클라이언트에 대한 실행 세션 exec session 을 제공
- CSI(Container Storage Interface) gRPC를 사용하여 블록 볼륨을 구성
- 클러스터에 구성된 CNI 플러그인을 사용하여 Pod IP 주소를 할당하고 포드에 필요한 네트워크 경로 및 방화벽 규칙을 설정


## 2. Kube proxy

Kube 프록시를 이해하려면 Kubernetes Service & Endpoint 개체에 대한 기본 지식이 있어야 합니다.

Kubernetes Service: 
- 일련의 포드들을 내부적으로 혹은 외부 트래픽에 노출시키는 방법 
- 서비스 객체가 생성될 때, 해당 객체는 가상 IP 인 **ClusterIP**를 할당 받음
  - ClusterIP는 Kubernetes 클러스터 내에서만 접근 가능

- Endpoint 객체: Service 객체 아래에 모든 IP 주소들과 pod 그룹의 포트들을 포함  
- Endpoint Controller: Pod IP 주소 (endpoint) 목록 유지 관리 역할
- Service Controller: Endpoint를 서비스에 구성하는 역할

Pod IP의 경우에는 ping이 가능하지만,
ClusterIP는 Service Discovery에만 사용되기 때문에 ping할 수 없음


### 그렇다면, Kube Proxy 란?

- Kube-proxy는 데몬셋으로 모든 노드에서 실행되는 데몬
- Pod에 대한 Kubernetes Services 개념을 구현하는 프록시 구성 요소 (로드 밸런싱 되는 Pod 세트의 단일 DNS). 
- 주로 UDP, TCP, SCTP를 프록시 함 (HTTP 인식 불가)

- Service (Cluster IP)를 통해 Pod를 노출시킬 때, Kube-proxy는 네트워크 규칙 생성
  - 네트워크 규칙: Service 객체 아래에 그룹화된 백엔드 Pod(Endpoint)으로 트래픽을 전송
- 즉, 모든 로드 밸런싱 및 서비스 검색은 Kube 프록시에서 처리 됨

### Kube-proxy 작동 원리?

Kube 프록시는 API 서버와 대화하여 서비스(ClusterIP)와 각 포드 IP 및 포트(endpoint)에 대한 세부 정보를 얻고 서비스 및 엔드포인트의 변경을 모니터링합니다.

그런 다음 Kube-proxy는 다음 모드 중 하나를 사용하여 트래픽을 서비스 뒤의 포드로 라우팅하기 위한 규칙을 생성/업데이트합니다