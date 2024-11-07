# Bring Your Own IP

⭐⭐⭐️

- 자신의 IPv4 이나 IPv6을 AWS에 할당시킬 수 있음 (Able to migrate)
- 필요 케이스?
    - IP 주소의 평판을 유지하고자 할 때 (Keep your IP address reputation)
    - IP 화이트 리스트 변경을 피하고자 할 때
    - IP 주소 변경없이 애플리케이션을 이동시키고자 할 때
    - AWS 를 HOT Standby 로 사용할 때
  

### BYOIP 사전 준비

- 주소 범위는 반드시 지역 내 인터넷 레지스트리 (Regional Internet Registry, RIR) 에 등록되어 있어야 함
  - 현재 지원되는 레지스트리: ARIN(American Registry for Internet Numbers), RIPE NCC(Réseaux IP Européens Network Coordination Centre), APNIC (Asia Pacific Network Information Centre)
- IP 주소 범위의 주소들은 반드시 히스토리 상 문제가 없어야함. AWS는 평판이 좋지 않은 IP 주소 범위를 거부함
- IPv4 주소 범위는 가장 작은 범위로 `/24` 까지를 지정할 수 있음
- IPv6 주소 범위는 가장 작은 범위로 `/48` 까지를 지정할 수 있는데 - 공개적으로 홍보된 (Publicly Advertised) CIDR 인 경우
- IPv6 주소 범위는 가장 작은 범위로 `/56` 까지를 지정할 수 있는데 - 공개적으로 홍보되지 않은 (Publicly Advertised) CIDR 인 경우, 하지만 필요 시 Direct Connect 으로 전파 가능  
  - AWS 가 IP 를 외부로 홍보하기 위해선 이미 등록된 인터넷 리스트를 당신의 허가가 필요함 → ROA 생성
- ROA (Route Origin Authorization) 을 생성해서 Amazon ASN 16509 와 14618 을 당신의 주소 범위에 유효하도록 허가 해주어야 함


### Good to know

- BYOIP 으로 인해 IP 소유권이 나 → AWS 으로 변경되는 건 아님 (여전히 본인이 소유·관리)
- 당신의 주소 범위를 가져오면, AWS 계정에서는 그 Address Pool 인 것처럼 명시함
- EC2 Instances, Network Load Balancers, 그리고 NATS gateways 로 IP 주소를 연결시킬 수 있음
- 당신의 AWS 계정 내 각 리전마다 총 다섯 가지의 IPv4 와 IPv6 주소 범위를 들고 올 수 있음