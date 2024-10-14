## Subnets, Route Tables and Internet Gateway

VPC를 생성할 때 내부적으로 2가지를 생성

<br/><img src="./img/vpc_route_tables_img1.png" width="80%" /><br/>

1. **Local Route Table**: VPC 내 하위 Subnet 들 사이에서의 통신 담당 
2. **Main Route Table**: VPC에서 어떤 Subnet에 트래픽을 전달할지 결정하는 규칙을 가짐 

기본적으로, Subnet 하위에 있는 EC2 인스턴스는 Subnet이 가지는 IP 범위 내에서 IP 주소를 랜덤으로 할당받음

원한다면, 주어진 IP 범위 내에서 하나의 IP 주소를 골라 할당할 수도 있음

EC2 Instance A가 EC2 Instance B 로 요청을 보내고 싶을 때는 어떻게 할까?

→ Subnet Route Table을 확인

초기 설정을 확인해보면 아래와 같이 설정되어 있음

**Root Route Table**

| Destination     | Target |
|-----------------|--------|
| `10.10.0.0/16`  | Local  |

기본적으로 하나의 엔트리 만을 가지고 있음

`10.10.0.0/16` 으로 들어오는 모든 트래픽은 Local (Route Table) 이 결정한다는 의미

위 엔트리는 해당 VPC 하위의 모든 Subnet의 진입점으로, 
만약 기존으로 생성되는 Root Route Table 이 아닌 Custom Route Table 을 생성한다면, 위 엔트리는 반드시 있어야 함

VPC 하위의 모든 서브넷 사이에는 네트워크 연결이 있어야 한다는 의미

> <small><i>시험에 나올 수 있는 질문 1</i></small>
> Q. VPC 하위 여러 Subnet에 위치한 EC2 인스턴스들은 서로 통신이 가능한 상태인가? 
> 
> \+ 참고로, 네트워크 연결성을 고려할 때에는 방화벽도 고려해야함
>

<small><i>시험에 나올 수 있는 질문 2</i></small>

**Q. EC2 인스턴스가 인터넷에 연결되어 있는가?**

No. 

이유? VPC 자체에 인터넷 게이트웨이가 없음 (VPC는 private IP 주소)

| Destination    | Target            |
|----------------|-------------------|
| `10.10.0.0/16` | Local             |
| `0.0.0.0/0`    | Internet Gateway  |

트래픽이 `0.0.0.0/0` 이라면, 즉 모든 IP 주소에 대해서, Internet Gateway 으로 보냄

하지만, 위 내용은 Subnet이 Public IP 가 아닌 **Priavte IP** 를 가질 경우에만 해당되는 설정

---

만약, Subnet A 하위의 인스턴스만 인터넷에 연결되게 하고 Subnet B 하위의 인스턴스는 격리하고 싶다면?

이럴 경우, Subnet 을 위한 Custom 테이블 생성


<table>
<tr>
<th>Subnet A Route Table</th>
<th>Subnet B Route Table</th>
</tr>
<tr>
<td>

| Destination    | Target            |
|----------------|-------------------|
| `10.10.0.0/16` | Local             |
| `0.0.0.0/0`    | Internet Gateway  |

</td>
<td>

| Destination    | Target            |
|----------------|-------------------|
| `10.10.0.0/16` | Local             |

</td>
</tr>
</table>

이렇게 커스텀 라우트 테이블을 생성했으면 더 이상 메인 루트 테이블은 반영되지 않음


### Route table

당신이 Subnet 의 인 바운드와 아웃바운드 트래픽의 경로를 포함하는 테이블

VPC에는 항상 Main Route Table 이 존재하며, 사용자는 이를 제거할 수 없음

당신이 Subnet을 생성하고 따로 Route Table을 생성하지 않았다면 해당 Subnet은 기본으로 VPC의 Main Route Table을 따라감

Main Route Table를 수정할 수도 있고 Custom Route Table 을 생성할 수도 있음


### Subnets

#### Public Subnet

- Has route for Internet
  - Internet Gateway 와 연결된 Subnet Route Table 을 가지는 Subnet
- Instances with Public IP can communicate to internet
  - 인터넷과 통신하는 인스턴스를 가짐
- Ex: NAT, Web servers, Load balancer
  - 전형적으로 Subnet 내에 NAT Gateway 나 웹 서버, 혹은 로드 밸런서를 가질 것

| Destination     | Target  |
|-----------------|---------|
| `172.31.0.0/16` | local   |
| `0.0.0.0/0`     | igw-xxx |

#### Private Subnet

- No Route to Internet
  - Internet Gateway 와 연결되지 않은 Subnet Route Table 을 가진 Subnet
- Instances receive private IPs
- Typically, uses NAT for instances to have internet access
- Ex: Database App server

| Destination     | Target  |
|-----------------|---------|
| `172.31.0.0/16` | local   |


### Subnets - IPv4

- AWS는 각 Subnet 마다 예약된 5개의 IP주소를 가짐: **CIDR의 가장 첫 4개의 IP + 가장 마지막 IP 주소** 
- 즉, 해당 5개의 IP는 사용할 수 없음
- Ex. CIDR가 `10.0.0.0/24` 라면, 예약 IP는:
  - **`10.0.0.0`**: Network address
  - **`10.0.0.1`**: VPC Router 를 위해 선점
  - **`10.0.0.2`**: Amazon-provided DNS (아마존이 제공하는 DNS에 의해 선점)
  - **`10.0.0.3`**: 추후 추가 IP가 필요할 수 있기 때문에 미리 선점
  - **`10.0.0.255`**: Network Broadcast address. AWS는 VPC 내에서 broadcast를 제공하지 않기 때문에 미리 IP를 선점해둠

> ✨ 시험 TIP
> 
> 만약 당신이 EC2 인스턴스에 할당할 29개의 IP 주소가 필요하다면 Subnet 사이즈를 `/27` (총 32개) 로 결정해서는 안됨.
> 
> 최소 64개의 IP 범위인 `/26`을 선택해야 함 (64-5 = 59 > 29, but 32-5 = 27 < 29)



### IP Address

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

- Private
- Public
- Elastic