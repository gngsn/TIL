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

NetworkController 객체는 유저가(e.g. Docker Engine)가 네트워크를 할당하고 관리할 수 있도록 간단한 API를 제공하는 libnetwork의 진입점<small>entry-point</small>을 제공한다. libnetwork는 내장 드라이버와 원격 드라이버를 모두 지원한다. NetworkController를 사용하면 특정 드라이버를 지정된 네트워크에 바인딩할 수 있다.

<br/>

**Driver**

Driver는 사용자가 볼 수 있는 객체는 아니지만 드라이버는 실제 네트워크 구현을 제공한다. 

NetworkController는 libnetwork에 투명하지만 드라이버에 의해 직접 처리될 수 있는 드라이버별 옵션/라벨을 구성할 수 있는 API를 제공합니다. 드라이버는 다양한 사용 사례 및 배포 시나리오를 만족시키기 위해 내장(예: 브리지, 호스트, 없음 및 오버레이)과 원격(플러그인 제공자의) 둘 다일 수 있다. 이때 드라이버는 네트워크를 소유하며 네트워크(IPAM 등) 관리를 담당한다. 향후 다양한 네트워크 관리 기능을 처리하는 데 여러 드라이버가 참여함으로써 이러한 점을 개선할 수 있다.

<br/>

**Network**

`Network` object는 위에서 정의한 CNM : Network의 구현체이다. NetworkController는 네트워크 개체를 만들고 관리할 수 있는 API를 제공한다. 네트워크가 생성되거나 업데이트될 때마다 해당 드라이버에게 이벤트에 대한 알림이 표시된다. LibNetwork는 네트워크 객체를 추상 수준으로 처리하여 동일한 네트워크에 속한 엔드포인트 그룹과 나머지 엔드포인트와의 연결을 제공한다. 드라이버는 필요한 연결 및 격리를 제공하는 실제 작업을 수행한다. 연결은 동일한 호스트 내에 있거나 여러 호스트에 걸쳐 있을 수 있다. 따라서 네트워크는 클러스터 내에서 전역 범위를 가진다.

<br/>

**Endpoint** 

Endpoint은 서비스의 Endpoint을 나타낸다. 네트워크의 컨테이너가 노출하는<small>exposed</small> 서비스와 그 네트워크의 다른 컨테이너가 제공하는 다른 서비스에 대한 연결을 제공한다.`Network` 개체는 Endpoint를 생성하고 관리할 수 있는 API를 제공한다. Endpoint은 하나의 네트워크에만 연결할 수 있다. Endpoint의 생성 호출은 해당 Sandbox에 대한 리소스 할당을 담당하는 해당 드라이버에 대해 수행된다. Endpoint는 특정 컨테이너가 아닌 서비스를 나타내므로 Endpoint는 클러스터 내에 글로벌 범위를 가진다.

<br/>

**Sandbox**

Sandbox는 IP address, MAC address, routes, DNS entries와 같은 컨테이너의 네트워크 설정값을 나타낸다. Sandbox 객체는 유저가 Network에 endpoint의 생성을 요청할 때 생성된다. 네트워크를 처리하는 Driver는 필요한 네트워크 리소스(e.g. IP address)를 할당하고 `libnetwork` 에 `SandboxInfo` 로 불리는 정보들을 다시 전달한다. libnetwork는 OS별 구조체(e.g. Linux용 netns)를 사용하여 네트워크 구성을 Sandbox로 표현되는 컨테이너에 채운다. Sandbox는 여러 Endpoint를 서로 다른 네트워크에 연결할 수 있다. Sandbox는 지정된 호스트의 특정 컨테이너와 연결되므로 컨테이너가 속한 호스트를 나타내는 로컬 범위를 가진다.

<br/><br/>

## CNM Lifecycle



도커와 같은 CNM의 소비자는 CNM 객체와 API를 통해 자신이 관리하는 컨테이너를 네트워크화한다.

1. Driver은 NetworkController에 등록된다. 내장<small>built-in</small> 드라이버은 libnetwork 내부에 등록되며, 원격 드라이버는 플러그인 메커니즘(WIP)을 통해 libnetwork에 등록된다. 각 드라이버는 특정 networkType을 처리한다.

2. `NetworkController` 객체는  `libnetwork.New()` API를 사용하여 네트워크 할당을 관리하고 선택적으로 드라이버별 옵션을 설정한다. 

3. 네트워크는 컨트롤러의 `NewNetwork()` API에 `name`과 `networkType`을 제공하여 생성된다. `networkType` 매개 변수는 적합한 드라이버를 찾는데 도움을 주며 생성된 Network를 찾은 Driver에 바인딩한다. 이 시점부터 네트워크의 모든 작업은 해당 드라이버가 처리한다.

  <details>
  <summary>NewNetwork</summary>
  <pre lang="go">
  	type NetworkController interface {
	    // ...
	    NewNetwork(networkType, name string, id string, options ...NetworkOption) (Network, error)
	    // ...
  	}
  </pre>
  </details>


4. `controller.NewNetwork()` API 드라이버 별 옵션과 레이블을 전달할 수 있는 선택적인 옵션 매개변수를 사용하며 드라이버가 그것의 목적에 맞게 사용할 수 있도록 한다.
5. 지정된 네트워크에서 `network.CreateEndpoint()`  호출하여 새 Endpoint를 만들 수 있다. 이 API도 드라이버가 사용할 수 있는 옵션 매개 변수를 받는다. 이러한 '옵션'에는 잘 알려진 라벨과 드리아버별 라벨이 모두 포함된다. 드라이버는 차례로 `driver.CreateEndpoint` 를 사용하여 호출되며, Endpoint가 네트워크에 생성될 때 IPv4/IPv6 주소를 예약<small>reserve</small>하도록 선택할 수 있다. 드라이버는 `driverapi`에 정의된 `InterfaceInfo` 인터페이스를 사용하여 이러한 주소를 할당한다. 서비스 endpoint는 기본적으로 애플리케이션 컨테이너가 수신 중인 네트워크 주소와 포트 번호에 불과하기 때문에 endpoint를 서비스 정의로 완료하는 데 endpoint가 노출시키는 포트와 함께 IP/IPv6가 필요하다.
5. 
