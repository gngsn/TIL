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

