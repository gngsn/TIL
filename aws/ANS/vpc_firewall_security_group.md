# VPC Firewall - Security Group

## Firewalls inside VPC

서로 다른 EC2 인스턴스가 서로 통신하려면 어떤 네트워크 설정을 해야할까?

즉, 하나의 머신에서 다른 머신으로 트래픽이 흐를 수 있도록 보장하면 됨

그중, 이번엔 방화벽(Firewall)에 대해 다룸

두 인스턴스가 통신하도록 하는 건,
한 도시에서 다른 도시로 고속도로를 뚫는 것과 다를 바 없으며,
방화벽은 그 고속도로에 설피된 톨게이트임

VPC 에서는 두 종류의 방화벽이 존재

- Security Groups → EC2 인스턴스와 밀접한 관련
- Network ACL (Network Access Control List) → 서브넷과 밀접한 관련

정리해보면,

- EC2 인스턴스로 **들어오는** 트래픽
  - : Outside of VPC → NACL → Security Group → EC2
- EC2 인스턴스에서 **나가는** 트래픽
  - : EC2 → Security Group → NACL → Outside of VPC

<br><img src="./img/vpc_firewall_security_group_img1.png" width="80%" /><br>

<br>

### Security Groups (보안 그룹)

> **Goal**
> - 보안 그룹이 어떻게 동작하는지 반드시 이해해야함

- AWS의 네트워크 보안의 기초 지식이자 네트워킹 이슈 트러블슈팅을 위해 알아야 하는 기초 지식임
- 보안 그룹은 일반적으로 EC2 인스턴스의 특정 포트(Port)에 접근하는 걸 막음

그렇다면 보안 그룹은 어떻게 EC2 인스턴스에 인입/인출되는 것을 컨트롤할까?

<small><i>How to use them to allow, inbound and outbound ports?</i></small>




 


















