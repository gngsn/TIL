# Operating System Upgrade

### TL;DR

**Commands**

**1. Drain**
: [kubectl cordon](https://kubernetes.io/docs/reference/kubectl/generated/kubectl_cordon/)
The given node will be marked unschedulable to prevent new pods from arriving.
'drain' evicts the pods if the API server supports [disruptions](https://kubernetes.io/docs/concepts/workloads/pods/disruptions/) eviction.

```Bash
kubectl drain [--ignore-daemonsets] [--grace-period] <node name>
```

**2. Cordon**
: [kubectl cordon](https://kubernetes.io/docs/reference/kubectl/generated/kubectl_cordon/). Mark node as unschedulable

```Bash
kubectl cordon <node name>
```

**3. Uncordon**
: [kubectl uncordon](https://kubernetes.io/docs/reference/kubectl/generated/kubectl_uncordon/). Mark node as schedulable

```Bash
kubectl uncordon <node name>
```

---

### Operating System Upgrade

**목표**: 쿠버네티스 운영 상, 소프트웨어 기반 업그레이드나 패치 적용, 보안 패치 등을 위해 어떻게 노드를 내릴 수 있을지

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

<br>

```
kube-controller-manager --pod-eviction-timeout=5m0s ...
```

<br>

컨트롤러 관리자에게 5분이라는 기본 값을 설정

노드가 오프라인이 되면, 
마스터 노드는 Eviction 시간을 고려해서 노드가 죽었다는 판단을 하기 전, 최대 5분까지 기다림

pod-eviction-timeout 이 지난 후, 노드가 다시 살아나면 어떤 Pod도 스케줄 되지 않는 비어있는 채로 생성

<br>

```
Master    ⚪️⚪️     🟠🟡     🟡🟢🟠
 Node    Node 1   Node 2   Node 3
```

<br>

🟠 Pod는 ReplicaSet로 관리되었기 때문에 다른 노드 (etc. Node 3)에 새 Pod를 생성

하지만 🔴 Pod는 ReplicaSet 하위에 있지 않기 때문에 그냥 사라짐

만약, 노드가 유지(maintenance) 관리 작업이 있으면서, 
해당 워크로드가 다른 Replica를 가진 노드에서 실행되는 걸 알고, 
해당 워크로드가 짧은 시간동안 다운되어도 괜찮으면서,
5분 안에 해당 노드가 다시 복구될 수 있다는 걸 보장할 수 있다면,
빠른 업데이트와 재부팅(reboot)가 가능

하지만 그 노드가 5분 후에 다시 복구된다는 확신할 수 없음

노드가 다시 돌아온다고 장담할 수가 없으니 더 안전한 방법을 사용할 수 있음

그래서 의도적으로 노드에 있는 모든 Pod가 다른 노드로 이동하게끔 `drain`(배출) 할 수 있음

(엄밀히 말하면 옮긴 게 아니지만)

한 노드를 `drain` 시키면 해당 노드에서 포드가 정상적으로 종료되고 다른 노드에 재생성됨

```Bash
kubectl drain node-1 
```

<br>

노드는 배치가 제한(`cordon`)되거나 스케줄 불가 상태(`unschedulable`)로 표시됨

<br>

> FYI.
> **cordon**: positioned around a particular area in order to prevent people from entering it. a line of police, soldiers, vehicles, etc

<br>

즉, 따로 설정한 제한을 없애지 않는 이상, 해당 노드에 Pod가 스케줄될 일은 없음

이후, Pod들은 다른 노드에 떠 있으니 해당 노드를 재부팅할 수 있음

해당 노드는 다시 복원되어도 여전히 Pod가 해당 노드에 스케줄링되지 않음

이후엔 `uncordon` (`cordon` 을 취소)해서 Pod 일정을 잡을 수 있도록 만들어야 함 


```Bash
kubectl uncordon node-1 
```

<br>

다른 노드로 옮겨진 포드가 자동으로 fall back 되진 않음

해당 Pod가 삭제되거나 클러스터에 새 Pod가 생성되면 해당 노드에 생성됨

drain 과 uncordon과 이외에도, `conrdon` 명령어가 있음

`conrdon` 은 단순히 스케줄 불가 상태로 체크

`drain` 과 달리 기존 노드에서 Pod를 종료하거나 이동시키지 않음

단순히 해당 노드에 새 포드가 스케쥴링되지 않도록 보장하는 것


