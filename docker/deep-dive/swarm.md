# Swarm

Swarm 은 아래의 두 가지로 사용할 수 있습니다.

- 상용 도커 호스트의 보안 클러스터 <br/><small>An enterprise-grade secure cluster of Docker hosts</small>
- 마이크로서비스 앱의 오케스트레이터 <br/><small>An orchestrator of microservices apps</small>


클러스터링 앞 쪽에서, Swarm를 사용해서 하나 이상의 도커 노드를 그룹화하여 클러스터로 관리할 수 있습니다.

Swarm은 따로 설치나 설정 없이도 바로 사용할 수 있는 Out-of-the-box 서비스로,
암호화된 분산 클러스터 스토어, 암호화된 네트워크, 상호 TLS, 보안 클러스터 조인 토큰, 인증서 관리 및 재발급을 쉽게 해주는 PKI를 얻을 수 있습니다.
<small>encrypted distributed cluster store, encrypted networks, mutual TLS, secure cluster join tokens, and a PKI</small>

또, 노드를 중단 없이 (non-disruptively) 추가 및 제거할 수도 있습니다.

오케스트레이션 프론트에서 스웜은 복잡한 마이크로서비스 앱을 쉽게 배포하고 관리할 수 있습니다.

네이티브 도커 명령으로 선언 파일로 앱을 정의하고 스웜에 배포할 수 있습니다.
롤링 업데이트, 롤백, 스케일링 작업까지 수행할 수 있습니다.
다시, 간단한 명령으로 모두 가능합니다.

도커 스웜(Docker Swarm)은 유사한 쿠버네티스(Kubernetes)로 컨테이너화된 애플리케이션을 orchestration 합니다.
쿠버네티스는 훨씬 더 많은 탄력성과 더 활발한 커뮤니티 및 생태계를 가지고 있습니다.
그러나 스웜(Swarm)은 사용이 훨씬 쉽고 많은 중소기업과 애플리케이션 배포에 인기 있는 선택입니다. 학습 스웜(Learning Swarm)은 쿠버네티스를 학습하기 위한 디딤돌입니다.

## Docker Swarm - The Deep Dive

이 장에서 딥 다이브 부분을 다음과 같이 나누어 보겠습니다:

- 스웜 프라이머
- 안전한 스웜 클러스터 구축
- 스웜(Swarm) 서비스 배포
- 문제 해결
- 스웜 백업 및 복구


Swarm primer

클러스터링 전면에서 swarm은 하나 이상의 도커 노드로 구성됩니다.
이러한 노드는 물리적 서버, VM, Raspberry Pi 또는 클라우드 인스턴스가 될 수 있습니다.
유일한 요구 사항은 모두 도커가 설치되어 있고 신뢰할 수 있는 네트워크를 통해 통신할 수 있어야 합니다.

Node는 manager 나 worker로 구성됩니다.
manager: control plane 관리 - cluster의 상태와 worker에게 task를 보내는 것과 같은 것
worker: manager로부터 작업을 수락하고 실행

swarm의 설정과 상태는 모든 manager들에 복제된 분산 데이터베이스에 저장됩니다.
in-memory에 저장되며 항상 최신 상태를 유지합니다.
하지만, 가장 좋은 것은 설정을 하지 않게끔 만드는 것입니다.
Swarm의 일부로 설치한 뒤 자체적으로 처리되도록 만드는 것이죠.


TLS는 매우 긴밀하게 통합되어 있어서, TLS 없이는 swarm을 구축하는 것이 불가능합니다.
보안을 중요시 여기는 요즘엔 필수적인 기능입니다.
Swarm은 통신을 암호화하고 노드를 인증하고 역할을 승인하기 위해 TLS를 사용합니다.
Automatic key rotation은 또한 금상첨화로 던져집니다.
그리고 가장 좋은 것은 이것이 동작하고 있는지도 모를 정도로 순조롭게 진행됩니다.


Orchestration 앞 단에서, 스웜에 대한 스케줄링의 가장 작은 단위 atomic unit는 Service입니다.
이것은 고급 기능을 컨테이너에 감싸는 더 높은 레벨의 구성입니다.
이러한 기능에는 scaling, rolling updates, simple rollbacks 등이 있습니다.
Service를 향상된 컨테이너로 생각하면 유용합니다.

그림 10.1에는 스웜(swarm)에 대한 높은 수준의 뷰가 나와 있습니다.


프라이머는 이제 됐어요. 몇 가지 예를 들어 손을 더럽히도록 합시다.

## Initializing a new swarm

스웜을 구축하는 과정을 스웜 초기화라고 하며, 크게 보면 총 세 개의 과정으로 진행됩니다.
최초 manager 초기화 > 추가 manager들 등록 > 작업자 가입.
<small>Initialize the first manager > Join additional managers > Join workers > Done.</small>

군집(swarm)에 포함되지 않은 도커 노드는 single-engine mode라고 합니다.
일단 군집에 추가되면 자동으로 swarm mode로 전환됩니다.

single-engine mode로 도커 호스트에서 `docker swarm init` 을 실행하면,
해당 노드가 swarm mode로 전환되고,
새로운 swarm을 생성하며 노드가 스웜의 첫 번째 관리자가 됩니다.

그러면 추가 노드를 작업자 또는 관리자로 조인할 수 있으며,
조인 프로세스는 자동으로 swarm mode로 전환됩니다.

다음 단계는 mgr1에서 새로운 스웜을 초기화합니다.
그런 다음 wrk1, wrk2 및 wrk3에 워커 노드로 합류하여,
프로세스의 일부로 자동으로 swarm mode로 전환합니다.
마지막으로 mgr2 및 mgr3를 추가 관리자로 추가하고 swarm mode로 전환합니다.
절차가 끝나면 6개 노드 모두 스웜 모드에 있으며 동일한 스웜의 일부로 작동합니다.

이 예에서는 그림 10.2에 나와 있는 노드의 이름과 IP 주소를 사용합니다.
사용자의 이름은 다를 수 있습니다.


---

### Locking a Swarm

이러한 기본으로 제공되는 보안에도 불구하고,
이전 manager (old manager)를 다시 시작하거나 이전 백업을 복원하면 클러스터에 충돌이 발생할 수 있습니다.

이전 manager가 다시 조인하면 암호를 해독하여 Raft log time-series database에 액세스할 수 있고, 현재의 Swarm configuration을 오염시키거나 삭제할 수도 있습니다.

이러한 상황을 방지하기 위해 도커는 Autolock 기능으로 스웜을 잠글 수 있습니다.
이는 클러스터에 다시 들어가기 전에 관리자가 키를 제시하도록 강제합니다.

초기화 과정에서 `--autolock` 플래그를 `docker swarm init` 명령에 전달하여 스웜을 잠글 수 있으나, 이미 스웜을 구축했기 때문에 `docker swarm update` 명령을 사용하여 스웜을 잠글 것입니다.

스웜 관리자로부터 다음 명령을 실행합니다.


```bash
$ docker swarm update --autolock=true
Swarm updated.
To unlock a swarm manager after it restarts, run the `docker swarm unlock` command and 
provide the following key:

   SWMKEY-1-XDeU3XC75Ku7rvGXixJ0V7evhDJGvIAvq0D8VuEAEaw

Please remember to store this key in a password manager, since without it you will not be able
to restart the manager.
```

잠금 해제 키는 안전한 장소에 보관해야 합니다. `docker swarm unlock-key` 명령으로 항상 현재 스웜 잠금 해제 키를 확인할 수 있습니다.

관리자 노드 중 하나를 재시작하여 클러스터에 자동으로 재가입되는지 확인합니다. 명령어 앞에 sudo를 붙여야 할 수도 있습니다.

```bash
$ service docker restart

# Try and list the nodes in the swarm.
$ docker node ls
Error response from daemon: Swarm is encrypted and needs to be unlocked before it can be used.
```

Docker service가 관리자에서 다시 시작되었지만 Swarm에 다시 조인하는 것은 허용되지 않았습니다.
다른 관리자 노드에서 `docker node ls` 명령을 실행해서 확인할 수 있습니다.
다시 시작된 관리자는 `down` 과 `unreachable` 로 표시됩니다.

`docker swarm unlock` 명령을 실행하여 재시작된 관리자에 대한 스웜 언락 명령을 실행합니다. 재시작된 manager에 대해 이 명령을 실행하고, 이 때 unlock 키를 함께 제공해야 합니다.


노드는 swarm에 다시 가입할 수 있으며, 만약 `docker node ls` 명령을 하게 되면`ready` 와 `reachable`로 표시 됩니다.

운영 환경에서는 스웜을 잠그고 unlock 키를 보호하는 것이 좋습니다.

## Dedicated manager nodes
기본적으로 manager 노드와 worker 노드는 사용자 응용프로그램을 실행할 수 있습니다.
프로덕션 환경에서는 Swarm을 구성하는 것이 일반적인데, worker만 사용자 애플리케이션을 실행합니다.
이를 통해 관리자는 control-plane 책임에만 집중할 수 있습니다.

모든 관리자에서 다음 세 가지 명령을 실행하여 세 관리자 모두가 응용프로그램 컨테이너를 실행하지 못하도록 합니다.

```bash
$ docker node update --availability drain mgr1
$ docker node update --availability drain mgr2
$ docker node update --availability drain mgr3
```

여러 레플리카를 사용하여 서비스를 배포할 때는 나중에 이를 확인할 수 있습니다.

이제 본격적으로 구축되고 leader와 manager HA의 인프라 개념을 이해했으므로
애플리케이션 측면으로 넘어가겠습니다.

---

- disruptively: 분열시켜, 분열성으로
- momentum: 탄력, 가속도, 운동량
- stepping-stone: 징검다리돌
- primer: 시발체
- swarm: 떼 (cluster of nodes)
- dispatch: (특별한 목적을 위해) 보내다\[파견하다\], 발송하다
- conscious: 의식하는, 자각하는 self-conscious, 의식이 있는
- plaudits: 칭찬, 찬사
- as the icing on the cake: 금상첨화
- get one's hands dirty: do manual, menial(하인), or other hard work.
- has the potential to V: 할 가능성이 있다
- compromise: 타협, 절충해서 나온 것, 타협하다
- to focus solely on

---

