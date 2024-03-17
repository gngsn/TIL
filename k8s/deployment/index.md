# Deployment

<small> 

> **TOC**
<br/> Deployment?
<br/> Use Case
<br/> Deployment yaml
<br/> Deployment command

</small>

## Deployment?

> A Deployment provides declarative updates for Pods and ReplicaSets.

Deployment는 선언형 방식으로 Pod와 ReplicaSet를 업데이트할 수 있습니다.

Deployment에서 원하는 상태를 설명하면 Deployment Controller가 실제 상태를 원하는 상태로 제어된 속도로 변경합니다. Deployment를 정의하여 새 ReplicaSet을 생성하거나 기존 Deployment를 제거하고 모든 리소스를 새 Deployment로 채택할 수 있습니다.

쿠버네티스는 이전의 애플리케이션 배포 시 필요했던 조건 들을 만족하도록 구성되어 있습니다.
가령, 아래와 같은 조건을 고려하여 안정적인 서버 운영을 위한 배포 기능을 가집니다.

- 배포된 파드 업데이트
- 새로운 애플리케이션 릴리즈
- 무중단 배포

배포된 파드를 업데이트하거나 새로운 릴리즈를 배포할 때,
사용자의 트래픽이 막히지 않게 중단되지 않는 서버를 운영하는 것이 당연시 되었는데요.
쿠버네티스에서는 안정적인 배포 뿐만 아니라, 
배포 과정을 파일에 한 번 선언해두면 항상 동일한 방식으로 배포가 진행되도록 합니다. 

애플리케이션을 업데이트하고 싶을 때, 모든 Pod에 변경 사항을 쉽게 반영하기 위해서,
Pod 상위 계층의 ReplicaSet 에 변경 사항을 적용할 수 있습니다. 

> ReplicaSets 를 통해 안정적으로 파드 개수를 유지시키고, 
한 파드에 장애가 발생했을 때, 트래픽을 정상 동작하는 파드로 조정하여 안정적인 파드 운영을 도와줍니다.  

업데이트를 반영 시킬 때, 여러 개의 ReplicaSet 모두에 반영을 하려고 할 땐 어떨까요?

가령, 공통 모듈 내 3개의 애플리케이션에 해당하는, _ex. DB·서버·프론트_, ReplicaSet들을 하나씩 업데이트 하는 방법도 있을 텐데요.
문제는, 서버 다음 프론트를 업데이트 한다고 했을 때, 
서버가 v2인데 DB와 프론트는 아직 v1인 내용을 반영한다면 장애가 발생할 수도 있습니다. 

위 방식이 아닌, 공통 모듈의 **모든 구성요소가 한 번에 업데이트**되고,
**항상 동일한 조건의 배포를 통해 진행**되는 것을 선호할 것입니다.

ReplicaSet보다 더 높은 계층인 Deployment 하나를 배포하여 안정적인 업데이트를 진행할 수 있습니다.

---

## Use Case

실제로 Deployment 를 사용하는 예시를 알아봅시다.

✔️ ReplicaSet 배포
ReplicaSet는 내부에서(background) Pod를 생성하며, 배포 과정 상태를 체크할 수 있습니다.

✔️ Pod의 새로운 상태를 선언.
Deployment의 PodTemplateSpec를 업데이트해서 새로운 상태를 선언합니다.
새로운 ReplicaSet가 생성되고 Deployment는 속도를 제어하며,
Pod가 이전 ReplicaSet에서 새로운 ReplicaSet 하위로의 이동을 관리합니다.
각각의 새로운 ReplicaSet은 Deployment의 수정을 반영합니다.


✔️ Rollback to an earlier Deployment revision if the current state of the Deployment is not stable.
Each rollback updates the revision of the Deployment.

✔️ Scale up the Deployment to facilitate more load.

✔️ Pause the rollout of a Deployment to apply multiple fixes to its PodTemplateSpec and then resume it to start a new rollout.

✔️ Use the status of the Deployment as an indicator that a rollout has stuck.

✔️ Clean up older ReplicaSets that you don't need anymore.

---

## Deployment yaml


```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment          # ❶
  labels:                         # ❸.①
    app: nginx                    # ❸.①
spec:
  replicas: 3                     # ❷
  selector:                       # ❷
    matchLabels:                  # ❷
      app: nginx                  # ❷
  template:
    metadata:
      labels:
        app: nginx
    spec:                         # ❸.②
      containers:                 # ❸.②
      - name: nginx               # ❸.③
        image: nginx:1.14.2       # ❸.②
        ports:
        - containerPort: 80
```

<br/>

#### ❶
Kubernetes는 `.metadata.name` field 를 참고하여 `nginx-deployment`를 생성합니다.
이 이름은 나중에 생성될 `ReplicaSets` 나 `Pods` 의 기초가 됩니다.
자세한 내용은 이후 [Deployment Spec](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/#writing-a-deployment-spec) 을 설명할 때 다루겠습니다.

<br/>

#### ❷

`Deployment`는 `.spec.replicas` field 를 참고해서 세 개의 Pod을 생성할 `ReplicaSet`을 생성합니다.
`.spec.selector` field는 생성된 `ReplicaSet`이 어떤 Pod들을 관리할지를 어떻게 결정할지 정의합니다.
위 경우엔, 파드의 템플릿 (app: nginx) 로 정의된 레이블을 찾습니다.
하지만, 파드 템플릿 자체의 규칙을 만족시키는 한, 조금 더 정교한 규칙을 적용할 수 있습니다.

<table><tr><td>
<b>참고</b> 

`.spec.selector.matchLabels` 필드는 `{key,value}`의 쌍으로 매핑되어 있습니다.
`matchLabels` 에 매핑된 단일 `{key,value}`은 `matchExpressions` 의 요소에 해당하며,
key 필드는 "key"에 그리고 `operator`는 "In"에 대응되며 value 배열은 "value"만 포함합니다.
매칭을 위해서는 `matchLabels` 와 `matchExpressions` 의 모든 요건이 충족되어야 합니다.

</td></tr></table>
<br/>

#### ❸

템플릿의 필드는 다음과 같은 하위 필드들을 포함합니다:

- ❸.①: 파드들은 `.metadata.labels` 필드를 통해 `app: nginx`로 라벨링됩니다.
- ❸.②: 파드 템플리들의 상세 명세나 `.template.spec` 필드는 파드들이 `nginx`라는 하나의 컨테이너에서 동작한다는 것을 의미하는데, 이는 `nginx` 도커 허브 이미지 버전 1.14.2. 에서 동작할 것입니다.
    - ```yaml
    selector:
    matchLabels:
    component: redis
    matchExpressions:
      - { key: tier, operator: In, values: [cache] }
      - { key: environment, operator: NotIn, values: [dev] }```
- ❸.③: `.spec.template.spec.containers[0].name` 필드에 명세된 이름 `nginx`으로 컨테이너가 생성됩니다.



---


[//]: # (ReplicationController 혹은 ReplicaSet 와 Service 등과 함께 Pod를 운영하고 있다고 가정해봅시다.)

[//]: # (이 때, Pod를 업데이트 할 때 아래의 두 가지를 생각할 것입니다.)

[//]: # ()
[//]: # (- Pod 제거 후 새로운 Pod 생성)

[//]: # (- Pod 생성 후 기존의 Pod 제거)

[//]: # ()
[//]: # (두 방식 모두 장·단점이 있습니다. )




