# Design a Kubernetes Cluster

클러스터를 생성하는 목적에 따라 다양한 환경을 가져갈 수 있음 

<br>

### 📌 Purpose

목적에 따라 환경을 고려해볼 수 있음

가령, 교육 / 테스트 & 개발 환경 / 프로덕션 애플리케이션 인지에 따라 달라질 수 있음

<br>

**✔️ Education purpose**

아래와 같은 가장 단순한 클러스터가 적절

- **Minikube** 
- **kubeadm / GCP / AWS 으로 생성하는 단일 클러스터**

<br>

**✔️ Development & Testing purpose**

- 단일 Master 와 여러 Worker 를 구성하는 멀티 노드 클러스터
- `kubeadm` 을 사용하거나 GCP / AWS / AKS 등을 통한 세팅 

<i><small> GCP: Google Cloud Platform / AWS: Amazon Web Service / AKS: Azure Kubernetes Service</small></i>

<br>

**✔️ Hosting Production Apps purpose**


- 고가용성 멀티 마스터 노드를 사용하는 멀티 노드 클러스터
- `kubeadm` 을 사용하거나 GCP / AWS / AKS 등을 통한 세팅
- 노드 5,000개, Pod 노드 당 100개로 총 150,000개, 컨테이너 300,000개 까지 가질 수 있음

클라우드 서비스는 클러스터 내의 노드 개수에 따라 적절한 사이즈의 노드를 자동으로 골라줌

<br>

### 📌 Cloud or On-Premise

하드웨어 인프라에 따라 고려해볼 수 있음

<br>

**✔️ Kubeadm for on-prem**

On-Premise 환경에서 kubeadm 툴은 굉장히 유용함

혹은 아래의 클라우드 서비스를 사용할 수 있음

**✔️ Google Kubernetes Engine (GKE) for GCP**
**✔️ Kubernetes Operations (KOps) for AWS**
**✔️ Azure Kubernetes Service (AKS) for Azure**

<br>

### 📌 Workload - Storage

워크 노드에 따른 스토리지를 고려해야 함

다양한 클래스의 스토리지를 정의하고 올바른 클래스를 올바른 애플리케이션에 할당하는 방법을 고려해야 함

**✔️ High Performance SSD Backed Storage**

높은 성능을 요구하는 워크로드라면 SSD 기반 스토리지를 고려할 수 있음

**✔️ Multiple Concurrent connections – Network based storage**

다중 Concurrent 액세스를 요구하는 경우, 네트워크 기반 스토리지를 고려할 수 있음

**✔️ Persistent shared volumes for shared access across multiple PODs**

여러 포드에서 한 볼륨에 액세스를 공유해야하는 경우, 스토리지 섹션에서 설명한 지속성 스토리지 볼륨을 고려할 수 있음

**✔️ Label nodes with specific disk types**

**✔️ Use Node Selectors to assign applications to nodes with specific disk types**

<br>

### 📌 Workload - Nodes

쿠버네티스 클러스터에서 형성되는 노드들은 물리적일 수도 있고 가상일 수도 있음

**✔️ virtual or Physical Machines**
**✔️ Minimum of 4 Node Cluster (Size based on workload)**
**✔️ Master vs Worker Nodes**
**✔️ Linux X86_64 Architecture**
**✔️ Master nodes can host workloads**
**✔️ Best practice is to not host workloads on Master nodes**


이제 마스터 노드는 CD 서버 등에서 QAPs 서버와 같은 제어 구성 요소를 호스팅하기 위한 것임을 알게 되었습니다.

작업자 노드는 워크로드를 호스팅하기 위한 것입니다.

그러나 이는 엄격한 요구 사항이 아니며 마스터 노드도 노드로 간주되며 워크로드를 호스팅할 수 있습니다.

최선의 방법으로, 특히 프로덕션 환경에서는 구성 요소를 제어하는 데만 마스터 노드를 전용으로 사용하는 것이 좋습니다.

kubeadm과 같은 배포 도구는 마스터 노드에 테인트를 추가함으로써 마스터 노드에서 워크로드를 호스팅하는 것을 방지함.

노드의 경우 64-bit Linux 운영 체제를 사용해야 함

또 다른 주목할 점은 일반적으로 마스터 노드에 모든 제어 평면 구성 요소가 있다는 것입니다.

그러나 큰 클러스터의 경우 etcd 클러스터를 분리하도록 선택할 수 있습니다

마스터 노드에서 자체 클러스터 노드로 이동합니다.

고가용성 설정에 대해 이야기할 때 다가오는 강의에서 이에 대한 다양한 사과에 대해 더 자세히 논의할 것입니다.

글쎄, 그것들은 쿠버네티스 클러스터를 설계하기 위한 몇 가지 고려 사항입니다.

자세한 내용과 몇 가지 흥미로운 읽기는 참조 섹션의 링크를 참조하십시오.

자, 이번 강의는 여기까지입니다.

글쎄요, 가기 전에 자격증 시험 입장에서 보면 이 섹션에서 정말 기억해야 할 것이 없습니다.

문서 페이지에서 사용할 수 있으므로 논의한 숫자를 외울 필요가 없습니다.

자, 이 섹션에서 앞으로 있을 강의에서 더 흥미로운 주제에 대해 알아보겠습니다,

여기서 우리는 실제 클러스터를 처음부터 처음부터 프로비저닝할 것입니다. 자, 이번 강의는 여기까지입니다.

다음 강의 때 뵙겠습니다.

### 📌 Workload - Master Nodes




---
