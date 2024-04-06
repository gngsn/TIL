## Rolling Updates and Rollbacks

### ✔️ Rollout

처음 `Deployment`를 생성할 때, 한 `Rollout`을 발생시키는데, 이는 새로운 배포 `Revision`을 생성

이후, 업데이트를 위해 새로운 `Deployment`를 생성하면, 
즉, 새로운 버전을 배포한다면,
새로운 `Rollout`을 발생 시키고, 이는 새로운 배포 `Revision`을 생성

배포 변화를 추적할 수 있고, 필요할 때 이전 배포 버전으로 쉽게 되돌릴 수 있음

**1. Rollout 상태 확인**

```Bash
❯ kubectl rollout status deployment/myapp-deployment
```

<br/>

**2. Rollout 내역 확인**

```Bash
❯ kubectl rollout history deployment/myapp-deployment
```

#### Deployment Strategy

배포 방법에는 2가지가 존재 

첫 번째, 배포 앱 모두 제거 후 새로운 버전 앱 생성 

문제는 모든 배포 앱을 다운시키고 나서 새로운 앱이 업로드 될 때까지 요청이 

네 이걸 새로운 버전으로 업그레이드하는 한 가지 방법은 이것들을 모두 파괴하고 응용 프로그램 인스턴스의

새로운 버전을 만드는 겁니다 실행 중인 5개의 인스턴스를 먼저 파괴한 다음 새 응용 프로그램 버전의

새 인스턴스 5개를 배포한다는 뜻이죠



<br/>


### ✔️ Versioning

```Bash
# Create
❯ kubectl create -f deployment-definition.yml

# Get
❯ kubectl get deployments

# Update
❯ kubectl apply -f deployment-definition.yml

# Update
❯ kubectl set image deployment/myapp-deployment nginx=nginx:1.9.1

# Status
❯ kubectl rollout status deployment/myapp-deployment

# Status
❯ kubectl rollout history deployment/myapp-deployment

# Rollback
❯ kubectl rollout undo deployment/myapp-deployment
```




