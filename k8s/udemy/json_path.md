# JSON path

쿠버네티스 프로덕션 환경으로 작업할 때,
Deployment, Pod, ReplicaSet, Service, Secret 등 수백 개의 노드와 수천 개의 개체에 대한 정보를 봐야 함

`Kubectl` utility 를 이용해 이 개체에 대한 정보를 봐야 함

종종 **모든 리소스**의 특정 필드를 보고 싶다는 요구 사항이 있을 수 있음 

수 천개의 리소스에서 해당 데이터를 조회하는 건 까다로움

→ 그래서 Kubectl 는 JSONpath 옵션을 지원

복잡한 조건을 Kubectl를 사용해 큰 데이터셋을 통한 데이터 필터링을 쉬운 작업으로 만드는 것

<br>

### How does Kubectl utility work?

kubectl 는 Kubernetes 의 객체를 읽고 쓰는 데 사용됨

Kubectl 명령 실행마다 Kube API 서버와의 통신을 통해 Kubernetes와 상호 작용함

kube-api-server 는 JSON 를 사용함

그래서 JSON 포맷으로 요청된 정보를 반환

JSON 포맷으로 정보를 받는 kubectl utility 는 사람이 읽기 쉽도록 테이블 형식으로 변환하여 출력

이 때, kube-api-server 에서 받아온 JSON 응답에는 많은 정보가 포함되어 있지만, 가독성을 위해 필요한 세부 사항만 출력

추가적인 세부 사항은 `Kubectl get` 명령과 함께 `-o wide` 옵션 사용

<br>

```
❯ kubectl get all -o wide -A
NAMESPACE            NAME                                             READY   STATUS             RESTARTS         AGE   IP           NODE                 NOMINATED NODE   READINESS GATES
kube-system          pod/coredns-76f75df574-l6tsh                     1/1     Running            2 (8d ago)       70d   10.244.0.4   kind-control-plane   <none>           <none>
kube-system          pod/coredns-76f75df574-s787j                     1/1     Running            2 (8d ago)       70d   10.244.0.2   kind-control-plane   <none>           <none>
kube-system          pod/weave-net-mvjxx                              1/2     CrashLoopBackOff   1858 (73s ago)   42d   172.21.0.2   kind-control-plane   <none>           <none>
...

NAMESPACE     NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)                  AGE   SELECTOR
default       service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP                  70d   <none>
kube-system   service/kube-dns     ClusterIP   10.96.0.10   <none>        53/UDP,53/TCP,9153/TCP   70d   k8s-app=kube-dns

NAMESPACE     NAME                        DESIRED   CURRENT   READY   UP-TO-DATE   AVAILABLE   NODE SELECTOR            AGE   CONTAINERS        IMAGES                                                     SELECTOR
kube-system   daemonset.apps/kindnet      1         1         1       1            1           kubernetes.io/os=linux   70d   kindnet-cni       docker.io/kindest/kindnetd:v20240202-8f1494ea              app=kindnet
kube-system   daemonset.apps/kube-proxy   1         1         1       1            1           kubernetes.io/os=linux   70d   kube-proxy        registry.k8s.io/kube-proxy:v1.29.2                         k8s-app=kube-proxy
...
```

<br>

가령, 특정 노드에서 사용 가능한 리소스 용량 노드에 설정된 테인트 조건이나 이미지 등, 
추가 내용을 출력하지만 다 보여주는 건 아님 

더 자세한 내용을 보려면 `kubectl describe` 를 통해 확인할 수 있음

하지만, 여러 노드에서는 불가능

이 때, JSON path 쿼리를 이용하면 명령의 출력을 필터링하고 포맷할 수 있음

<br>

### How to JSON path in kubectl

kubectl 의 JSON path 를 사용하기 위해서 아래 네 가지 단계가 필요

1. Identify the **kubectl** command
   - kubectl 명령어를 알아야 함
2. Familiarize with **JSON** output
   - `-o json` 옵션을 통해 JSON Format 출력을 확인
3. Form the **JSON Path** query
   - `.items[0].spec.containers[0].image`
   - `$` 표시가 필수는 아님 kubectl 이 추가해줌
4. Use the **JSON Path** query with **kubectl** command
   - `-o=jsonpath='{ ... }'` 옵션 사용해서 쿼리
   - `-o=jsonpath='{ .items[0].spec.containers[0].image }'`
   - 반드시 `'{ ... }'` 내에 Json Query를 작성해야 함

<br>

```Bash
❯ kubectl get nodes -o=jsonpath='{.items[*].metadata.name}' -A
kind-control-plane

❯ kubectl get nodes -o=jsonpath='{.items[*].status.nodeInfo.architecture}'
arm64

❯ kubectl get nodes -o=jsonpath='{.items[*].status.capacity.cpu}'
10
```

두 개의 쿼리를 한 번에 질의하고 싶다면 아래와 같이 붙여서 질의 하면 됨

```
❯ kubectl get nodes -o=jsonpath='{.items[*].metadata.name}{.items[*].status.capacity.cpu}'
kind-control-plane10
```

하지만 위 처럼 질의하면 보기가 힘듦

`{"\n"}` 혹은 `{"\t"}` 등을 사용해서 포맷을 설정할 수 있음

```
❯ kubectl get nodes -o=jsonpath='{.items[*].metadata.name}{"\n"}{.items[*].status.capacity.cpu}'
kind-control-plane
10
```

### Loops - Range

원하는 출력 방식은 아래와 같음

```Bash
# as is
master  node01
4       4

# to be
master  4
node01  4
```

이를 위해서 Loop 절이 필요

<table><tr>
<td><pre>FOR EACH NODE
    PRINT NODE NAME \t PRINT CPU COUNT \n 
END FOR</pre></td>
<td><pre>{range .items[*]}
    {.metadata.name}{"\t"}{.status.capacity.cpu}{"\n"} 
{end}</pre></td>
</tr>
<tr>
<td colspan="2">
<pre><code>❯ kubectl get nodes -o=jsonpath='{range .items[*]}{.metadata.name}{"\t"}{.status.capacity.cpu}{"\n"}{end}'</code></pre>
</td></tr></table>

<br>

### JSON path for Custom Column Names

혹은 Column 명을 지정할 수도 있음

```Bash
❯ kubectl get nodes -o=custom-columns=<COLUMN NAME>:<JSON PATH>
```

가령 예를 들면,

```Bash
❯ kubectl get nodes -o=jsonpath='{range .items[*]}{.metadata.name}{"\t"}{.status.capacity.cpu}{"\n"}{end}' -o=custom-columns=NODE:.metadata.name,CPU:.statis.capacity.cpu
NODE                 CPU
kind-control-plane   <none>
```

<br>

### JSON path for Sort

출력 시 특정 값을 기준으로 정렬을 할 수도 있는데, JSON Path 에서 명시했던 경로를 입력하면 됨

가령, Metadata 의 name 으로 정렬하고자 한다면 아래와 같이 작성할 수 있음

```Bash
❯ kubectl get nodes --sort-by=.metadata.name
NAME     STATUS   ROLES    AGE   VERSION
master   Ready    master   5m    v1.11.3
node01   Ready    <none>   5m    v1.11.3
```

혹은, CPU 의 capacity 으로 정렬하려면 아래와 같이 작성할 수 있음

```Bash
❯ kubectl get nodes --sort-by=.status.capacity.cpu
NAME     STATUS   ROLES    AGE   VERSION
master   Ready    master   5m    v1.11.3
node01   Ready    <none>   5m    v1.11.3
```
