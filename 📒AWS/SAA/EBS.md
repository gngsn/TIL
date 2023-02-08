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



