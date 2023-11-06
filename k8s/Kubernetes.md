쿠버네티스

[🔗 Terms](https://kubernetes.io/ko/docs/reference/glossary/?all=true#term-control-plane)


쿠버네티스는 컨테이너화된 워크로드와 서비스를 관리하기 위한 이식성이 있고, 확장가능한 오픈소스 플랫폼이다. 쿠버네티스는 선언적 구성과 자동화를 모두 용이하게 해준다. 쿠버네티스는 크고, 빠르게 성장하는 생태계를 가지고 있다. 쿠버네티스 서비스, 기술 지원 및 도구는 어디서나 쉽게 이용할 수 있다.

쿠버네티스란 명칭은 키잡이(helmsman)나 파일럿을 뜻하는 그리스어에서 유래했다. K8s라는 표기는 "K"와 "s"와 그 사이에 있는 8글자를 나타내는 약식 표기이다. 구글이 2014년에 쿠버네티스 프로젝트를 오픈소스화했다. 쿠버네티스는 프로덕션 워크로드를 대규모로 운영하는 15년 이상의 구글 경험과 커뮤니티의 최고의 아이디어와 적용 사례가 결합되어 있다.



### Kubernetes Components

쿠버네티스를 배포하면 클러스터를 얻는다.

✔️ 쿠버네티스 클러스터: 컨테이너화된 애플리케이션을 실행하는 노드라고 하는 워커 머신의 집합.
모든 클러스터는 최소 한 개의 워커 노드를 가진다.

✔️ 워커 노드:
애플리케이션의 구성요소인 파드를 호스트.

✔️ 컨트롤 플레인:
워커 노드와 클러스터 내 파드를 관리.

프로덕션 환경에서는 일반적으로 컨트롤 플레인이 여러 컴퓨터에 걸쳐 실행되고,
클러스터는 일반적으로 여러 노드를 실행하므로 내결함성과 고가용성이 제공.

<br/><img src="https://kubernetes.io/images/docs/components-of-kubernetes.svg" alt="components-of-kubernetes" width="60%" /><br/>

<br/>


| 리소스                      | 용도                                         |
|--------------------------|--------------------------------------------|
| Node                     | 컨테이너가 배치되는 서버                              |
| Namespace                | 쿠버네티스 클러스터 안의 가상 클러스터                      |
| Pod                      | 컨테이너의 집합 중 가장 작은 단위, 컨테이너의 실행 방법 정의        |
| Replica Set              | 같은 스펙을 갖는 파드를 여러 개 생성하고 관리하는 역할            |
| Deployment               | 레플리카 세트의 리비전을 관리                           |
| Service                  | 파드의 집합에 접근하기 위한 경로 정의                      |
| Ingress                  | 서비스를 쿠버네티스 클러스터 외부로 노출                     |
| Config Map               | 설정 정보를 정의하고 파드에 전달                         |
| Persistence Volume       | 파드가 사용할 스토리지의 크기 및 종류 정의                   |
| Persistence Volume Claim | Persistence Volume 을 동적으로 확보               |
| Storage Class            | Persistence Volume 이 확보하는 스토리지의 종류를 정의     |
| Stateful Set             | 같은 스펙으로 모두 동일한 파드를 여러 개 생성하고 관리            |
| Job                      | 상주 실행을 목적으로 하지 않는 파드를 여러 개 생성하고 정상적인 종료 보장 |
| Cron Job                 | 크론 문법으로 스케줄링되는 Job                         |
| Secret                   | 인증 정보 같은 기밀 데이터 정의                         |
| Role                     | 네임스페이스 안에서 조작 가능한 쿠버네티스 리소스의 규칙 정의         |
| Role Binding             | 쿠버네티스 리소스 사용자와 Role 을 연결 지음                |
| Cluster Role             | 클러스터 전체적으로 조작 가능한 쿠버네티스 리소스의 규칙 정의         |
| Cluster Role Binding     | 쿠버네티스 리소스 사용자와 클러스터 Role 을 연결 지음           |
| Service Account          | 파드가 쿠버네티스 리소스를 조작할 때 사용하는 계정               |



### 컨트롤 플레인 컴포넌트
- 클러스터에 관한 전반적인 결정 수행
    - ex, 스케줄링
- 클러스터 이벤트를 감지하고 반응
    - ex, 디플로이먼트의 replicas 필드에 대한 요구 조건이 충족되지 않을 경우 새로운 파드를 구동시키는 것

컨트롤 플레인 컴포넌트는
- 클러스터 내 어떠한 머신에서든지 동작 가능
- 하지만 간결성을 위해, 보통 동일 머신 상 모든 컨트롤 플레인 컴포넌트에 구성 스크립트를 구동시키고, 사용자 컨테이너는 해당 머신 상에 동작시키지 않음
- 여러 머신에서 실행되는 컨트롤 플레인 설정의 예제를 보려면 kubeadm을 사용하여 고가용성 클러스터 만들기를 확인해본다.

<pre>
fyi. Using kubeadm, 
you can create a minimum viable Kubernetes cluster that conforms to best practices. 
In fact, you can use kubeadm to set up a cluster that will pass the Kubernetes Conformance tests.
kubeadm also supports other cluster lifecycle functions, such as bootstrap tokens and cluster upgrades.

The kubeadm tool is good if you need:
- A simple way for you to try out Kubernetes, possibly for the first time.
- A way for existing users to automate setting up a cluster and test their application.
- A building block in other ecosystem and/or installer tools with a larger scope.
</pre>

---

- Control Plane (Master 구성) Component

| Component               | Role                                                    |
|-------------------------|---------------------------------------------------------|
| kube-apiserver          | Kubernetes API를 노출하는 컴포넌트 kubectl 로부터 리소스를 조작하라는 지시를 받음 |
| etcd                    | 고가용성을 갖춘 분산 key-value 스토어, 쿠버네티스 클러스터의 백킹 스토어로 사용됨      |
| kube-scheduler          | 노드를 모니터링하고 컨테이너를 배치할 적절한 노드를 선택                         |
| kube-controller-manager | 리소스를 제어하는 컨트롤러를 실행                                      |

<br/>
