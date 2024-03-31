## Static Pods


kubelet은 kube-apiserver 에 의존해, 해당 kubelet가 위치한 노드에 어떤 Pod를 배치할지 지시를 받음

kube-scheduler 판단에 결정되고, 이후 데이터 저장소에 저장됨

### What If

만약, Master가 존재하지 않고 (kube-apiserver, kube-scheduler, controllers, etcd cluster, etc) 다른 노드가 없다면?

하나의 Node가 독립적으로 실행될 수 있을까?

그렇다면, 누가 Pod 생성 지시를 내릴 수 있을까?

kubelet은 노드를 독립적으로 관리할 수 있음

_즉, 하나의 Node가 독립적으로 실행될 수 있을까? → Yes_

하지만, **Pod 상세 정보**를 제공해줄 API 서버가 없음

kube-apiserver 없이 Pod definition 파일을 전달하는 방법은, 

**kubelet 이 Pod 상세 정보를 파일을 읽어오게 설정할 수 있음**

_**즉, Pod에 관한 정보를 저장하는 서버 디렉터리에 관리하고,
kubelet을 설정하면 해당 Pod 정의 파일을 읽을 수 있음**_

<br/><img src="./img/staticpods_img1.png" width="60%" /><br/>

kubelet은 주기적으로 디렉터리 하위의 파일을 읽고 Pod를 생성

뿐만 아니라 Pod가 죽지 않게 보장

앱이 고장 나면 kubelet가 재시작 시도

디렉터리 내 파일을 조금이라도 변경되면 kubelet이 Pod를 재생성하여 변경이 적용됨

파일을 제거하면 Pod가 자동으로 삭제됨


API 서버의 간섭이나 쿠버네티스 클러스터 구성 요소의 간섭 없이,
kubelet이 스스로 만든 Pod를 **정적 Pod**라고 함

kubelet은 오직 Pod에 대한 내용 밖에 모르기 때문에,
ReplicaSet, Deployment, Service를 통해 배치될 수 없고,
지정된 디렉토리에 정의 파일을 배치하는 방식으로만 Pod를 만들 수 있음

<br/>

### Designated Directory

지정된 폴더는 무엇이고 어떻게 구성할까요?

1. Specifying Configuration Option to `kubelet.service` file 

호스트의 어떤 디렉터리든 될 수 있고,
디렉터리 위치는 서비스를 실행하는 동안 kubelet에 옵션으로 전달됨

옵션은 `pod-manifest-path` 값으로 입력할 수 있고,
현재 `etc/kubernetes/manifests` 폴더로 설정돼 있는 것을 확인할 수 있음

<br/>

2. Specifying Configuration File to `kubelet.service` file

`kubelet.service`에 설정 파일 제공: `--config=kubeconfig.yaml`

_kubeconfig.yaml_

```yaml
staticPodPath: /etc/kubernetes/manifest
```

<pre>
정적 Pod 생성을 원할 땐, 가장 먼저 kubelet 옵션을 살펴볼 필요가 있음
option `pod-manifest-path` 확인 후, 정적 Pod 정의 파일을 어디에 둘지 알 수 있음
</pre>

정적 Pod 들이 생성되고 나면, `docker ps` 명령어를 통해 확인할 수 있음

<pre>
다른 런타임에서는 아래 명령어 실행
- cri-o: crictl ps
- containerd: nerdctl ps
</pre>

---

그럼, 노드가 클러스터의 일부로, Pod 생성을 요청하는 API 서버가 있을 때는?

kubelet은 두 종류의 Pod를 동시에 만들 수 있음: 정적 Pod과 API 서버로 부터 요청된 Pod

API 서버는 정적 파일로 생성된 Pod의 존재를 알고 있음 

`kubectl get pods` 명령어를 입력하면, 정적 Pod가 출력되는 것을 통해 확인 가능

클러스터의 일부일 때, kubelet이 정적 Pod를 만들 때 `kube-apiserver` 에도 Mirror 객체를 생성함

Mirror 객체는 readonly 객체로, 읽을 수는 있지만 수정하거나 삭제 불가능

수정/삭제하는 방법은 오직 노드 manifest 폴더에서 파일을 수정/삭제해야만 반영

<br/>

### Use Case

정적 Pod를 이용해 Control Plane의 구성 요소, 그 자체를 노드에 있는 Pod으로 배포할 수 있음

<br/><img src="./img/staticpods_img2.png" width="60%" /><br/>

모든 마스터 노드에 kubelet을 설치해서 시작할 때, Control Plane 의 Pod 정의 파일을 생성

<br/><img src="./img/staticpods_img3.png" width="60%" /><br/>

가령, kube-apiserver나 controller, etcd server 등 정의 파일을 지정된 manifest folder에 저장

그럼 kubelet이 파일을 읽어 Control Plane 컴포넌트들을 노드의 Pod과 동일하게 컨트롤

바이너리를 다운로드하거나 서비스를 구성할 필요가 없고, 
서비스가 다운될까 걱정할 필요도 없음

한 Pod라도 다운되면, 정적 Pod이기 때문에, kubelet이 자동으로 재시작 함

_kube-system 네임스페이스에 Pod를 확인하면, 정적 Pod이기 때문에, 컨트롤 플레인 구성 요소를 Pod로 볼 수 있음_

---

#### Static PODs vs DaemonSets

<table>
<tr>
<th></th>
<th>Static PODs</th>
<th>DaemonSets</th>
</tr>
<tr>
<th>Created By</th>
<td>Kubelet</td>
<td>Kube-API server (DaemonSet Controller)</td>
</tr>
<tr>
<th>Use Cases</th>
<td>Control Plane components</td>
<td>노드 내 Monitoring Agents, Logging Agents 등</td>
</tr>
<tr>
<th>Common Thing</th>
<td colspan="2">Kube-Scheduler 에 영향을 받지 않음</td>
</tr>
</table>

