### Worker Node
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

