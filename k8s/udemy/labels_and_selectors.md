# Labels and Selectors

Labels 과 Selectors 는 그룹을 만드는 대표적인 방법

가령, 동물들을 분류할 때 1·2·3 등급/야생·가축/색상 등을 그룹지을 수 있는데, 초록색 동물을에 Green 이라는 Label이 붙여 분류

Selector는 분류한 항목들을 필터링

가령, 분류 값이 '포유류'라면 포유류 목록을 얻을 수 있고, 거기에 색깔도 '녹색'으로 지정하면 녹색 포유류를 얻을 수 있음

<br/>

### 쿠버네티스 내 Label과 Selector 사용

다양한 쿠버네티스의 Pods, Services, ReplicaSets, Deployments, 등 모든 것들은 서로 다른 '객체' 이며,
한 클러스터 내에 수많은 객체들이 생성 될 수 있음

이렇게 수 많은 객체들은 카테고리 별 필터링할 수 있는 방법이 필요

가령 아래와 같이 구분할 수 있음

- 객체의 타입 별: Pods, Services, Replica Sets, ... 
- 애플리케이션 별: App1, App2, App3, ...
- 기능 별: Front-End, Web-Servers, Auth, Image-Processing, Cache ...

의도에 따라 Label과 Selector를 설정해 객체를 그룹화하고 선택 


<br/>

### Labels

Pod 생성 시, 아래와 같이 Label을 지정할 수 있음

<pre><code lang="yaml">
apiVersion: v1
kind: Pod
metadata:
  name: simple-webapp
  <b>labels:
    app: App1
    function: Front-end</b>
spec:
  containers:
    - name: nginx-container
      image: nginx
</code></pre>

