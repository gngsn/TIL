## Objects In Kubernetes

[🔗 official] (https://kubernetes.io/docs/concepts/overview/working-with-objects/#kubernetes-objects)

<br/>

### Object spec and status

Kubernetes Objects는 Kubernetes 시스템의 persistent 엔터티이다.
Kubernetes는 당신의 cluster의 상태를 나타내기 위해 이러한 엔티티를 사용.

- 어떤 컨테이너화된 애플리케이션이 실행되는지 혹은, 어떤 노드들인지
- 이러한 애플리케이션들이 사용할 수 있는 리소스
- 이러한 애플리케이션이 어떻게 행동하는지에 관련된 정책; 가령, restart 정책, upgrade, 그리고 fault-tolerance.

Kubernetes object 는 "record of intent" 으로,
한 번 object를 생성하면, Kubernetes 시스템은 해당 객체가 존재를 지속적으로 보장한다.
object를 생성하는 것은 Kubernetes 시스템에 당신의 cluster가 어떤 워크로드처럼 동작할지 결정하는 것이다.
(object를 생성함으로써, 당신은 Kubernetes 시스템에 당신이 원하는 cluster의 워크로드이 어떻게 보일지 효과적으로 말하는 것이다.)
-> 이것이 바로 cluster의 목표 상태 (desired state)이다.

Kubernetes object 와 동작하기 위해서는 - 그것들을 생성, 수정, 혹은 삭제하는 것에 상관없이 - Kubernetes API가 사용된다.
가령, `kuberctl` 명령어를 사용하게 되면 CLI는 필요한 Kubernetes API 호출을 당신을 위해 만들 것이다.
또한, Client Library 를 사용해서 당신의 프로그램에서 Kubernetes API를 바로 호출할 수도 있다. 

### Object spec and status


