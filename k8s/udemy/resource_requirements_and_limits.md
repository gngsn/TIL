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
