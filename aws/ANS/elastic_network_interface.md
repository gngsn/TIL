# Elastic Network Interface (ENI) deep dive

EC2 인스턴스에는 어떻게 IP 주소가 할당?

-> EC2 인스턴스가 생성될 때, ENI를 통해 IP 주소 할당

서버의 물리 네트워크 카드와 굉장히 비슷

- VPC 내 **Virtual Network Card**를 의미하는 논리 컴포넌트.
- ENI 는 특정 availability zone (AZ) 범위. 

**ENI 가 가질 수 있는 구성 요소:**

- VPC IPv4 주소 범위 내 Primary IPv4 주소.
- VPC IPv6 주소 범위 내 Primary IPv6 주소.
- VPC IPv4 주소 범위 내 하나 이상의 Secondary IPv4 주소.
- Private IPv4 주소 당 하나의 Elastic IP 주소 (IPv4).
- 하나의 Public IPv4.
- 하나 이상의 IPv6 주소.
- **하나 이상의 Security Group** 을 가질 수 있음.
  - Security Group은 5개로 제한되는데, 이 경우 변경될 수 있음.
- ⭐️ **하나의 MAC 주소**
  - 여러 소프트웨어 라이센스들은 MAC 주소에 연결되어 있음 
    → 즉, 새로 시작하는 인스턴스에 기존의 ENI를 붙이면 됨. (소프트웨어를 새로 살 필요가 없음) 
- Source/Destination Check Flag

<table>
<tr>
<td>

```
 +----------- Available Zone ------------+
 |                                       |
 |  +-- EC2 ---+  Eth0 - primary ENI     |
 |  | Instance |  : 192.168.0.31         |
 |  |          |  Eth1 - Secondary ENI   |
 |  +----------+  : 192.168.0.42         |
 |                                       |
 |  +-- EC2 ---+  Eth0 - primary ENI     |
 |  | Instance |                         |
 |  |          |                         |
 |  +----------+                         |
 +---------------------------------------+
```

</td>
<td>

```
 +----------- Available Zone ------------+
 |                                       |
 |  +-- EC2 ---+  Eth0 - primary ENI     |
 |  | Instance |  : 192.168.0.31         |
 |  |          |                         |
 |  +----------+                         |
 |                                       |
 |  +-- EC2 ---+  Eth0 - primary ENI     |
 |  | Instance |                         |
 |  |          |  Eth1 - Secondary ENI   |
 |  +----------+  : 192.168.0.42         |
 +---------------------------------------+
```

</td>
/tr>
</table>

Secondary ENI를 다른 인스턴스에 붙일 수 있지만, Primary ENI를 붙일 수는 없음

### IP Addresses per instance

EC2 인스턴스에 어떤 ENI가 얼마나 붙어있는지 어떻게 알까

→ EC2 인스턴스에 따라 다름

https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/using-eni.html

### ENI Use cases








