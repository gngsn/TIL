# Operating System Upgrade

목표: 쿠버네티스 운영 상, 소프트웨어 기반 업그레이드나 패치 적용, 보안 패치 등을 위해 
어떻게 노드를 내릴 수 있을지

몇몇의 노드를 가진 클러스터를 가정해보자.

```
Master    🟠🔴     🟠🟡     🟡🟢
 Node    Node 1   Node 2   Node 3
```

만약, 노드 하나가 다운되면 어떻게 될까?


```
Master    ⚪️⚪️     🟠🟡     🟡🟢
 Node    Node 1   Node 2   Node 3
```

물론 해당 노드의 Pod는 접근 불가능하지만, Pod를 어떻게 배치하느냐에 따라 사용자가 영향을 받을 수 있음

가령, 🟠 Pod의 복제본이 있으니 🟠 Pod에 액세스하는 사용자는 영향을 받지 않음

해당 트래픽을 🟠을 통해 처리할 수 있음

하지만 Node 1이 🔴을 실행하는 유일한 Pod이기 때문에 🔴에 접속하는 사용자는 영향을 받음

만약, 노드가 바로 살아나면, kubelet 프로세스가 시작되고 Pod가 온라인으로 돌아옴 

하지만 노드가 5분 이상 다운되면, 쿠버네티스는 죽은 것으로 여기고 해당 노드에서 Pod를 종료

만약 Pod가 ReplicaSet에 의해 관리된다면, 해당 Pod는 다른 노드에 재생성됨

Pod가 복구되길 기다리는 시간은 pod-evition-timeout로 설정할 수 있음 

```
kube-controller-manager --pod-eviction-timeout=5m0s ...
```

컨트롤러 관리자에게 5분이라는 기본 값을 설정
