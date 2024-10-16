# IP Addresses - Private vs Public vs Elastic & IPv4 vs IPv6

Subnet 하위에 EC2 인스턴스가 있을 때, Subnet 범위 내의 IP 주소를 할당

만약, 명시적으로 IP를 할당하지 않았다면 동적으로 IP가 자동 할당됨

이를 auto-assign public IP 라고 불림

public IP와 Private IP를 함께 지정될 수 있음

하지만 실제 동작할 public IP 주소를 지정할 수는 없는데, 이는 AWS의 Public IP Pool 내에서 지정될 것임

---

이제 특정 상황을 가정해보자,

만약 Public Subnet 하위에서 유저들에게 노출되고 있던 EC2 인스턴스를 중단했다가 다시 실행한다고 가정해보자

당신의 Private IP는 유지되겠지만 당신의 Public IP는 날라가 버리고 재시동될 때 재할당 받게 됨 (AWS에 의해 할당되기 때문)

이 때 해결법은 → **Elastic IP**

Elastic IP는 일종의 Static IP 로, 당신의 AWS ACCOUNT 내에 위치하면서 당신이 해당 IP를 놓지 않는 이상 당신의 EC2 인스턴스에 붙어있음


### 세 가지 종류의 IP 주소


| Feature                   | **Private**                                        | **Public**                                                                                                  | **Elastic**                                                                                 |
|---------------------------|----------------------------------------------------|-------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| Communication             | Communication within VPC                           | Can communicate over internet                                                                               | Can communicate over internet                                                               |
| Address range             | Gets IP address from subnet range. Ex: 10.200.0.1  | Gets IP address from Amazon Pool within region                                                              | Gets IP address from Amazon Pool within region                                              |
| Instance restart behavior | Once assigned cannot be changed                    | Changes over instance restart                                                                               | Do not change over instance restart. Can be removed anytime.                                |
| Releasing IP              | Released when instance is terminated               | Released to POOL when instance is stopped or terminated                                                     | Not released. Remains in your account. (Billed)                                             |
| Automatic Assignment      | Receives private ip on launch on EC2 instance      | Receives public ip on launch on EC2 instance if “Public ip addressing attribute” is set to true for subnet  | Have to explicitly allocate and attach EIP to EC2 instance. Can be reattached to other EC2  |
| Examples                  | Application servers, databases                     | Web servers, Load Balancers, Websites                                                                       | Web servers, Load Balancers, Websites                                                       |

<br>

**Elastic IP 가격**

Elastic IP 는 실행 중인 EC2 인스턴스와 연결되어 (IP 주소를 사용) 있으면 요금을 청구하지 않지만,

EC2 인스턴스를 사용하다가 멈추면 요금이 부과됨

→ IP 주소를 낭비하기 때문

<br>

### IPv6 Addresses  ⭐️⭐️⭐️

- AWS VPC 는 IPv6 지원
- IPv6는 8 block 의 16 bit 로 구성된 총 128 bit
  - Example: `2001:0db8:85a3:0000:0000:8a2e:0370:7334`
- IPv6는 모두 Public (Private IPv6 는 없음) 이며 전 범위에 대해 Unique 함
  - 즉, 인터넷과 연결될 수 있음
- VPC 는 dual-stack mode 인데, 즉 IPv4와 IPv6를 동시에 가질 수 있다는 의미
- IPv6는 인스턴스를 중지(Stop)하고 시작(Start)할 때 유지되며, 인스턴스를 종료(Terminate)할 때 릴리즈됨

<br>

### IPv4 vs IPv6 Addresses

| IPv4                                                                                                             | IPv6                                                                                                          |
|------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| **Default and required for all VPCs**; cannot be removed.                                                        | Opt-in only  (Optional)                                                                                       |
| The VPC/Subnet CIDR block size can be from `/16` to `/28`.                                                       | The VPC CIDR block size is fixed at `/56`. Subnet block is fixed at `/64`. (`\57`나 `\58` 같은 사이즈를 사용할 수는 없음)   |
| You can choose the private IPv4 CIDR block for your VPC                                                          | IPv6 CIDR block is allocated to ⭐️**VPC from Amazon's pool of IPv6 addresses**.We cannot select the range.    |
| Supports both Private and Public IPs                                                                             | No distinction between public and private IP addresses. IPv6 addresses are public.                            |
| An instance receives an Amazon-provided private DNS hostname that corresponds to its private/Public IPv4 address | Amazon-provided DNS hostnames are not supported.                                                              |
| **Supported** for AWS Site-to-Site VPN connections and customer gateways, NAT devices, and VPC endpoints         | **Not supported** for AWS Site-to-Site VPN connections and customer gateways, NAT devices, and VPC endpoints  |








