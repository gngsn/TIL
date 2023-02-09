# EBS

: Elastic Block Store. Instance에 붙일 수 있는 Network Driver.

- 목적: EBS 볼륨을 사용하면 인스턴스가 종료된 후에도 데이터를 지속할 수 있음. 즉, 인스턴스 재생성 후 이전 EBS 볼륨 마운트하면 데이터를 다시 받을 수 있음

- 특정 가용 영역에서만 가능 (bound to a specific availability zone)

> CCP(Certified Cloud Practitioner): One EBS can be only mounted to one EC2 instance
> Associate Level(Solutions Architect, Developer, SysOps): multi-attach

- EBS Volume? like network USB stick. (USB 처럼 한 컴퓨터에서 꺼내 다른 컴퓨터로 옮겨(detached/attached) 연결할 수도 있는 장치) 물리적 연결없이 네트워크를 통해 연결.

-> EC2 인스턴스와 네트워크로 연결되기 때문에 지연이 생길 수도 있음

- AZ 고정됨(locked): us-easr-1a는 us-east-1b 에 연결될 수 없음 (but. 스냅샷으로 볼륨을 옮길 수 있음)

- 볼륨이기 때문에 용량을 미리 결정. -> 해당 프로비전 용량에 따라 요금 청구

  - 원하는 양의 GB

  - 원하는 양의 IOPS (I/O per Seconds, 단위 초당 전송 수)

- 하나의 EBS는 하나의 EC2 인스턴스와 연결. 하지만, EC2 인스턴스는 여러 개의 EBS 연결 가능

- EC2 인스턴스에 연결될 필요 없음: EBS 볼륨을 생성 후 연결하지 않고 그대로 둘 수도 있음. 필요한 경우에만 연결이 가능


### `Delete on Termination` option: 기본적으로 볼륨 타입(Volume Type)이 Root에 체크

-> 기본적으로 루트 EBS 볼륨은 인스턴스 종료와 함께 삭제되도록 설정이 됨

=> ⭐️ 인스턴스가 종료될 때 루트 볼륨을 유지하고자 하는 경우, 데이터를 저장하고자 하는 등의 경우에는 루트 볼륨의 종료 시 삭제 속성을 비활성화하면 됨

-> 다른 EBS는 선택되어 있지 않아 자동으로 삭제되지 않음

# EBS 볼륨 타입

- 6가지

1. gp2/gp3 (SSD): 범용 SSD (General Purpose SSD). 

(⭐️ gp2와 IOPS 프로비저닝이 시험에서 가장 중요하게 출제됨)

### gp Series (General Purpose)

특징: 짧은 지연 시간(Cost effective storage), 효율적인 비용 스토리지 (Low-latency)

사용 사례: 시스템 부팅 볼륨, 가상 데스크톱 개발, 개발과 테스트 환경

크기: 1GiB ~ 16TiB

#### gp3

- 최신 세대의 볼륨

- 기본 성능: IOPS - 3,000 / 처리량 - 초당 125MB 제공

- 최대: 각각 IOPS - 16,000 / 처리량 - 초당 1,000MB/s 제공

#### gp2

- 이전 버전으로 볼륨이 더 작음

- 작은 볼륨으로 3,000 IOPS를 낼 수 있고, 최대 IOPS 16,000

- 볼륨과 IOPS가 연결: 1 GB 당 3 IOPS, 만약 5,334GB라고 하면 최대 용량인 16,000 IOPS를 초과


⭐️⭐️⭐️ gp2/gp3는 **비용 효과적인 스토리지** gp3에서는 IOPS와 처리량을 독자적으로 설정할 수 있는 반면, gp2에서는 그 둘이 연결되어 있음


## Provisioned IOPS (PIOPS) SSD

- IOPS 성능을 유지할 필요가 있는 주요 비즈니스 애플리케이션 or 16,000 IOPS 이상을 요하는 애플리케이션에 적합

- 데이터베이스 워크로드에 적합 (DB는 스토리지 성능과 일관성에 아주 민감하기 때문)

-> 즉, 스토리지 성능과 일관성에 아주 민감할 때에는 gp2/gp3 볼륨에서 io1 또는 io2 볼륨으로 바꾸는 것이 해답

### io1/io2

- 최신 세대를 고르는 것이 적절

- 4TB ~ 16TiB

- 최대: 

  - Nitro EC2 인스턴스 - 최대 64,000 IOPS까지 가능 (다른 기기보다 높은 성능)

  - Nitro EC2 인스턴스가 아닌 경우에는 최대 32,000 IOPS까지 지원

- IOPS 스토리지 크기를 독자적으로 증가시킬 수 있음 (like gp3 볼륨)

- io2가 더 합리적: io1과 동일한 비용으로 **내구성**과 **GiB 당 IOPS의 수가 더 높음**

### io2 Block Express

- 4GB ~ 64TB (더 고성능 유형의 볼륨)

- 지연 시간이 밀리초 미만 (Sub-millisecond latency)

- IOPS 와 GB의 비율이 1,000:1일 때, 최대 256,000 IOPS



