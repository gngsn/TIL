### Worker Node
The Worker nodes are responsible for running containerized applications.
The worker Node has the following components.

- kubelet
- kube-proxy
- Container runtime

Kubernetes Worker Node Components
이제 각각의 워커 노드 구성요소를 살펴보도록 하겠습니다.


#### kubelet

Kubelet은 클러스터의 모든 노드에서 실행되는 에이전트 구성 요소이다. Kubelet 는 컨테이너로 실행되지 않고 대신 systemd 에 의해 관리되는 데몬으로 실행된다.
API 서버에 워커 노드를 등록하고 주로 API 서버에서 podSpec(Pod 사양 – YAML 또는 JSON)으로 작업하는 역할을 한다. 
podSpec은 pod 내부에서 실행되어야 하는 컨테이너, 해당 리소스(예: CPU 및 메모리 제한), 환경 변수, 볼륨, 레이블 등 기타 설정을 정의한다.

그런 다음 컨테이너를 생성하여 podSpec을 원하는 상태로 만듭니다.
간단히 말하면, kubelet 는 다음과 같은 역할을 담당한다.

- Pod의 컨테이너 작성, 수정 및 삭제
- liveliness, readiness(준비 상태) 및 startup Probes를 처리하는 역할을 담당
- Pod 구성을 읽고 볼륨 마운트에 대한 각 디렉토리를 호스트에 생성하여 볼륨 마운트를 담당
- cAdvisor 및 CRI와 같은 구현으로 API 서버에 호출을 통해 Node 및 Pod 상태를 수집 및 보고

Kubelet은 또한 Pod 변경을 감시하고 노드의 컨테이너 런타임을 활용하여 이미지를 풀링하고 컨테이너를 실행하는 컨트롤러입니다.
Kubelet은 API 서버의 PodSpec 외에 파일, HTTP endpoint, HTTP 서버의 podSpec을 수용할 수 있다. "파일로 정의된 podSpec"의 좋은 예로 Kubernetes static pod를 들 수 있다.

정적 포드는 API 서버가 아닌 Kubelet에 의해 제어됩니다.
이는 Kubelet 컴포넌트에 Pod YAML 위치를 제공하여 Pod를 생성할 수 있음을 의미한다. 
그러나 Kubelet에서 생성된 정적 Pod는 API 서버에서 관리하지 않는다.