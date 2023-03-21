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

