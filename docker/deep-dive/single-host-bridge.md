### **Single-host bridge networks**

도커 네트워크의 가장 간단한 유형은 single-host bridge network이다.

- **Signle-host**는 단일 도커 호스트에만 존재하며 동일한 호스트에 있는 컨테이너만 연결할 수 있음을 알려준다.
- **Bridge**는 802.1d 브리지(레이어 2 스위치)의 구현임을 알려준다.

Linux의 Docker는 내장 `bridge` 드라이버를 사용하여 단일 호스트 브리지 네트워크를 생성하는 반면

Windows의 Docker는 내장 `NAT` 드라이버를 사용하여 브리지 네트워크를 생성한다.

모든 면에서, 그들은 동일하게 작동한다.

<br/>

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a7348494-7cc7-43fc-97e9-815619656437/Untitled.png)

<br/>

"mynet"이라고 불리는 동일한 로컬 브리지 네트워크를 가진 두 개의 도커 호스트를 보여준다.

네트워크는 동일하지만 독립적으로 분리된 네트워크입니다. 그림 속의 Contianer가 서로 다른 네트워크에 있기 때문에 직접 통신할 수 없음을 의미합니다.

모든 도커 호스트에는 기본 단일 호스트 브리지 네트워크가 제공된다. 리눅스에서는 브릿지(bridge)라고 하며, 윈도에서는 나트(nat)라고 부른다. (이름들은 드라이버들이 그것들을 만들 때 사용한 이름과 동일하다.)

<br/>

명령줄에서 `--network` 플래그로 재정의하지 않으면 새로 생성되는 모든 컨테이너들은 이것을 기본 네트워크로 가진다.

<br/>

```bash
//Linux
$ docker network ls
NETWORK ID        NAME        DRIVER        SCOPE
333e184cd343      bridge      bridge        local

//Windows
> docker network ls
NETWORK ID        NAME        DRIVER        SCOPE
095d4090fa32      nat         nat           local
```

<br/>

다음 목록은 새로 설치된 Linux 및 Windows Docker 호스트에서 `docker network ls` 명령의 출력을 보여준다. 출력은 각 호스트의 기본 네트워크만 표시되도록 잘린다.

네트워크 이름이 네트워크 생성에 사용된 드라이버와 동일한데, 이는 우연의 일치일 뿐 요구 사항은 아니다.

<br/>

```bash
docker network inspect bridge
[
    {
        "Name": "bridge",     << Will be nat on Windows
        "Id": "333e184...d9e55",
        "Created": "2018-01-15T20:43:02.566345779Z",
        "Scope": "local",
        "Driver": "bridge",   << Will be nat on Windows
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": null,
            "Config": [
                {
                    "Subnet": "172.17.0.0/16"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        <Snip>
    }
]
```

`docker network inspect` 명령어는 훌륭한 정보들의 모음treasure trove이다. 만약 당신이 로우레벨 디테일에 관심이 있다면 위의 출력들을 읽는 것을 강력히 추천했다.

<br/>

Linux 호스트에서 bridge 드라이버로 구축된 도커 네트워크는 거의 20년 동안 리눅스 커널에 존재했던 내우외환(고군분투?)으로 다져진battle-hardened Linux bridge 기술을 기반으로 한다. 이것은 성능이 우수하고 매우 안정적이라는 것을 의미한다. 또한 표준 Linux 유틸리티를 사용하여 확인inspect할 수 있습니다.

(역자 ; battle-hardened은 원래 (군인이) 전투 경험으로 다져진으로 해석됨..)

<br/>

```bash
$ ip link show docker0
3: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc...
    link/ether 02:42:af:f9:eb:4f brd ff:ff:ff:ff:ff:ff
```

위의 코드를 예시로 보면,

모든 리눅스 기반 도커 호스트의 기본 "bridge" 네트워크는 "**docker0**"이라는 커널의 기본 Linux bridge에 매핑된다.

이것은 `docker network inspect` 를 통해 확인할 수 있다.

<br/>

```bash
$ docker network inspect bridge | grep bridge.name
"com.docker.network.bridge.name": "docker0",
```

<br/>

도커의 기본 "브리지" 네트워크와 리눅스 커널의 "도커0" 브리지 사이의 관계는 그림 11.7과 같다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/da1504ef-dcbd-482c-84a7-baa533927125/Untitled.png)

브리지 네트워크는 호스트 커널의 ‘docker0’ 리눅스 브리지에 매핑되며 포트 매핑을 통해 호스트의 이더넷 인터페이스에 매핑될 수 있음

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/786c3b35-b8ce-4a98-87b6-ad4cbbdb2f33/Untitled.png)

`docker network create` 명령을 사용하여 "localnet"이라는 signle-host bridge network를 생성해 보자.

```bash
//Linux
$ docker network create -d bridge localnet

//Windows
> docker network create -d nat localnet
```

새 네트워크가 생성되어 향후 도커 네트워크 ls 명령의 출력에 나타난다. Linux를 사용하는 경우 커널에 새 Linux 브리지도 생성된다.

Linux `brctl` 도구를 사용하여 현재 시스템에 있는 Linux 브리지를 살펴보자. `apt-get install bridge-utils` 또는 Linux Distro와 같은 기능을 사용하여 수동으로 `brctl` 바이너리를 설치해야 할 수 있다.

```bash
$ brctl show
bridge name       bridge id             STP enabled    interfaces
docker0           8000.0242aff9eb4f     no
br-20c2e8ae4bbb   8000.02429636237c     no
```

<br/>

출력에는 두 개의 bridge가 표시된다. 첫 번째 줄은 우리가 이미 알고 있는 "docker0" bridge이다. 이는 도커의 기본 "bridge" 네트워크와 관련이 있습니다. 두 번째 bridge(br-20c2e8ae4bb)는 새로운 `localnet` docker bridge network와 관련이 있다. 둘 다 스패닝 트리를 사용하도록 설정하지 않았으며 연결된 장치도 없다(인터페이스 열).

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3b11f429-946c-4e2d-a518-8021d3645a33/Untitled.png)

<br/>

- Linux bridge

  브릿지(Bridge)는 두 개의 이더넷 세그먼트를 연결하기 위해서 사용한다. 브릿지에서 패킷은 IP주소가 아닌, 이더넷 주소를 기반으로 전송된다. 패킷은 L2영역에서 이루어지기 때문에, 프로토콜에 상관 없이 투명하게 다룰 수 있다.

새로운 컨테이너를 만들어 새로운 `localnet` bridge network에 연결해봅시다.

Windows에서 "`alpine sleep 1d`"를 " `mcr.microsoft.com/powershell:nanoserver pwsh.exe -Command Start-Sleep 86400` "으로 대체해야 합니다.

<br/>

```bash
$ docker container run -d --name c1 \\
  --network localnet \\
  alpine sleep 1d
```

<br/>

이제 이 컨테이너가 `localnet` 네트워크 상에 있게 된다.

`docker network inspect`를 통해 이를 확인할 수 있습니다.

출력에 새 "c1" 컨테이너가 `localnet` bridge/nat 네트워크에 있음을 표시한다.

Linux `brctl show` 명령을 다시 실행하면 `br-20c2e8ae4bbb` bridge에 연결된 c1의 인터페이스를 볼 수 있다.

<br/>

```bash
$ brctl show
bridge name       bridge id           STP enabled     interfaces
br-20c2e8ae4bbb   8000.02429636237c   no              vethe792ac0
docker0           8000.0242aff9eb4f   no
```

<br/>

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/10def709-9e64-4554-8859-1f2fbada28bb/Untitled.png)

동일한 네트워크에 다른 새 컨테이너를 추가하면 "c1" 컨테이너를 이름으로 ping할 수 있을 것입니다. 이는 모든 새 컨테이너가 내장된 Docker DNS 서비스에 자동으로 등록되어 동일한 네트워크에 있는 다른 모든 컨테이너의 이름을 확인할 수 있기 때문입니다.

주의: Linux의 기본 브리지 네트워크는 Docker DNS 서비스를 통한 name resolution을 지원하지 않는다. 다른 모든 사용자 정의 브리지 네트워크도 마찬가지다. 컨테이너가 사용자 정의 localnet 네트워크에 있으므로 다음 데모가 작동한다.

- 원문

  **Beware:** *The default `bridge` network on Linux does not support name resolution via the Docker DNS service. All other user-defined bridge networks do. The following demo will work because the container is on the user-defined `localnet` network.*

<br/><br/>

## Practice

1. "c2"라는 새롭게 통신할 컨테이너를 생성하고 "c1"과 동일한 `localnet` 네트워크에 배치한다.

```bash
//Linux
 $ docker container run -it --name c2 \\
   --network localnet \\
   alpine sh

//Windows
 > docker container run -it --name c2 `
   --network localnet `
   mcr.microsoft.com/powershell:nanoserver
```

터미널이 "c2" 컨테이너로 전환된다.

1. "c2" 컨테이너 내에서 이름으로 "c1" 컨테이너를 ping한다.

```bash
> ping c1
 Pinging c1 [172.26.137.130] with 32 bytes of data:
 Reply from 172.26.137.130: bytes=32 time=1ms TTL=128
 Reply from 172.26.137.130: bytes=32 time=1ms TTL=128
 Control-C
```

이는 c2 컨테이너가 요청을 내부 도커 DNS 서버로 전달하는 local DNS resolver를 실행하고 있기 때문이다. 이 DNS 서버는 `--name` 또는 `--net-alias` 플래그로 시작된 모든 컨테이너에 대한 매핑을 유지한다.

컨테이너에 logged on 상태에서 일부 네트워크 관련 명령을 실행해 보자. Docker 컨테이너 네트워킹에 대해 자세히 알아볼 수 있는 좋은 방법이다.

다음은 이전에 만든 "c2" Windows 컨테이너 내부에서 실행된 ipconfig 명령을 보여준다. Ctrl+P+Q로 컨테이너 밖으로 나와서, IP 주소와 일치하도록 다른 `docker network inspect localnet` 명령을 실행할 수 있다.

```bash
PS C:\\> ipconfig
Windows IP Configuration
Ethernet adapter Ethernet:
   Connection-specific DNS Suffix  . :
   Link-local IPv6 Address . . . . . : fe80::14d1:10c8:f3dc:2eb3%4
   IPv4 Address. . . . . . . . . . . : 172.26.135.0
   Subnet Mask . . . . . . . . . . . : 255.255.240.0
   Default Gateway . . . . . . . . . : 172.26.128.1
```

지금까지 bridge network의 컨테이너는 다른 컨테이너와만 통신할 때 **동일한 네트워크**에 있어야 한다고 했지만, 포트 매핑을 사용하면 이를 해결할 수 있다.

<br/><br/>

### Port Mapping

포트 매핑을 사용하면 컨테이너를 도커 호스트의 포트에 매핑할 수 있다. 도커 호스트에 도달하는 설정된 포트의 모든 트래픽은 해당 컨테이너로 전송된다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c331b846-472b-44a8-a070-3677f2b89a56/Untitled.png)

컨테이너에서 실행 중인 애플리케이션이 포트 80에서 작동하고 있다. 이것은 호스트의 `10.0.0.15` 인터페이스의 포트 `5000`에 매핑된다. 결과적으로 `10.0.0.15:5000`에서 호스트에 도달하는 모든 트래픽이 포트 80의 컨테이너로 리디렉션된다.

웹 서버를 실행하는 컨테이너의 포트 80을 도커 호스트의 포트 5000에 매핑하는 예를 살펴보자.

이 예제는 리눅스에서 NGINX를 사용할 것이다. 만약 당신이 윈도우를 따르고 있다면, 당신은 nginx를 `mcr.microsoft.com/windows/servercore/iis:nanoserver` 과 같은 윈도우 기반 웹 서버 이미지로 대체할 필요가 있을 것이다.

<br/><br/>

### Nginx Practice

1. 새 웹 서버 컨테이너를 실행하고 컨테이너의 포트 80을 도커 호스트의 포트 5000에 매핑한다.

```bash
$ docker container run -d --name web \\
   --network localnet \\
   --publish 5000:80 \\
   nginx
```

1. 포트 매핑을 확인한다.

```bash
$ docker port web
 80/tcp -> 0.0.0.0:5000
```

컨테이너의 포트 80이 도커 호스트의 모든 인터페이스에서 포트 5000에 매핑되어 있음을 나타낸다.

<br/>

1. 웹 브라우저가 도커 호스트의 포트 5000을 가리키도록 하여 해당 설정을 테스트한다. 이 단계를 완료하려면 Docker 호스트의 IP 또는 DNS 이름을 알아야 합니다. Mac 또는 Windows에서 Docker Desktop을 사용하는 경우 localhost:5000 또는 127.0.0.1:5000을 사용할 수 있습니다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bc0135b7-b06c-4944-bb34-f19cca4caf55/Untitled.png)

이제 외부 시스템은 도커 호스트의 TCP 포트 5000에 대한 포트 매핑을 통해 로컬넷 브리지 네트워크에서 실행되는 NGINX 컨테이너에 액세스할 수 있습니다.

이렇게 포트를 매핑하면 작동하지만 투박clunky하고 확장되지 않습니다. 예를 들어 단일 컨테이너만 호스트의 모든 포트에 바인딩할 수 있습니다. 즉, 해당 호스트의 다른 컨테이너가 포트 5000에 바인딩할 수 없습니다. 이것이 단일 호스트 브리지 네트워크가 local 개발과 매우 작은 애플리케이션에만 유용한 이유 중 하나입니다.