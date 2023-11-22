### Deployments

[🔗 Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)

A Deployment provides declarative updates for Pods and ReplicaSets.

You describe a desired state in a Deployment, and the Deployment Controller changes the actual state to the desired state at a controlled rate. You can define Deployments to create new ReplicaSets, or to remove existing Deployments and adopt all their resources with new Deployments.


The following is an example of a Deployment.
It creates a ReplicaSet to bring up three nginx Pods:

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



