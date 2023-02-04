## EC2 sizing & configuration options

- Operating System ( OS ): (1) Linux, (2) Windows or (3) Mac OS
- How much compute power & cores ( CPU )
- How much random-access memory ( RAM )
- How much storage space:
    - Network-attached ( EBS & EFS )
    - hardware ( EC2 Instance S tore )
- Network card: speed of the card, Public IP address
- Firewall rules: security group
- Bootstrap script (configure at first launch): EC2 User Data

## EC2 User Data

- It is possible to bootstrap our instances using an EC2 User data script.
- bootstrapping means launching commands when a machine starts
- That script is only run once at the instance first start
- EC2 user data is used to automate boot tasks such as:
    - Installing updates
    - Installing software
    - Downloading common files from the internet
    - Anything you can think of
- The EC2 User Data Script runs with the root user

다만 사용자 데이터 스크립트에 작업을 더 추가할수록 부팅 시 인스턴스가 할 일이 더 늘어난다

참고로, EC2 사용자 데이터 스크립트는 루트 계정에서 실행된다.

따라서 모든 명령은 sudo로 해야 한다

## EC2 instance types: example

| Instance | vCPU | Mem (GIB) | Storage | Network Performance | EBS Bandwidth (Mbps) |
| --- | --- | --- | --- | --- | --- |
| t2. micro | 1 | 1 | EBS-Only | Low to Moderate |  |
| t2.xlarge | 4 | 16 | EBS-Only | Moderate |  |
| c5d.4xlarge | 16 | 32 | 1 x 400 NVMe SSD | Up to 10 Gbps | 4,750 |
| r5.16xlarge | 4 | 512 | EBS Only | 20Gbps | 13,600 |
| m5.8xlarge | 32 | 128 | EBS Only | 10Gbps | 6,800 |

수백 개의 EC2 인스턴스 중 예시를 다섯 개 들어보면  

첫 번째 t2. micro 는 아주 심플하다. vCPU 1개와 1GB 메모리입니다. Storage는 오직 EBS만 있고네트워크 성능은 낮을에서 중간 사이입니다.

인스턴스 종류를 증가하는 순간 (예를 들어 여전히 같은 제품군에 있고 즉, t2 제품군에서 t2.xlarge로 증가하면 vCPU 4개와 16GB 메모리입니다. 네트워크 성능은 중간입니다.

완전히 다른 새로운 레벨의 인스턴스를 사용하면, 예를들어 c5d.4xlarge은 굉장히 복잡한데요.

vCPU 16개, 즉 코어 16개입니다. 메모리는 32GB로 훨씬 많고 EC2 인스턴스에 부착된 스토리지인 400 NVMeSSD가 있으며, 네트워크 스토리지와 통신하는 대역폭(4,750)도 좋습니다.

r5.16xlarge와 m5.8xlarge같이 더 다양한  종류의 인스턴스가 있으며, 혹은 여러분의 애플리케이션 성능에 맞는 EC2 인스턴스를 선택하여 주문형 클라우드를 사용할 수도 있다.


## Hands-On: Launching an EC2 Instance running Linux

- We’ll be launching our first virtual server using the AWS Console
- We’ll get a first high-level approach to the various parameters
- We’ll see that our web server is launched using EC2 user data
- We’ll learn how to start / stop / terminate our instance

EC2 생성 실습 시 **User Data**는 EC2 인스턴스가 처음으로 부팅될 때 딱 한 번 실행될 스크립트임.

```java
#!/bin/bash
# install httpd (Linux 2 version)
yum update -y
yum install -y httpd
systemctl start httpd
systemctl enable httpd
echo "<h1>Hello World from $(hostname -f)</h1>" > /var/www/html/index.html
```

이것이 EC2 인스턴스에 웹 서버를 시작하고 파일을 쓸 것입니다.

`Delete on Termination` : 인스턴스를 종료할 때 디스크 또한 비운다는 뜻

### Security Group

- 보안 그룹
- HTTP - 80번 포트 추가, `0.0.0.0/0, ::/0` 는 어디에서나 라는 의미

클라우드는 일회용이기 때문에 Terminate 한다는 것은 매우 흔한 일


## EC2 Instance Types - Overview

- You can use different types of EC2 instances that are optimised for different use cases ([https://aws.amazon.com/ec2/instance-types/](https://aws.amazon.com/ec2/instance-types/))
- AWS has the following naming convention:

```java
m5.2xlarge
```

- m: instance class
    - 범용의 인스턴스
- 5: generation (AWS improves them over time)
- 2xlarge: size within the instance class
    - small, large, 2xlarge ~

### EC2 Instance Types – General Purpose

- Great for a diversity of workloads such as web servers or code repositories
- Balance between:
- Compute
- Memory
- Networking
- In the course, we will be using the t2.micro which is a General Purpose EC2 instance

EC2 Instance Types – Compute Optimized

- Great for compute-intensive tasks that require high performance processors:
    - Batch processing workloads
    - Media transcoding
    - High performance web servers
    - High performance computing (HPC)
    - Scientific modeling & machine learning
    - Dedicated gaming servers

⇒ 모든 좋은 성능의 CPU와 컴퓨팅(compute size)을 요구합니다.

컴퓨터 최적화의 모든 인스턴스는 C로 시작하는 이름을 가진다: ex. C5, C6…

### EC2 Instance Types – Memory Optimized

: 여기서 메모리란 RAM을 의미.

- Fast performance for workloads that process large data sets in memory
- Use cases:
    - High performance, relational/non-relational databases
    - Distributed web scale cache stores
    - In-memory databases optimized for BI (business intelligence)
    - Applications performing real-time processing of big unstructured data

처리하는 유형의 작업에 빠른 성능을 제공

예를 들면 대부분 인메모리 데이터베이스가 되는 고성능의 관계형비관계형 데이터베이스에 사용하고, 일래스틱 캐시로 예를 들 수 있는 분산 웹 스케일 캐시 저장소에도 사용

즉, BI에 최적화된 인메모리 데이터베이스와  대규모 비정형 데이터의 실시간 처리를 실행하는 사이즈가 큰 애플리케이션에도 사용

Memory Optimized를 보면 R~ 로 시작하는 인스턴스 시리즈가 있는데 RAM을 나타냅니다. 하지만 X1이나 대용량 메모리 Z1도 있다. (외울 필요는 없지만 알고 있으면 좋다)

### EC2 Instance Types – Storage Optimized

- Great for storage-intensive tasks that require high, sequential read and write access to large data sets on local storage
- Use cases:
    - High frequency online transaction processing (OLTP) systems
    - Relational & NoSQL databases
    - Cache for in-memory databases (for example, Redis)
    - Data warehousing applications
    - Distributed file systems

참고. 이름이 I, D, 혹은 H로 시작한다.


## Introduction to Security Groups

- Security Groups are the fundamental of network security in AWS
- They control how traffic is allowed into or out of our EC2 Instances. Inbound trafficEC2 InstanceOutbound trafficSecurity Group

WWW. -→  Inbound —  EC2

            ←- Outbound - 

- Security groups only contain rules
- Security groups rules can reference by IP or by security group

## Security Groups Deeper Dive

- Security groups are acting as a “firewall” on EC2 instances
- They regulate:
    - Access to Ports
    - Authorized IP ranges – IPv4 and IPv6
    - Control of inbound network (from other to the instance)
    - Control of outbound network (from the instance to other)


## Security Groups Good to know

- Can be attached to multiple instances
    - EC2와 Security Groups은 N:M 관계일 수 있다.
- Locked down to a region / VPC combination
- Does live “outside” the EC2 – if traffic is blocked the EC2 instance won’t see it
- **It’s good to maintain one separate security group for SSH access**
- If your application is not accessible (time out), then it’s a security group issue
- If your application gives a “connection refused“ error, then it’s an application error or it’s not launched
- All inbound traffic is **blocked** by default
- All outbound traffic is **authorised** by default

## Classic Ports to know

- 22 = SSH (Secure Shell) - log into a Linux instance
- 21 = FTP (File Transfer Protocol) – upload files into a file share
- 22 = SFTP (Secure File Transfer Protocol) – upload files using SSH
- 80 = HTTP – access unsecured websites
- 443 = HTTPS – access secured websites
- 3389 = RDP (Remote Desktop Protocol) – log into a Windows instance


## EC2 Instances Purchasing Options

다양한 워크로드에 따라 AWS EC2에 지정해서 할인을 받고 비용을 최적화할 수 있다

### ✔️ **On-Demand Instances**

- 사용한 대로 지불 - Pay for what you use (필요한 만큼 인스턴스를 실행할 수 있음)
    - Linux or Windows - 1분 이후 초 단위로 청구
    - 다른 모든 운영체제의 경우 1시간 단위로 청구
- 단기적인 워크로드에 사용하기 좋음 (Short workload)
- 비용을 예측할 수 있음 (Predictable pricing)
- 비용이 가장 많이 들지만, 바로 지불할(upfront) 금액은 없음
- 장기적 약정도 없음 (No long-term commitment)
- Recommended
    - 단기적이고 중단 없는 워크로드가 필요 시 (Short-term / Un-interrupted workloads)
    - 애플리케이션이 어떻게 가동될지 예측 불가능할 때 (can’t predict how the application will behave)

### ✔️ **Reserved (1 & 3 years)**

- 기간 1년 혹은 3년. 장기간의 워크로드를 위함 (Long workload)
- **Reserved Instances**: 예약 인스턴스
    - Reserved Instances는 On-Demand에 비해 72% 할인을 제공
    - 특정 인스턴스 속성을 예약 (Instance Type, Region, Tenancy, OS)
    - Reservation Period
        - 예약 기간을 1년이나 3년으로 지정해서 할인을 더 받을 수 있으며, 모두 선결제
        - 1 Year (+discount) or 3 Year (+++discount)
    - Payment Options
        - 결제 옵션은 선결제 없음(+, No Upfront), 부분 선결제(++, Partial Upfront), 모두 선결제(+++, All Upfront, 최대 할인)
    - Reserved Instance’s Scope — Regional or Zonal
        - 인스턴스의 범위를 특정한 리전이나 존으로 지정할 수 있음 ← 즉, 특정 AZ에 있는 예약된 용량을 의미
    - Recommended
        - 사용량이 일정한 애플리케이션에 예약 인스턴스를 사용하는 게 좋음 (가령 database)
    - 예약 인스턴스를 마켓플레이스에서 살 수 있고, 더 이상 필요가 없어지면 팔 수도 있다.

- **Convertible Reserved Instances**
    - 유연한 인스턴스 타입. 특별한 유형의 예약 인스턴스
    - EC2 Instance Type, Instance type, instance family, OS, scope와 tenancy를 변경할 수 있음
    - 시간이 지나면 인스턴스 타입을 변경하길 원한다면 전환형 예약 인스턴스가 적절
    - Up to 66% discount. 유연성이 더 크기 때문에 할인율은 조금 더 적음

*시간에 따라 AWS는 % 할인율을 변경하지만, 그러한 정확한 숫자가 시험에 필요하지는 않습니다.*

### ✔️ Spot Instances

- 최대 90% 할인. 가장 저렴한 할인 폭(Very very Cheap)
- 최대 지불 가격을 정의한 후, 스폿 가격이 그 가격을 넘게 되면 인스턴스가 손실
    - 언제라도 인스턴스들이 손실될 수 있어서 신뢰성이 낮음 (Can lose instances, less reliable)
- MOST cost-efficient instances. 가장 비용효율적인 인스턴스.
- 인스턴스가 고장에 대한 회복력이 있다면 아주 유용
    - Batch Jobs
    - Data analysis
    - 이미지 처리. Image processing
    - 모든 종류의 분산형 워크로드. Any distributed workloads
    - 시작 시간과 종료 시간이 유연한 워크로드. Workloads with a flexible start and end time
- **Not suitable for critical jobs or databases (⭐️)**
    - 중요한 작업이나 데이터베이스에는 적절하지 않음.
- 아주 짧은 워크로드 (Short workloads) (very very short)

### ✔️ Dedicated Hosts

- 전용 호스트는 물리적 서버 전체를 예약해서 인스턴스 배치를 제어할 수 있음
    - EC2 인스턴스 용량이 있는 실제 물리적 서버를 받음
- 법규 준수 요건(Compliance requirements)이 있거나 소켓, 코어, VM 소프트웨어 라이선스를 기준으로 청구되는 기존의 서버에 연결된 소프트웨어 라이선스가 있는 경우 → 물리적 서버에 접근하고 전용 호스트를 갖춰야 하는 경우(활용 사례)들임
- Purchasing Options
    - 온디맨드로 초당 비용을 지불하거나
    - 1년 또는 3년 동안 예약할 수 있음 (No Upfront:선결제 없음, Partial Upfront:부분 선결제, All Upfront:모두 선결제)
- 실제 물리적 서버를 예약하기 때문에 AWS에서 가장 비싼 옵션 (The most expensive option)
- 활용 사례: 라이선싱 모델과 함께 제공되는 소프트웨어인 경우, 즉, 흔히 BYOL(Bring Your Own License) 인 경우, 또는 규정이나 법규를 반드시 준수해야 하는 회사를 갖고 있는 경우


## Which purchasing option is right for me?

어떤 경우게 어떤 구매 옵션을 사용해야 적합할까?

- On demand: 리조트가 있고 원할 때 언제나 리조트에 오고 전체 가격을 지불
- Reserved: 미리 계획을 하고 그 리조트에 아주 오래 체류할 것이라는 걸 알고 있음. 1년 내지 3년. 그리고 오래 체류할 예정이기 때문에 많은 할인을 받음
- Savings Plans: 내 일정한 금액 지출할 것임을 알고 있다고 말하는 것. 그럼 당신은 다음 12개월 동안 매월 300달러를 지출할 예정. 그래서 여러분은 시간이 지나면 객실 타입을 변경할 수 있다. King, Suite, Sea View 등으로 변경할 수 있다. 하지만, 절약 플랜은 호텔에서 특정한 지출액을 약정하라고 말함
- Spot Instances: 빈 객실이 있고 사람들을 끌어 모으기 위해 호텔이 마지막 할인을 제공하는 경우. 빈 객실이 있고 사람들이 그 빈 객실을 얻기 위해 경매를 함. 그래서 많은 할인을 받게 됨. 하지만 이 리조트에서 다른 사람이 여러분보다 객실 요금을 더 많이 낼 의사가 있다면 여러분은 언제라도 쫒겨날 수 있다.
- Dedicated Hosts: 마치 리조트 건물 전체를 예약하려는 것과 같음. 자신만의 하드웨어, 자신만의 리조트를 받음
- Capacity Reservations: 용량 예약은 말하자면 객실을 예약하는데 내가 체류할지 확실치 않다고 말하는 것과 같음. 체류할 수도 있겠지만, 체류하지 않는 경우에도 그 객실을 예약하는 전체 비용을 지불하게 됨/

### 가격 비교 예시 — m4.large 기준 — us-east-1

![스크린샷 2023-02-04 오후 5.50.17.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/edae72ba-fadd-4d07-a004-8ae58f4136ff/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-02-04_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.50.17.png)

예약 인스턴스는 보다시피 가격이 달라짐

→ 1년, 3년 약정, 선결제 없음 또는 전부 선결제의 경우에 다름

→ EC2 절약 플랜도 마찬가지

예약 인스턴스 할인과 같은 걸 알 수 있음

예약된 전환형 인스턴스도 있고, 온디맨드 가격으로 제공되는 전용 호스트도 있음

전용 호스트 예약은 최대 70%까지 할인되고요(왜냐면 호스트를 예약하니까)

그리고 용량 예약은 역시 온디맨드 가격

시험에서는 여러분의 워크로드에 적합한 게 어떤 타입의 인스턴스인지 물어볼 것.