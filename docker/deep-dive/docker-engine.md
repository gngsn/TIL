## I**ntroduction : Docker Engine**

containers들을 관리하고 실행시키는 핵심 소프트웨어 core software. (VMware 를 잘 알고 있다면 ESXi와 비슷하다고 생각하면 유용할 것임)

모듈식으로 설계되었고, 많은 소형 전용 툴들로 부터 제작되었다. 가능한 경우, 이러한 표준은 OCI (Open Container Initiative)에 의해 유지되는 것과 같이 **개방형 표준**open standards에 기초한다.

> *자동차 엔진과 도커 엔진은 비유를 하자면, 둘 다 모듈식이고 많은 전용 부품들에 의해 연결되어 제작된다.

> A car engine — intake manifolds, throttle body, cylinders, spark plugs, exhaust manifolds etc. 
> <br/>The Docker Engine — APIs, execution driver, runtimes, shims etc.*

<br/>

 Docker 엔진을 구성하는 주요 구성 요소는 *Docker daemon*, *containerd*, *runc* 및 *networking and storage*와 같은 다양한 플러그인이다. 이 모두가 함께 컨테이너를 생성하고 실행한다.

<br/><br/>

## 초기 Docker model

도커가 처음 출시되었을 때 도커 엔진은 두 가지 주요 구성 요소를 가지고 있었다.

- The Docker daemon (이하 "the daemon"이라고 함)
- LXC

<br/>

**The Docker daemon**

The Docker daemon는 **단일 바이너리**monolithic binary이다.

Docker client, the Docker API, the container runtime, image builds 등을 비롯한 많은 코드들을 담고 있었다.

<br/>

**LXC**

LXC는 daemon에게 Linux kernel에 존재하는 컨테이너의 기본 **구성 블록**building-blocks에 대한 **namespaces**나 **제어 그룹**control groups(cgroups)와 같은 접근을 제공했다.

<br/>

## 현재 Docker model

### Getting rid of LXC

LXC에 대한 의존도는 처음부터 문제였다.

먼저, LXC는 **리눅스에 특화되어 있는데**Linux-specific, multi-platform을 목표로 하는데 큰 리스크였다.

결과적으로 Docker.Inc는 LXC를 대체하기 위해 `libcontainer` 라는 독자적인 툴을 개발했다. `libcontainer`는 Docker가 호스트 커널에 존재하는 기본적인 컨테이너 빌딩 블록에 접근할 수 있도록 하는 플랫폼 애그노스틱 platform-agnostic(플랫폼-불가지론, 불특정 플랫폼) 툴이 되는 것을 목표로 개발되었다.

<br/>

**❓ 기본적인 빌딩 블록이 뭘까? fundamental container building-blocks**

<br/><br/>

Libcontainer는 LXC를 대체해서 Docker 0.9의 기본 실행 드라이버가 되었다.

**Getting rid of the monolithic Docker daemon**

시간이 지남에 따라 도커 데몬의 모놀리식monolithic 특징이 점점 더 문제가 되었다.

- 변경innovate이 어렵다.
- 점점 느려진다.
- 이 것은 생태계가 원하는 것이 아니었다.

<br/>

Docker, Inc는 이런 문제를 인지하고 단일화된 데몬을 분해하고 모듈화하기 위한 많은 노력을 했다.

가능한 많은 기능으로 분리하여 더 작은 단위의 **특성화된 툴들**specialized tools로로 다시 구현하였다.

이렇게 분리된 툴들은 교체swapped out할 수 있게 되었고, 다른 툴을 제작할 때 서드파티 툴로 재사용이 용이했다.

이러한 계획은 더 큰 도구들로 맞춰질 수 있는 작은 특성화된 툴들을 제작하는 tried-and-tested Unix 철학을 따른다.

Docker 엔진을 분해하고 리팩터링하는 작업으로 **모든 컨테이너의 실행과 컨테이너 런타임 코드를 데몬에서 완전히 제거하고 작고 특성화된 도구로 리팩터링되는 과정**을 확인해보았다.

<br/><br/>

**The influence of the Open Container Initiative (OCI)**

Docker,Inc 가 데몬을 분해하고 코드를 리팩토링하는 동안 OCI는 두 가지 컨테이너 관련 사양(일명 표준)을 정의하는 과정에 있었다.

1. [Image spec](https://github.com/opencontainers/image-spec)
2. Container [runtime spec](https://github.com/opencontainers/runtime-spec)

<br/>

두 스펙specifications모두 2017년 7월에 version 1.0으로 출시되었고, 안정성이 중요하기 때문에 큰 변화를 보이면 안된다.

두개의 최신 사양은 아래와 같다.

- Image spec : 2017년 11월 v1.0.1
- runtime spec : 2020년 3월 v1.0.2

<br/>

Docker, Inc는 이러한 규격을 만드는데 많은 관여를 했고, 많은 코드들에 기여했다.

2016년 초, Docker 1.11는 Docker 엔진을 OCI 사양에 최대한 가깝게 구현했다.

예를 들어, Docker daemon은 더 이상 컨테이너의 런타임 코드를 포함하지 않는다 - 모든 컨테이너의 런타임 코드는 별도로 분리된 OCI 호환 계층OCI-compilant layer에서 구현된다.

기본적으로 Docker는 이를 위해 runc를 사용한다.

runc는 OCI 컨테이너 런타임 사양의 기준 구현체이다. runc is the *reference implementation* of the OCI container-runtime-spec.

또한 도커 엔진의 *containerd* 는 도커 이미지가 유효한 OCI 번들로의 *runc*로 표시되게끔 만든다.

<small>As well as this, the *containerd* component of the Docker Engine makes sure Docker images are presented to *runc* as valid OCI bundles.</small>

<br/><br/>

### **runc**

- **❓ 컨테이너 런타임 툴이란?**

runc는 OCI container-runtime-spec의 구현체이다. Docker, Inc는 규격의 정의하는데 크게 관여했고, runc를 개발했다.

runc는 작고, 결향의 libcontainer 용 CLI Wrapper이다.

(libcontainer - 초기 도커 아키텍처에서 LXC를 인터페이스 계층으로 대체)

runc는 **Container 생성**이라는 단 하나의 목적만을 가지며, 빠르고 훌륭good하다.

하지만, CLI wrapper이기 때문에 효과적으로 독립된 컨테이너 런타임 툴이다. 이 말은 바이너리로 다운받고 빌드할 수 있는데, 곧 runc (OCI) container를 빌드하고 실행시킬 수 있다는 의미이다.

하지만 이것은 골격bare bones이며 매우 낮은 레벨low-level이다. 즉, **완전한 Docker 엔진의 특성**full-blown Docker engine을 가질 수는 없다.

🔗 [*runc* 의 릴리즈 정보](https://github.com/opencontainers/runc/releases)

<br/><br/>

### containerd

Docker 데몬에서 기능을 제거하기 위한 노력의 일환으로 모든 컨테이너 실행 로직이 containerd라는 새로운 툴로 리팩토링되었다.

<br/>

이것의 단 하나의 목적은 컨테이너 **생명 주기 운영**container lifecycle operations을 관리하는 것이었다. `start | stop | pause | rm...`

containerd는 리눅스와 윈도우용 데몬으로 사용할 수 있으며, Docker 1.11 릴리즈 이후 도커는 리눅스 상에서 이 데몬을 사용하고 있다. 도커 엔진 스택에서 containerd는 OCI 레이어에서 데몬과 런크 사이에 위치한다.

containerd는 원래 작고, 가볍고lightweight, 그리고 **container lifecycle operations**라는 하나의 작업을 위해 설계되었다. 하지만, 시간이 지남에 따라 *image pulls, volumes and networks와 같은* 기능들이 확장되었다.

<br/>

기능을 추가하는 이유 중 하나는 다른 프로젝트에서 더 쉽게 사용할 수 있도록 하기 위해서이다.

예를 들어, Kubernetes와 같은 프로젝트에서는 containerd가 이미지를 push하고 pull하는 것과 같은 추가적인 작업을 수행하는 것에 유용했다. 이런 이유로, containerd는 단순한 컨테이너 생명주기container lifecycle 관리, 그 이상의 것들을 수행한다. 하지만, 모든 추가적인 기능들은 모듈화되어있고 옵션이기 때문에 원하는 것들을 선택choose하고 채택pick할 수 있다. 따라서 프로젝트(ex. Kubernetes)에 필요한 부분만을 가져온 containerd를 포함할 수 있다.

containerd는 Docker, Inc.에 의해 개발되었으며 Cloud Native Computing Foundation(CNCF)에 기부되었다. containerd는 완전한 CNCF 프로젝트로 안정적이고 생산 준비가 되어있다.

[🔗 containerd 의 최신 릴리즈 정보](https://github.com/containerd/containerd/releases)

<br/><br/>

### Starting a new Conrainer (example)

큰 그림과 역사에 대한 내용을 조금 배웠으니, 새로운 컨테이너를 만드는 과정을 알아보자.

컨테이너를 시작하는 가장 일반적인 방법은 Docker CLI를 사용하는 것이다.

```bash
$ docker container run --name ctr1 -it alpine:latest sh
```

<br/>

이와 같은 명령을 Docker CLI에 입력하면 Docker client는 적절한 API 페이로드로 변횐해서 그들의 Docker daemon에 의해 노출된 API endpoint로 POST 요청을 한다.

API는 daemon에서 시행implemented되며 로컬 소켓이나 네트워크를 통해 노출될 수 있다.

Linux에서 socket은 `/var/run/docker.sock` 이고, Windows에서는 `\\pipe\\docker_engine` 이다.

daemon이 새로운 container를 생성하라는 명령을 수신하면, containerd를 호출한다. (daemon은 더 이상 container를 생성하는 그 어떤 코드도 포함되어 있지 않다는 것을 기억해라)

<br/>

**daemon은 gRPC를 통해 CRUD 스타일 API를 통해 containerd와 통신한다.**

<br/>

사실, containerd는 실제로 containers를 생성하지 못한다. runc를 통해 생성한다, 필요한 Docker 이미지를 OCI 번들로 변환하고 runc에게 사용해서 새로운 container를 생성하라고 지시한다.

runc는 OS 커널에 접속하여interfaces 컨테이너(네임스페이스, cgroup 등)를 만드는 데 필요한 모든 구성 요소를 하나로 묶는다. 컨테이너 프로세스는 runc의 하위 프로세스로 시작되는데, 시작하자마자 runc가 종료exit된다.

Voila ! 이제 container가 실행됐다.

<br/>

### **One huge benefit of this model**

daemon에 의해 제거된 container를 실행하고 관리하기 위한 모든 로직과 코드를 갖다는 것은 전체 컨테이너 런타임이 도커 데몬으로부터 분리됨을 의미한다. 우리는 종종 이것을 ”daemonless containers”라고 부르는데, 이것은 실행 중인 컨테이너에는 영향을 주지 않고 Docker 데몬에 대한 유지보수maintenance 및 업그레이드를 수행할 수 있게 해준다.

이전 모델에서는, 모든 컨테이너 런타임에 대한 모든 로직이 데몬에 구현되어 있어서, 데몬을 시작하고 중지하면 호스트에서 실행 중인 모든 컨테이너가 죽었다. production 환경에서 이 문제는 매우 큰 문제였다. 특히 새로운 버전의 Docker가 얼마나 자주 출시가 되는지를 고려해보면 알 수 있을 것이다.

다행히, 이것은 더 이상 문제가 되지 않는다.

<br/><br/>

### **What’s this shim all about?**

shim은 데몬이 없는 컨테이너의 구현에 필수적이다. (바로 위 챕터에서 데몬의 업그레이드와 같은 경우 등을 위해 데몬에서 실행 중인 컨테이너를 분리하는 것에 대해 언급했다).

앞에서 containerd가 새로운 컨테이너를 만들기 위해 runc를 사용한다고 언급했다. 사실, 그것은 만들 모든 container을 위해 runc의 새로운 인스턴스를 fork 한다. 그러나 각 컨테이너가 생성되면, 상위 runc 프로세스가 종료된다. 즉, 수백 개의 런크 인스턴스를 실행하지 않고도 수백 개의 컨테이너를 실행할 수 있다.

컨테이너의 부모parent runc 프로세스가 종료되면, 연결된 containerd-shim 프로세스가 컨테이너의 부모parent 프로세스가 된다.

shim이 컨테이너의 상위 프로세서parent로서 수행하는 책임들 중 일부는 다음과 같다.

<br/>

✔️ daemon이 재시작될 때, pips가 닫히는 등의 이유때문에 container가 종료되지 않도록 STDIN과 STDOUT 스트림을 열린 상태로 유지한다.

✔️ daemon에게 container의 종료 상태를 다시 보고한다.

<br/>

### How it’s implemented on Linux

Linux 시스템에서, 컴포넌트는 아래와 같이 분리된 binaries 들로 구현된다.

- `dockerd` (the Docker daemon)
- `docker-containerd` (containerd)
- `docker-containerd-shim` (shim)
- `docker-runc` (runc)

<br/>

Docker 호스트에서 ps 명령을 실행하면 Linux 시스템에서 이 모든 것을 볼 수 있다. 시스템에 실행 중인 컨테이너가 있을 때에만 위의 일부가 있을 수 있는 것을 확인할 수 있다.

<br/><br/>

### **What’s the point of the daemon**

데몬에 여전히 존재하는 주요 기능으로는 이미지 관리, 이미지 빌드, REST API, 인증, 보안, 코어 네트워킹, 오케스트레이션 등이 있다.

<br/>

> image management, image builds, the REST API, authentication, security, core networking, and orchestration.

<br/>

### **Securing client and daemon communication**

도커는 클라이언트-서버 모델을 구현한다.

✔️ 클라이언트 구성요소가 CLI를 시행implements한다.

✔️ 서버(daemon) 컴포넌트는 공용public-facing REST API를 포함한 기능을 시행<small>implements</small>한다.

<br/>

클라이언트는 도커(Windows - docker.exe), 데몬은 도커드(Windows - dockerd.exe)라고 한다.

기본 설치는 이들을 동일한 호스트에 배치하고 로컬 IPC 소켓을 통해 통신하도록 구성한다.

<br/>

- `/var/run/docker.sock` on Linux
- `//./pipe/docker_engine` on Windows

<br/>

네트워크를 통해 통신하도록 구성할 수도 있다.

기본적으로 네트워크 통신은 포트 `2375/tcp`의 보안되지 않은 HTTP 소켓을 통해 발생한다.

<br/>이와 같은 안전하지 않은 설정configuration은 실험실에는 적합할 수 있지만 다른 어떤 곳에서는 허용되지 않을 수 있다.

<br/><br/>

### TLS to the rescue!

도커를 사용하면 클라이언트와 데몬이 TLS로 보안된 네트워크 연결만 허용하도록 강제할 수 있다. 모든 트래픽이 (신뢰할 수 있는 내부 네트워크를 통과하는 경우를 포함해서) 프로덕션 환경에 권장된다.

클라이언트와 데몬을 모두 보호할 수 있습니다. 클라이언트 보안을 설정하면 클라이언트는 신뢰할 수 있는 CA(인증 기관)에서 서명한 인증서를 가진 Docker 데몬에만 연결된다.

데몬을 보호하면 데몬은 신뢰할 수 있는 CA의 인증서를 제공하는 클라이언트의 연결만 수락한다. 두 모드의 조합은 최고의 보안을 제공한다.

우리는 간단한 랩 환경을 이용하여 Docker를 Daemon 모드와 클라이언트 모드 TLS에 맞게 설정하는 과정을 진행해보자.