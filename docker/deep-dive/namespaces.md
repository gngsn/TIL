## Namespaces



> *A namespace wraps a global system resource in an abstraction that makes it appear to the processes within the namespace that they have their own isolated instance of the global resource. Changes to the global resource are visible to other processes that are members of the namespace, but are invisible to other processes.*

<br/>

네임스페이스는 글로벌 시스템 리소스를 추상화로 래핑하여 네임스페이스 내의 프로세스에 글로벌 리소스의 분리된 인스턴스가 있는 것처럼 보이게 한다.

<br/>

→ 네임스페이스는 글로벌 시스템 리소스를 추상화로 래핑하여 (글로벌 리소스의 분리된 인스턴스인) 네임스페이스 내에 프로세스가 있는 것처럼 보이게 한다.

글로벌 리소스의 변경 사항은 또 다른 네임스페이스의 구성원 프로세스에는 표시되지만, 네임스페이스 외부의 다른 프로세스에는 표시되지 않는다.

네임스페이스의 용도 중 하나는 컨테이너를 구현하는 것이다.

<br/>

| Namespace | Flag            | Page                  | Isolates                             |
| --------- | --------------- | --------------------- | ------------------------------------ |
| Cgroup    | CLONE_NEWCGROUP | cgroup_namespaces(7)  | Cgroup root directory                |
| IPC       | CLONE_NEWIPC    | ipc_namespaces(7)     | System V IPC, POSIX message queues   |
| Network   | CLONE_NEWNET    | network_namespaces(7) | Network devices, stacks, ports, etc. |
| Mount     | CLONE_NEWNS     | mount_namespaces(7)   | Mount points                         |
| PID       | CLONE_NEWPID    | pid_namespaces(7)     | Process IDs                          |
| Time      | CLONE_NEWTIME   | time_namespaces(7)    | Boot and monotonic clocks            |
| User      | CLONE_NEWUSER   | user_namespaces(7)    | User and group IDs                   |
| UTS       | CLONE_NEWUTS    | uts_namespaces(7)     | Hostname and NIS domain name         |

<br/>

### Namespace API



[clone](<https://man7.org/linux/man-pages/man2/clone.2.html>) , [setns](<https://man7.org/linux/man-pages/man2/setns.2.html>), [unshare](<https://man7.org/linux/man-pages/man2/unshare.2.html>), [ioctl](<https://man7.org/linux/man-pages/man2/ioctl.2.html>)