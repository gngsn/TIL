## Objects In Kubernetes

[🔗 official](https://kubernetes.io/docs/concepts/overview/working-with-objects/#kubernetes-objects)

<br/>

### Understanding Kubernetes objects

Kubernetes Objects는 Kubernetes 시스템의 persistent 엔터티이다.
Kubernetes는 당신의 cluster의 상태를 나타내기 위해 이러한 엔티티를 사용.

- 어떤 컨테이너화된 애플리케이션이 실행되는지 혹은, 어떤 노드들인지
- 이러한 애플리케이션들이 사용할 수 있는 리소스
- 이러한 애플리케이션이 어떻게 행동하는지에 관련된 정책; 가령, restart 정책, upgrade, 그리고 fault-tolerance.

Kubernetes object 는 일종의 "의도를 담은 레코드 (record of intent)" 로 볼 수 있다.

한 번 object를 생성하면, Kubernetes 시스템은 해당 객체가 존재를 지속적으로 보장한다.

object를 생성하는 것은 Kubernetes 시스템에 당신의 cluster가 어떤 워크로드처럼 동작할지 결정하는 것이다.
(object를 생성함으로써, 당신은 Kubernetes 시스템에 당신이 원하는 cluster의 워크로드이 어떻게 보일지 효과적으로 말하는 것이다.)
-> 이것이 바로 cluster의 의도한 상태 (desired state)이다.

생성, 수정, 혹은 삭제하는 것이든, Kubernetes object 와 동작하기 위해서는 Kubernetes API가 사용된다.
가령, `kuberctl` 명령어를 사용하게 되면 CLI는 필요한 Kubernetes API 호출을 당신을 위해 만들 것이다.
또한, Client Library 를 사용해서 당신의 프로그램에서 Kubernetes API를 바로 호출할 수도 있다. 

<br/>

### Object spec and status

거의 모든 쿠버네티스 오브젝트는 `spec` 오브젝트와 `status`오브젝트를 포함하는데, 이는 오브젝트의 구성을 결정해주는 두 개의 중첩되곤 한다.

`spec`을 가진 오브젝트를 생성하기 위해선, **의도한 상태** *desired state*와 같은 리소스에 원하는 특징에 대한 설명을 추가해 설정해야 한다.

`status`는 쿠버네티스 시스템과 컴포넌트에 의해 제공되며, 업데이트된 오브젝트의 **현재 상태**를 설명한다. 
쿠버네티스 ***컨트롤 플레인**은 모든 오브젝트의 실제 상태를 사용자가 의도한 상태와 일치시키기 위해 끊임없이 그리고 능동적으로 관리한다.

<small>**컨트롤 플레인*: 컨테이너의 라이프사이클을 정의, 배포, 관리하기 위한 API와 인터페이스들을 노출하는 컨테이너 오케스트레이션 레이어.</small>

예를 들어, 쿠버네티스 Deployment 는 클러스터에서 동작하는 애플리케이션을 표현해줄 수 있는 오브젝트이다.
Deployment 생성 시, 디플로이먼트 spec에 3개의 애플리케이션 레플리카가 동작되도록 설정할 수 있다.
쿠버네티스 시스템은 그 디플로이먼트 spec을 읽어 spec에 일치되도록 상태를 업데이트하여 3개의 의도한 애플리케이션 인스턴스를 구동시킨다. 
만약, 그 인스턴스들 중 어느 하나가 어떤 문제로 인해 멈춘다면(상태 변화 발생), 쿠버네티스 시스템은 보정(이 경우에는 대체 인스턴스를 시작하여)을 통해 spec과 status간의 차이에 대응한다.

오브젝트 명세, 상태, 그리고 메타데이터에 대한 추가 정보는, Kubernetes API Conventions 를 참조한다.



<br/>

### Required fields
[🔗 official] (https://kubernetes.io/docs/concepts/overview/working-with-objects/#required-fields)

생성하려는 Kubernetes 개체의 매니페스트(YAML 또는 JSON 파일)에는 이래와 같은 필드 값을 설정 필요:

- `apiVersion`: 해당 object를 생성하기 위해 사용하는 Kubernetes API 버전
- `kind`: 생성하고자 하는 object의 종류 (kind)
- `metadata`: object 를 구별할 용도의 `name`, `UID`(, 선택적으로 `namespace`) 등을 포함한 object 데이터
- `spec` - object가 가질 상태 정의. 객체 spec의 정확한 형식은 모든 쿠버네티스 객체마다 다르며, 한 객체에 특정된 중첩된 필드를 포함. 쿠버네티스 API 참조를 통해 쿠버네티스를 사용하여 생성할 수 있는 모든 객체의 사양 형식을 찾을 수 있다.

가령, Pod API의 spec 필드 문서를 참고해보면, 각 Pod의 경우, `.spec` 필드는 `Pod` 와 `Pod`가 가질 상태를 지정 (예: 해당 Pod 내의 각 컨테이너에 대한 컨테이너 이미지 이름).
다른 예로, StatefulSet의 경우, `.spec` 필드는 `StatefulSet`와 원하는 상태를 지정.
StatefulSet의 `.spec` 내에는 Pod 개체에 대한 템플릿이 있음. 해당 템플릿은 StatefulSet 컨트롤러가 StatefulSet 규격을 만족시키기 위해 만들 Pods를 설명함.
다른 종류의 개체도 다른 `.status`를 가질 수 있으며, 또 API 참조 페이지는 해당 `.status` 필드의 구조와 각 다른 유형의 개체에 대한 내용을 자세히 설명함


FYI. [Configuration Best Practices] (https://kubernetes.io/docs/concepts/configuration/overview/)

<br/>

## Pods

Pod는 배포를 할 수 있는 Kubernetes에서 생성하고 관리할 수 있는 가장 작은 단위.

하나의 Pod은 (고래의 꼬리나 완두콩의 깍지같은) 하나의 컨테이너이거나 컨테이너 그룹인데, 공유가능한 저장소, 네트워크 리소스, 그리고 컨테이너들의 동작에 대한 명세들과 함께한다.

Pod의 내용은 항상 상황에 따라 함께 위치되거나 혹은 함께 스케줄링되어야 한다.
하나의 Pod 모델
A Pod models an application-specific "logical host": it contains one or more application containers which are relatively tightly coupled. In non-cloud contexts, applications executed on the same physical or virtual machine are analogous to cloud applications executed on the same logical host.

As well as application containers, a Pod can contain init containers that run during Pod startup. You can also inject ephemeral containers for debugging if your cluster offers this.

<br/>

<table><tr><td>

**Examples**
Create the objects defined in a configuration file:

``Bash
kubectl create -f nginx.yaml
```

Delete the objects defined in two configuration files:

``Bash
kubectl delete -f nginx.yaml -f redis.yaml
```

Update the objects defined in a configuration file by overwriting the live configuration:

``Bash
kubectl replace -f nginx.yaml
```

</td></tr></table>



<br/>

## Deployment

<br/>

## Controllers

<br/>

## kubectl and kubectl commands

<br/>
