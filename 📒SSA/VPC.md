# VPC



## What is A VPC❓ 

Amazon Virtual Private Cloud lets you provision a logically isolated section of the Amazon Web Services (AWS) Cloud where you can launch AWS resources in a virtual network that you define.

you have complete control over your virtual networking environment, including selection of your own IP address range, creation of subnets, and configuration of route tables and network gateways.

Additionally, you can create a Hardware Virtual Private Network (VPN) connection between your corporate datacenter and you VPC and leverage the AWS cloud as an extension of your corporate data center



AWS VPC를 사용하면, 논리적으로 독립된 섹션을 제공하면서 당신이 설정한 가상 네트워크에서 AWS resorces를 시작할 수 있다.

사용자 자신의 IP 주소 범위 선택, 서브넷 생성, 경로 테이블 및 네트워크 게이트웨이 구성 등 가상 네트워킹 환경을 완벽하게 제어할 수 있다.

또한 기업의 데이터 센터와 VPC 간에 하드웨어 가상 사설망 연결을 생성하고 기업 데이터 센터의 확장으로 AWS 클라우드를 활용할 수 있다.

![스크린샷 2020-10-20 오후 5.07.15](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 5.07.15.png)



redline : region EC2  (us-east-1)

redline : VPC

stateless ACL

two different subnets : PUBLIC SN, PRIVATE SN

- PUBLIC SN : public subnet. we have a public subnet so Internet traffic is accessible or the internet is accessible for any easy

  ​	인터넷 트래픽을 접근할 수 있거나, 인터넷에 쉽게 접근할 수 있다.

- PRIVATE SN: our EC2 instances cannot access the Internet on their own and you can still connect into these EC2 instances

  ​	EC2 인스턴스는 단독으로 인터넷에 액세스할 수 없으며 이러한 EC2 인스턴스에 계속 연결할 수 있음

![스크린샷 2020-10-20 오후 5.21.48](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 5.21.48.png)

위를 보면 Public SN 과 Private SN의 IP가 다른데, 이건 사설 IP 범위에 따라 설정할 수 있음.

- 10.0.0.0 - 10.255.255.255 (10/8 prefix)

- 172.16.0.0 - 172.31.255.255 (172.16/12 prefix)

- 192.168.0.0 - 192.168.255.255 (192.169/16 prefix)

(**10101100.0001**0000.0.0 ~ **10101100.0001**1111.~.~ / 12자리가 prefix되었다는 의미 )

CIDR.xyz 사이트에서 확인할 수 있음





## What can we do with a VPC❓ 

- Launch instances into a subnet of your choosing
- Assign custom IP address ranges in each subnet
- Configure route tables between subnets
- Create internet gateway and attach it to our VPC
- Much better security control over your AWS resources
- Instances security groups
- Subnet network access control lists (ACLs)



### Default VPC vs Custom VPC

- Default VPC is user friendly, allowing you to immediately deploy instances.

AWS에서 개발자들이 Default VPC를 사용하기 쉽게 제공하기 때문

- All Subnets in default VPC have a route out to the internet

모든 인터넷에 접근할 수 있게 만듦.

- Each EC2 instances has both a public and private IP address

모든 EC2 인스턴스는 public IP와 private IP를 가지고 있어서, 만약 네가 default VPC를 삭제해도 바로 복구할 수 있어서 잘못 삭제가 되어도 문제될 게 없어 





### VPC Feature

#### VPC Peering

- Allows you to connect one VPC with another via a direct network route using private IP addresses.

  : private IP 주소를 사용하여 직접 네트워크 경로를 통해 하나의 VPC를 다른 VPC와 연결할 수 있음.

- Instances behave as if they were on the same private network

  : 인스턴스가 동일한 개인 네트워크에 있는 것처럼 동작함

- You can peer VPC's with other accounts as well as woth other VPCs in the same account.

- Peering is in a star configuration: ie 1 central VPC peers with 4 others. NO TRANSITIVE PEERING❗️ 

- you can peer between regions.



![스크린샷 2020-10-20 오후 5.51.30](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 5.51.30.png)

![스크린샷 2020-10-20 오후 5.52.47](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 5.52.47.png)

A를 통과하지 않아도 B와 C를 연결할 수 있다.



#### Remember the following

- Think of a VPC as a logical datacenter in AWS.
- Consists of IGWs (Or Virtual Private Gateways), Route Tables, Network Access Control Lists, Subnets, and Security Groups
- 1 Subnet = 1 Availability Zone
- Security Groups are Stateful; Network Access Control Lists are Statrless
- NO TRANSITIVE PEERING - 새로운 연결을 만들어줘야 함

 





## Build A Custom VPC - Part1



![스크린샷 2020-10-20 오후 6.02.36](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 6.02.36.png)

![스크린샷 2020-10-20 오후 6.12.06](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 6.12.06.png)

각 서브넷 CIDR 블록의 처음 4개의 IP 주소와 마지막 IP 주소는 사용할 수 없으며 인스턴스에 할당할 수 없다. 예를 들어 CIDR 블록 10.0.0/24가 있는 서브넷에서는 다음과 같은 5개의 IP 주소가 예약된다.

10.0.0.0: 네트워크 주소.

10.0.0.1: VPC 라우터를 위해 AWS에 의해 예약됨.

10.0.0.2: AWS에 의해 예약됨. DNS 서버의 IP 주소는 VPC 네트워크 범위 + 2의 기반이다. 여러 CIDR 블록이 있는 VPC의 경우 DNS 서버의 IP 주소는 기본 CIDR에 위치한다. 우리는 또한 VPC의 모든 CIDR 블록에 대해 각 서브넷 범위의 베이스와 2를 예약한다. 자세한 내용은 Amazon DNS 서버를 참조하십시오.

10.0.0.3: AWS에서 향후 사용을 위해 예약.

10.0.0.255: 네트워크 브로드캐스트 주소. 우리는 VPC에서의 방송을 지원하지 않기 때문에 이 주소를 예약한다.

![스크린샷 2020-10-20 오후 6.14.04](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2020-10-20 오후 6.14.04.png)

아직 GW 설정을 안함

단 하나의 Gateway만 하나의 VPC를 연결할 수 있음



#### Remember the following

- When you create a VPC a default Route Table, Network Access Control List (NACL) and a default Security Group.
- It won't create any subnets, nor will it create a default internet gateway.

- US-East-1A in your AWS account can be a completely different availability zone to US-East-1A in another AWS account. The AZ's are randomized.

- Amazon always reserve **5 IP addresses** within your subnets.

- You can only have **1 Internet Gateway per VPC**.

- Security Groups can't span VPCs.




# Build A Custom VPC - Part2

생성한  





 온-프레미스 데이터베이스 : 클라우드 데이터베이스와는 정반대. 데이터베이스 센터느낌.



Amazon Aurora

다중AZ : 가용영역을 두 개 두고 똑같은 서버를 하나 만들고 이중화를 시켜 하나가 터졌을 때 살아남을 수 있도록 만들어 둠. 그래서 가용성이 높다고 생각하면 되고, 리전마다 개수를 다르게 해뒀기 때문에 오류가 나면 다른 것을 올리는 느낌. 리전 별로 설정하는 게 더 가용성이 높다. AWS Aurora 를 사용하면 여러 리전에 걸쳐 replicas 를 생성할 수 있습니다. 또한 Aurora 는 기본적으로 Cross-Region Disaster Recovery 를 지원하며 문제에서 요구하는 1초의 RPO, 1분의 RTO 를 만족하기 때문에 주어진 상황에 적합한 선택지입니다.



소스 0.0.0.0/0 -> 인 바운드 // 대상 0.0.0.0/0 -> 아웃 바운드

보안 그룹은 상태 저장이라서 인바운드하면 아웃바운드는 알아서 설정됨.

 네트워크 ACL는 상태 비저장이라서 상태 비저장이라서 저장을 안해 인바운드와 아웃바운드를 따로 지정해줘야 함.





**WAF** : CloudFront에 전달되는 HTTP 및 HTTPS 요청을 모니터링할 수 있게 해주고 콘텐츠에 대한 액세스를 제어할 수 있게 해주는 웹 애플리케이션 방화벽입니다. 요청이 시작되는 IP 주소나 쿼리 문자열의 값 등 지정하는 조건에 따라, CloudFront는 요청된 콘텐츠나 HTTP 상태 코드 403 (Forbidden)로 요청에 응답합니다. 또한 요청이 차단될 때 사용자 지정 오류 페이지를 반환하도록 CloudFront를 구성할 수 있습니다. CloudFront 배포 단계에서 액세스를 차단해야하기 때문에 WAF

(웹 애플리케이션❓ ) 방화벽







High Performance -> Cluster



현재 대칭 암호화 키를 하드웨어 보안 모듈 (HSM) --> KMS 사용!

