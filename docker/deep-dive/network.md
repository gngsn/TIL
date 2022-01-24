# Docker Network



<details>
<summary>번역 원본 페이지</summary>
<a href="https://github.com/moby/libnetwork/blob/master/docs/design.md" alt="libnetwork design"> libnetwork design </a>
</details>
<br/><br/>

### Goal


<details>
<summary>원본</summary>
libnetwork project will follow Docker and Linux philosophy of developing small, highly modular and composable tools that work well independently. Libnetwork aims to satisfy that composable need for Networking in Containers.
</details>

<br/>

libnetwork 프로젝트는 **독립적으로 잘 작동하는 작고 모듈화된 복합적인 도구들을 개발한다**<small>developing small, highly modular and composable tools that work well independently</small>는 도커와 리눅스의 철학을 따른다.
<code>Libnetwork</code>는 컨테이너 내 네트워킹에 대해 위와 같이 조립될 수 있는 조건을 충족시키는 것을 목표로 한다.

<br/><br/>

### The Container Network Model

Libnetwork는 컨테이너에 대한 네트워킹을 제공하는 데 필요한 단계를 형식화하는 동시에 여러 네트워크 드라이버를 지원하는 데 사용할 수 있는 추상화를 제공하는 **CNM(Container Network Model)**을 구현한다. CNM은 3가지 주요 구성 요소로 구성된다.

<br/>

![img](https://github.com/moby/libnetwork/raw/master/docs/cnm-model.jpg?raw=true)

<br/>

<details>
<summary><b>Sandbox</b></summary>
<p>
A Sandbox contains the configuration of a container's network stack. This includes management of the container's interfaces, routing table and DNS settings. An implementation of a Sandbox could be a Linux Network Namespace, a FreeBSD Jail or other similar concept. A Sandbox may contain <i>many</i> endpoints from <i>multiple</i> networks.
</p>
</details>

Sandbox은 컨테이너의 네트워크 스택 구성을 포함한다. 

여기에는 container's interfaces, routing table and DNS settings 관리가 포함된다. Sandbox의 구현체<small>implementation </small>는 Linux Network Namespace, a FreeBSD Jail이나 기타 유사한 개념일 수 있다. Sandbox 에는 여러 Network의 많은 Endpoint가 포함될 수 있다.

<br/>

<details>
<summary> <b>Endpoint</b> </summary>
<p>
An Endpoint joins a Sandbox to a Network. An implementation of an Endpoint could be a <code>veth</code> pair, an Open vSwitch internal port or similar. An Endpoint can belong to only one network and it can belong to only one Sandbox, if connected.
</p>
</details>

Endpoint는 Sandbox를 Network에 연결시킨다.
Endpoint 구현은 `veth` 쌍이나, Open vSwitch 내부 포트 등이 될 수 있다.
Endpoint는 하나의 네트워크가 연결된 경우, 단 하나의 Endpoint에만 속할 수 있다.

<br/>

*veth: 리눅스의 virtual ethernet interface*

*Open vSwitch : OVS로 약칭되며 분산 가상 멀티 레이어 스위치의 오픈 소스 구현*

<br/>

<details>
<summary> <b>Network</b> </summary>
<p>
A Network is a group of Endpoints that are able to communicate with each-other directly. An implementation of a Network could be a Linux bridge, a VLAN, etc. Networks consist of <i>many</i> endpoints.
</p>
</details>
네트워크는 서로가 직접 통신할 수 있는 Endpoint 그룹이다.
네트워크의 구현은 Linux bridge, a VLAN 등이 될 수 있다.
네트워크는 많은 Endpoint로 구성된다.

<br/><br/>

### CNM Objects

**NetworkController** 

NetworkController 객체는 유저가(e.g. Docker Engine)가 네트워크를 할당하고 관리할 수 있도록 간단한 API를 제공하는 libnetwork의 진입점<small>entry-point</small>을 제공한다. 

libnetwork는 내장 드라이버와 원격 드라이버를 모두 지원한다. 

NetworkController를 사용하면 특정 드라이버를 지정된 네트워크에 바인딩할 수 있다.













