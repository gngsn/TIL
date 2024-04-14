# Configure Applications

## Environment Variables 

환경 변수를 설정하려면 `env` 속성 사용


```yaml
apiVersion: v1
kind: Pod
metadata:
  name: ubuntu-sleeper-pod
spec:
  containers:
    - name: ubuntu-sleeper
	  image: ubuntu-sleeper
	  ports:
        - containerPort: 8080
	  env:
        - name: APP_COLOR
        - value: pink
```

`env`는 배열로, 각 `name`과 `value` 명시

`name`은 환경 변수 이름, `value`는 해당 환경 변수 값

```Bash
docker run -e APP_COLOR=pink simple-webapp-color
```

위 처럼 직접 값을 입력할 수도 있지만, `ConfigMap`이나 `Secret`의 값을 가져올 수 있음

---

## ConfigMaps

Pod 정의 파일이 많아질 수록, 한 파일에 여러 환경 변수를 관리하기 어려워짐

이러한 데이터들을 Pod 정의 파일에서 분리해 Configuration Map 으로 중앙 관리할 수 있음

ConfigMap은 쿠버네티스에서 Key-Value 쌍의 Config 데이터를 전달하는 데 사용

<br>

```Bash
APP_COLOR: blue
APP_MODE: prod
```

<br>

Pod가 생성되면 Pod에 Config map 값을 주입해 환경 변수로 사용될 수 있게 함

<br>

---

<br>

**ConfigMap 구성 두 단계**

<br>

### STEP 1. Create ConfigMap

첫째, 구성 맵을 생성

다른 쿠버네티스 객체와 동일하게 ConfigMap은 두 가지 방법으로 생성 가능

#### 📌 1. Imperative

<br>

**✔️ --from-literal option**

```Bash
❯ kubectl create configmap \
     <<config-name>> --from-literal=<key>=<value>
```

Key-Value 를 Command Line에 바로 입력

<br>

**Example.**

```Bash
❯ kubectl create configmap \
     app-config --from-literal=APP_COLOR=blue \
                --from-literal=APP_MODE=prod \
```

여러 개를 사용하고 싶다면 `--from-literal` Option 사용

<br>

**✔️ --from-file option**

```Bash
❯ kubectl create configmap \
     <<config-name>> --from-file=<path-to-file>
```

명령줄이 길어질 수 있는데, 이때 `--from-file` 옵션을 통해 파일을 명시할 수 있음

<br>

**Example.**


```Bash
❯ kubectl create configmap \
     app-config --from-file=app_config.properties
```

<br>

#### 📌 2. Declarative

```Bash
kubectl create -f ...
```

선언적 방식은 definition 파일 생성

ConfigMap 정의 파일은 `spec` 대신 `data` 속성을 포함

<br>

**Example.**

_config-map.yaml_

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  APP_COLOR: blue
  APP_MODE: prod
```

Config 파일 이름 지정해서 kubectl 명령 실행

```Bash
kubectl create -f config-map.yaml
```

그럼 지정한 값으로 ConfigMap을 생성함

필요한 만큼 ConfigMap 생성 가능

<table>
<tr>
<th>app-config</th>
<th>mysql-config</th>
<th>redis-config</th>
</tr>
<tr>
<td>
<pre><code lang="bash">APP_COLOR: blue
APP_MODE: prod
</code></pre>
</td>
<td>
<pre><code lang="bash">port: 3306
max_allowed_packet: 128M
</code></pre>
</td>
<td>
<pre><code lang="bash">port: 6379
rdb-compression: yes
</code></pre>
</td>
</tr>
</table>

각각 Application, SQL, Redis 을 위한 Config Map

나중에 이름을 기반으로 Pod 주입을 결정하는데, 이름을 정확히 정의·명시 하는 게 중요

<br>

#### View ConfigMaps

Config Map 확인은 `Kubectl get configmaps` 명령 실행

```Bash
❯ kubectl get configmaps
NAME                          DATA   AGE
app-config                    2      5s
kube-root-ca.crt              1      48d
```

자세하게 보기 위해선 `Kubectl describe configmaps` 명령 실행으로 상세 확인 가능

Data 섹션 하위에 지정한 환경 변수를 확인할 수 있음

<br/>

```Bash
❯ kubectl describe configmaps
Name:         app-config
Namespace:    default
Labels:       <none>
Annotations:  <none>

Data
====
APP_COLOR:
----
blue
APP_MODE:
----
prod

BinaryData
...
```

<br>

### STEP 2. Inject into Pod

_둘째, 그걸 포드에 주입_

간단한 웹앱 실행 Pod 정의 파일을 예시로 들자면,

<table>
<tr><td>

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
    ports:
    - containerPort: 8080
    envFrom:
    - configMapRef:
        name: app-config
```

</td></tr>
<tr><td>

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  APP_COLOR: blue
  APP_MODE: prod
```

</td></tr>
</table>

환경 변수를 삽입하려면 `containers` 하위에 `envFrom` 새 속성을 추가

`envFrom` 속성은 Array로 다중의 환경 변수를 전달할 수 있음

→ 이전에 생성해둔 ConfigMap 값을 주입하는 방법


---

Pod에 환경변수를 입력하는 방법은 세 가지가 존재 

**📌 1. Env: ConfigMap 사용**

```yaml
envFrom:
    - configMapRef:
        name: app-config     
```

<br>

**📌 2. Single Env**

단일 환경 변수로 입력

```yaml
env:
  - name: APP_COLOR
    valueFrom:
      configMapKeyRef:
        name: app-config     
        key: APP_COLOR     
```

<br>

**📌 3. Volume**

전체 데이터를 파일로 볼륨에 입력

```yaml
volumes:
  - name: app-config-volume
    configMap: 
      name: app-config
```

<br>
