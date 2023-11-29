## Docker Containers Management on AWS

1. Amazon Elastic Container Service (Amazon ECS)
   - Amazon’s own container platform
2. Amazon Elastic Kubernetes Service (Amazon EKS)
   - Amazon’s managed Kubernetes (open source)
3. AWS Fargate
   - Amazon’s own Serverless container platform
   - Works with ECS and with EKS
4. Amazon ECR
   - Store container images

## ECS
:Elastic Container Service

### Launch Type

1. EC2 Launch Type
- 도커 컨테이너는 미리 프로비저닝한 Amazon EC2 인스턴스에 위치
- AWS에서 컨테이너를 실행하면 ECS Cluster에 **ECS Task** 실행 -> EC2 인스턴스 내 ECS Agent
- EC2 Launch Type은 인프라를 직접 프로비저닝하고 유지해야 함
    - 즉, Amazon ECS 및 ECS 클러스터가 여러 EC2 인스턴스로 구성

- ECS 인스턴스는 각각 ECS 에이전트(Agent)를 실행해야 함. 그럼 ECS 에이전트가 Amazon ECS 서비스와 지정된 ECS 클러스터에 각각의 **EC2 인스턴스를 등록**
- 이후, ECS 태스크를 수행하기 시작하면 AWS가 컨테이너를 시작하거나 멈추게 함. 즉 새 도커 컨테이너가 생기면 시간에 따라 EC2 인스턴스에 지정됨
- ECS 태스크를 시작하거나 멈추면 자동으로 위치가 지정


2. Fargate Launch Type

- 인프라를 프로비저닝하지 않아 관리할 EC2 인스턴스가 없음 = 서버리스. (EC2 시작 유형보다 관리가 매우 쉬움)
- Fargate 유형은 ECS 클러스터가 있을 때 ECS 태스크를 정의하는 **태스크 정의**만 생성하면 필요한 CPU나 RAM에 따라 ECS 태스크를 AWS가 대신 실행
- 새 도커 컨테이너를 실행하면 어디서 실행되는지 알리지 않고 그냥 실행됨
- EC2 인스턴스를 관리할 필요가 없음: 특정 작업을 위해 백엔드에 EC2 인스턴스가 생성될 필요도 없고, 확장하려면 태스크 수만 늘리면 됨


### IAM Roles for ECS

1. EC2 Instance Profile (EC2 Launch Type only):
- ECS agent 사용 

- [1] EC2 Launch Type을 사용한다면 EC2 Instance Profile을 생성
- [2] ECS agent가 EC2 Instance Profile을 사용하여 API 호출
- [3] 이후 인스턴스가 저장된 ECS 서비스가 **CloudWatch 로그**에 API 호출로 **컨테이너 로그**를 보내고 **ECR로부터 도커 이미지를 가져옴**

- Secrets Manager나 SSM Parameter Store에서 민감 데이터를 참고하기도 함

1. ECS Task Role

ECS Task는 ECS Task 역할을 가짐 (EC2, Fargate Launch Type 둘 다 적용 가능)

두 개의 태스크가 있다면 각자에 특정 역할을 만들 수 있음
- Amazon S3에 API 호출을 실행하는 Task A는 EC2 Task A Role을 맡고,
- DynamoDB에 API 호출을 실행하는 Task B는 EC2 Task B Role을 맡음
=> 역할이 각자 다른 ECS 서비스에 연결할 수 있게 할 수 있음

- ECS 서비스의 태스크 정의에서 태스크의 역할을 정의


### Load Balancer Integrations
: 로드 밸런서 통합

1. Application Load Balancer

- 애플리케이션 로드 밸런서(ALB): HTTP나 HTTPS 엔드 포인트로 태스크를 활용하기 위해 애플리케이션 로드 밸런서(ALB)를 앞에서 실행하면 모든 사용자가 ALB 및 백엔드의 ECS 태스크에 직접 연결. 대부분의 사용 사례를 지원

- 네트워크 로드 밸런서(NBL): 처리량이 매우 많거나 높은 성능이 요구될 때, 혹은 AWS Private Link와 사용할 때 권장


### Data Volumes (EFS)

- ECS 클러스터에 EC2 인스턴스와 Fargate 시작 유형 둘 다 구현했다고 가정할 때, EC2 태스크에 파일 시스템을 마운트해서 데이터를 공유 시 Amazon EFS 파일 시스템을 사용하는 게 좋음.
  - **네트워크 파일 시스템**이라 EC2와 Fargate 시작 유형 모두 호환이 되며 **EC2 태스크에 파일 시스템을 직접 마운트**할 수 있음
  - 어느 AZ에 실행되는 태스크든 Amazon EFS에 연결되어 있다면 데이터를 공유할 수 있고 원한다면 파일 시스템을 통해 다른 태스크와 연결할 수 있음


- Best Practice: Fargate (서버리스 방식으로 ECS 태스크를 실행) + Amazon EFS(서버리스 파일 시스템 지속성)
  - 서버를 관리할 필요 없고 미리 비용을 지불
  - 미리 프로비저닝하기만 하면 바로 사용할 수 있음
- Usecase: EFS와 ECS를 함께 사용해서 다중 AZ가 공유하는 컨테이너의 영구 스토리지

- Amazon S3는 ECS 태스크에 파일 시스템으로 마운트될 수 없음
