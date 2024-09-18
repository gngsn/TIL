# VPC

### Virtual Private Cloud (VPC)

- AmazonVPC = AmazonVirtual Private Cloud
- Launch AWS resources into a virtual network that you've defined
- VPC closely resembles a traditional on-premises network
- VPC benefits of using the scalable infrastructure of AWS


#### LAN

- LAN: Local Area Network
- vLan: Virtual LAN
- LAN 을 AWS VPC 로 바꾸는데 크게 다를게 없음

### AWS Account -> Region & AZ -> VPC

**VPC 범위**: Region Level. VPC 생성 시 특정 AWS region 에 생성된다는 의미

<img src="./img/vpc_01.png" width="70%">

<br>

**그렇다면 왜 여러 VPC가 필요할까?**

논리적으로 단일 VPC에 애플리케이션을 배포할 수 있지만,

Best Practice 를 고려할 때, 애플리케이션이나 프로젝트 단위로 네트워크를 분리시키는게 좋음

따라서 Mumbai 리전의 VPC가 가용성 지대(AZ1~AZ3) 에 걸쳐 있는 것을 볼 수 있음

AWS EC2는 특정 AZ에 위치하는데, 다시 말해, 머신 한 대가 동시에 두 개의 AZ에 있을 수 없음

EC2는 VPC 안에 인스턴스를 부팅하면서, 어떤 리전에서 시작할 지 ⎯ AZ1에서 실행할지 AZ2에서 실행할지 ⎯ 결정

이때, EC2를 실행할 AZ는 Subnet을 이용해 결정됨

VPC 생성 후, 그 이하에 여러 개의 Subnet을 생성할 수 있음

서브넷을 생성하는 동안 어느 Subnet이 될지 결정할 수 있음

→ 여기서 3개의 다른 AZ로 EC2 인스턴스를 만들려면 적어도 3개의 Subnet이 필요

정리하자면, **Subnet은 AZ로 매핑되고 VPC는 AWS 리전으로 매핑됨**
