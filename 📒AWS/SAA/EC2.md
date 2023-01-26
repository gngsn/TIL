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
    - Authorised IP ranges – IPv4 and IPv6
    - Control of inbound network (from other to the instance)
    - Control of outbound network (from the instance to other)
