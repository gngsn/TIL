# Kubernetes Components

[🔗 Kubernetes Components](https://kubernetes.io/docs/concepts/overview/components/)

<br/>

Kubernetes를 배포하게 되면, cluster가 생성된다.
하나의 Kubernetes cluster는 node의 모음으로 구성되는데, 컨테이너화된 애플리케이션을 동작하는 worker machines이다.
모든 cluster는 적어도 하나의 worker node를 갖는다.
worker node는 앱 워크로드의 구성이 되는 Pod 들을 호스트한다.

해당 문서는 다양한 컴포넌트들을 훑어보는데, 이 개념들은 Kubernetes cluster를 만들어내고 동작시킬 때 알아둘 내용들이다.

<br/><img src="./img/components-of-kubernetes.svg" alt="components-of-kubernetes" width="80%" /><br/>

<br/>

## Control Plane Components

control plane의 컴포넌트는 클러스터에 대한 글로벌 설정(가령, 스케줄링) 뿐만 아니라 탐지(detecting)와 Cluster 이벤트에 대한 응답을 생성합니다.
(가령, deployment의 레플리카 필드가 조건을 충족하지 못했을 때 새로운 Pod을 시작함)

Control plane 컴포넌트들은 클러스터 내의 어떤 machine 위에서 실행될 수 있습니다.
하지만 편의를 위해, 보통 Set-Up 스크립트를 동일한 machine 위의 모든 Control plane component 위에서 실행시키며, 해당 machine 위의 사용자 컨테이너 위에서 실행시키지는 않습니다.

<small>fyi. [Creating Highly Available Clusters with kubeadm](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/high-availability/)</small>

<br/>

### kube-apiserver

https://kubernetes.io/docs/reference/command-line-tools-reference/kube-apiserver/

`kube-apiserver`는 Kubernetes API를 제공하는 Kubernetes control plane 의 컴포넌트입니다.
API server는 Kubernetes control plane의 프론트 사이드입니다.
Kubernetes API server의 주 구현체는 `kube-apiserver` 입니다.

`kube-apiserver`는 수평 확장이 가능하도록 설계되어졌고, 더 많은 인스턴스를 배포함으로서 스케일링할 수 있습니다.
몇몇의 `kube-apiserver` 인스턴스를 실행시킬 수 있는데, 해당 인스턴스들의 트래픽를 조정(balance)할 수 있습니다. 

<br/>

### etcd

모든 cluster 데이터를 위해 Kubernetes의 뒷 단(Back 단)에서 사용하는 지속적이고 가용성 높은 key-value 저장소입니다.
만약, Kubernetes cluster가 etcd를 뒷 단 저장소로 사용한다면, 반드시 back up 계획을 염두해두어야 한다.
더 많은 정보는 [etcd](https://etcd.io/docs/) 공식문서에서 찾아 볼 수 있습니다.

<br/>

### kube-scheduler

아직 노드에 할당되지 않은 새로 생성된 Pod를 확인하고, 해당 Pod이 실행될 노드를 선택하는 Control plane 컴포넌트입니다.
스케줄링을 결정하기 위한 고려되는 다음과 같은 요소들이 있습니다:

- 개별적이면서 모여져있는 자원 요구사항 (individual and collective resource requirements)
- hardware/software/policy 제약 사항 (hardware/software/policy constraints)
- 함께 동작해야 하거나 하지 않는(affinity and anti-affinity) 조건 명세 (affinity and anti-affinity specifications)
- 데이터 지역성 (data locality)
- 워크로드 간 인터페이스 (inter-workload interference)
- 데드라인 (deadlines)

<br/>

### kube-controller-manager
Control plane component that runs controller processes.

Logically, each controller is a separate process, but to reduce complexity, they are all compiled into a single binary and run in a single process.

There are many different types of controllers. Some examples of them are:

Node controller: Responsible for noticing and responding when nodes go down.
Job controller: Watches for Job objects that represent one-off tasks, then creates Pods to run those tasks to completion.
EndpointSlice controller: Populates EndpointSlice objects (to provide a link between Services and Pods).
ServiceAccount controller: Create default ServiceAccounts for new namespaces.
The above is not an exhaustive list.
























