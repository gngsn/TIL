# Resource Requirements and Limits


모든 Pod를 운영하려면 자원이 필요하며,
모든 노드엔 사용 가능한 CPU와 메모리 리소스를 가지고 있음


쿠버네티스 스케줄러는 Pod에 요구되는 리소스 양을 고려하여, 
노드에서 사용 가능한 리소스에 따라 Pod가 배치될 노드를 결정

그리고 포드를 설치할 최적의 노드를 결정

노드에 충분한 리소스가 없으면, 스케줄러는 해당 노드에 Pod를 두는 걸 피하고 리소스가 충분히 사용 가능한 곳에 배치

모든 노드에 사용 가능한 리소스가 충분하지 않으면 스케줄러가 Pod의 스케줄링을 보류

Pod은 현재 Pending 상태 유지

kubectl Events를 보면 불충분한 CPU가 있는 걸 볼 수 있음

<br/><img src="./img/resource_requirements_and_limits_img1.jpg" width="40%" /><br/>

**Example.**

3개의 노드를 가진 쿠버네티스 클러스터를 가정해보자

```
🔋 CPU  /  🛢 Memory

Pod 1  🔋🔋🛢
Pod 2  🔋🛢🛢🛢
Pod 3  🔋🔋🔋🛢🛢🛢

Node1  [🔋🔋🔋🔋🔋🔋🛢🛢🛢🛢🛢🛢]
Node2  [🔋🔋🔋🔋🔋🔋🛢🛢🛢🛢🛢🛢]
Node3  [🔋🔋🔋🔋🔋🔋🛢🛢🛢🛢🛢🛢]
```

Pod 1은 CPU 2개와 메모리 1개 필요

스케줄러는 사용 가능한 리소스가 충분하기 때문에 2번 노드에 배치

<br/>

Pod 생성 시, 필요한 CPU와 메모리의 양을 지정할 수 있음

<br/>

_pod-definition.yml_

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: simple-webapp-color
  labels:
    name: simple-webapp-color
spec:
  containers:
  - name: simple-webapp-color
    image: simple-webapp-color
    port: 
      - containerPort: 8080
    resources:
      requests:
        memory: "4Gi"
		cpu: 2
```

예를 들어, 컨테이너에 대한 리소스 요청으로 CPU 2개와 4 Gi 메모리로 설정할 수 있음

스케줄러가 Pod를 노드에 놓으려 할 때 이 수치를 감안하여 사용 가능한 충분한 양의 리소스가 있는 노드에 배치

Pod는 사용 가능한 양의 자원을 보장받음 

---

`0.1`와 같이 수치를 지정해서 CPU 설정 가능한데, 참고로 동일한 수치로써 Milli를 의미하는 m과 함께 100m로 표현될 수 있음

`1m`가 최소

CPU 1 카운트 = AWS로 vCPU 한 대 = GCP나 Azure의 코어 하나  = 다른 시스템의 Hyperthread 하나


<br/><br/>

### Resource - Memory

작성할 수 있는 값:

```
1 G (Gigabyte) = 1,000,000,000 bytes
1 M (Megabyte) = 1,000,000 bytes
1 K (Kilobyte) = 1,000 bytes

1 Gi (Gigibyte) = 1,073,741,824 bytes
1 Mi (Mebibyte) = 1,048,576 bytes
1 Ki (Kibibyte) = 1,024 bytes
```

**노드에서 실행되는 컨테이너**

기본적으로, 컨테이너는 노드 리소스 사용에 한계가 없음

하지만, Pod에 리소스 제한 지정 가능

예를 들어 컨테이너에 vCPU를 하나로 제한하면, 컨테이너는 해당 노드에서 vCPU 한 대만 사용하도록 제한되며,
메모리도 동일하게 컨테이너에 512 mebibyte로 지정 가능

Resource Limits은 Pod 파일에서 지정 가능

_pod-definition.yml_

<br/><pre><code lang="yaml">
apiVersion: v1
kind: Pod
metadata:
  name: simple-webapp-color
  labels:
    name: simple-webapp-color
spec:
  containers:
  - name: simple-webapp-color
    image: simple-webapp-color
    port: 
      - containerPort: 8080
    resources:
      requests:
        memory: "4Gi"
		cpu: 2
      <b>limits:
        memory: "2Gi"
		cpu: 2</b>
</code></pre>

<br/>

메모리와 CPU에 대한 새로운 한도를 지정 후,
Pod가 만들어질 때 쿠버네티스는 컨테이너에 제한을 설정

```
⚠️ 이 때, Pod 내 **모든 컨테이너마다** 제한과 요청이 설정된 것 명심
컨테이너가 여러 개면 각 컨테이너는 개별적으로 요청이나 제한을 설정할 수 있음
```

<br/>

Pod가 지정된 제한을 초과하면?

**CPU**는 시스템이 CPU를 조절해 지정된 한도를 넘지 않음. 즉, 컨테이너가 한계점 이상으로 CPU 리소스를 쓸 수 없음

**Memory는 다름**

컨테이너는 한도보다 많은 메모리 리소스를 쓸 가능성이 있음

무리가 계속해서 한계보다 많은 메모리를 소모하면 그 무리는 종료 (terminated)

그래서, 로그나 실행하면 설명된 명령의 출력 결과에서 Pod가 OOM 에러로 종료되는 걸 확인할 수 있음

<br/>

#### Default Behavior

```
⭐️ 기본적으로 쿠버네테스는 CPU나 메모리 요청, 제한 세트가 없음
즉, 어떤 Pod든 어떤 노드에서 필요한 만큼의 자원을 소비할 수 있고,
해당 노드에서 실행 중인 다른 Pod나 프로세스를 질식(suffocate)시킬 수 있음
```
