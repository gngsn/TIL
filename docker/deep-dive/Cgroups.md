## Cgroups



### **DESCRIPTION**

> *Control groups, usually referred to as cgroups, are a Linux kernel feature which allow processes to be organized into hierarchical groups whose usage of various types of resources can then be limited and monitored.*

<br/>

### cgroup

cgroup이라고 불리는 **제어 그룹**은, 프로세스들이 다양한 유형의 리소스 사용을 제한하고 모니터링할 수 있게끔 계층적 그룹으로 조직될 수 있도록 하는 리눅스 커널 기능이다. 커널의 cgroup 인터페이스는 cgroupfs라고 불리는 유사 파일 시스템을 통해 제공된다. 그룹화는 코어 cgroup 커널 코드에서 구현되는 반면 리소스 추적 및 제한은 리소스별 하위 시스템 집합(메모리, CPU 등)에서 구현된다.

따라서, cgroup은 **cgroup filesystem**을 통해 규정defined된 **제한이나 한도**parameters **집합**에 바인딩된 프로세스의 모음이다.

<br/>

- Memory, CPU, Network, Device, I/O 등을 제한 할 수 있음

cgroups 을 관리하는 방법

- cgroups 파일 시스템에 직접 접근
- cgmanager 이용
- cgroup-tools 이용

<br/>

### subsystem

서브시스템은 cgroup에서 프로세스의 동작을 수정modifies하는 커널 구성요소이다.

다양한 서브시스템들이 구현되는데, 아래의 동작들을 가능하게 만들어준다.

<br/>

- cgroup이 사용할 수 있는 CPU 시간과 메모리의 양을 제한
- cgroup이 사용하는 CPU 시간을 계산accounting
- cgroup 내의 프로세스들을 중단freezing 하고 재개resuming

<br/>

하위 시스템은 (resource) controller라고도 한다.

컨트롤러에 대한 cgroup은 계층 구조로 정렬된다. 이 계층은 cgroup 파일 시스템 내에서 하위 디렉토리를 생성, 제거 및 이름 변경함으로써 정의됩니다. 계층의 각 수준에서 속성(예: 제한)을 정의할 수 있습니다.

cgroup이 제공하는 제한limits, 제어controls 및 측정하는accounting 것들은 cgroup 아래의 하위 계층 전체에 영향을 미친다. 예를 들어, 상위 수준 cgroup에 제한을 걸면 하위 cgroup에서 그 범위를 초과할 수 없다.

<br/>

<br/>

<br/>

[Cgroup Driver 선택하기](https://tech.kakao.com/2020/06/29/cgroup-driver/)

<br/>

**Cgroup 이란?**

Cgroup은 Control Group의 약자로 다수의 Process가 포함되어 있는 Process Group 단위로 Resource 사용량을 제한하고 격리시키는 Linux의 기능이다.

Resource는 CPU, Memory, Disk, Network를 의미하며, Cgroup은 주로 Container의 Resource 제어를 위해서 많이 사용된다.

<br/>

Container가 생성된다면 생성된 Container의 Process들을 담당하는 Container Cgroup이 생성된다.

Container의 모든 Process들은 해당 Container의 Cgroup에 소속된다.

Container 내부 Process들의 Child Process들은 모두 Container Cgroup에 소속된다.

(Child Process는 Process가 Fork System Call을 통해 생성되는 자식 프로세스를 의미).

따라서 Container의 Resource를 제어하기 위해서는 Container Cgroup을 제어하면 된다.

<br/>

### **Cgroup 제어하기**

**cgroup을 제어하는 방법**

- `cgroupfs`
- `systemd`에서 제공하는 API를 사용

<br/>

✔️ **`cgroupfs` 를 이용하여 Cgroup 제어**

Linux에서는 cgroup을 제어하는 방법으로 `cgroupfs`을 제공.

cgroupfs은 이름에서 알 수 있는 것처럼 cgroup 제어를 위해 탄생한 특수 File System.

cgroup 정보는 Linux Kernel 내부에 관리하고 있는데,

Linux Kernel은 cgroup 제어를 위해 별도의 System Call 제공하는 방식이 아닌 cgroupfs을 제공한다.

cgroup 정보는 cgroupfs의 **Directory와 File로** 나타나며,

cgroup 제어는 Directory 생성, 삭제 및 File의 내용 변경을 통해 이루어진다.

따라서 cgroupfs으로 cgroup을 제어할때는 “mkdir, rmdir, echo”와 같은 명령어들을 사용할 수 있다.

<br/>

<small>내 견해 - syscall이 아닌 파일시스템으로 관리한다는 것이 인상적. 조작을 filesystem의 구조를 통해서 사용하구나 .. 그럼 구조가 궁금하니... 아래 확인</small>

<br/>

```bash
# mount 
...
cgroup on /sys/fs/cgroup/cpu,cpuacct type cgroup (rw,nosuid,nodev,noexec,relatime,cpu,cpuacct)
cgroup on /sys/fs/cgroup/pids type cgroup (rw,nosuid,nodev,noexec,relatime,pids)
cgroup on /sys/fs/cgroup/net_cls,net_prio type cgroup (rw,nosuid,nodev,noexec,relatime,net_cls,net_prio)
cgroup on /sys/fs/cgroup/freezer type cgroup (rw,nosuid,nodev,noexec,relatime,freezer)
cgroup on /sys/fs/cgroup/blkio type cgroup (rw,nosuid,nodev,noexec,relatime,blkio)
cgroup on /sys/fs/cgroup/devices type cgroup (rw,nosuid,nodev,noexec,relatime,devices)
cgroup on /sys/fs/cgroup/cpuset type cgroup (rw,nosuid,nodev,noexec,relatime,cpuset)
cgroup on /sys/fs/cgroup/memory type cgroup (rw,nosuid,nodev,noexec,relatime,memory)
...
```

<br/>

systemd는 `cgroupfs`을 `/sys/fs/cgroup` 하위 경로에 Mount 한다. 따라서 systemd를 사용하는 Linux 환경에서는 User가 별도로 cgroupfs을 Mount하지 않았어도 `/sys/fs/cgroup` 하위 경로에 cgroupfs들이 Mount되어 있다.

<br/>

`mount` 명령어를 통해서 Mount되어 있는 cgroupfs들을 확인해보면, 실제 cgroup이 제어하는 Resource Type 별로 존재한다. cgroupfs은 하나만 Mount 되어 있지 않고, Resource Type 별로 Mount 되어 있다는 것을 알 수 있다.

각 cgroupfs이 담당하는 Resource Type은 Mount Option을 통해서 알 수 있는데, 예를 들어 위 예제의 cgroupfs 중에서 `/sys/fs/cgroup/memory` 경로 mount option에는 `memory` 가 있는데, 이는 해당 cgroupfs이 Memory를 담당하는 cgroupfs이라는 의미이다.

<br/>

**cgroup 생성**

cgroup을 생성할 때는 cgroupfs에 Directory를 생성하면 된다.

즉 cgroupfs안의 Directory는 하나의 cgroup이라는 의미이며, cgroup는 Directory 구조처럼 Tree 형태를 갖는다.