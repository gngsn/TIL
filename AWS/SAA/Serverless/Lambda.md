# Lambda


### Lambda vs. Amazon EC2
**Amazon EC2**
- EC2는 클라우드의 가상 서버라서 프로비저닝이 필요 
- 따라서 프로비저닝을 할 메모리와 CPU 크기가 제한됨
- 지속적으로 실행되어야 함
- 최적화를 하려면 효율적으로 시작하고 중단해야 함 (오토 스케일링 그룹)

**AWS Lambda**
- 가상의 함수: 관리할 서버 없이 코드 프로비저닝으로 함수 실행
- 최대 15분
- 온디맨드로 실행: Lambda를 사용하지 않으면 람다 함수가 실행되지 않음
- 비용: 함수가 실행되는 동안만 청구
- 스케일링 자동화 (람다 함수를 동시에 많이 필요로 하는 경우, AWS가 자동으로 람다 함수를 늘려줌)


### Lambda의 장점

- 가격 책정이 아주 쉬워짐
  - Lambda가 수신하는 요청의 횟수에 따라 과금: 호출 횟수, 컴퓨팅 시간, Lambda가 실행된 시간
  - 프리 티어: 1,000,000 AWS Lambda requests and 400,000 GBs of compute time
- 다양한 AWS 서비스와 통합 가능
- Lambda에 다양한 프로그래밍 언어를 사용
  - Node.js (JavaScript), Python, Java (Java 8 compatible), C# (.NET Core), Golang, C# / Powershell, Ruby
  - Custom Runtime API (community supported, example Rust): 사용자 지정 런타임 API
  - Lambda Container Image: Lambda 컨테이너 이미지. 컨테이너 이미지 자체가 Lambda의 런타임 API를 구현해야 함
- CloudWatch와의 모니터링 통합도 쉬움
- 함수당 더 많은 리소스를 프로비저닝 하려면 함수당 최대 10GB의 램을 프로비저닝 가능
- 함수의 RAM을 증가시키면 CPU 및 네트워크의 품질과 성능도 함께 향상
- Lambda를 지원하는 언어


### Example 1. 서버리스 섬네일 생성

특정 S3 버킷에서 섬네일을 생성하고 싶을 때

1. Amazon S3에 새 이미지가 업로드되는 이벤트가 생성
2. S3 이벤트 알림을 통해 섬네일을 생성하는 코드의 람다 함수가 작동
3. 해당 섬네일은 다른 S3 버킷이나 같은 S3 버킷으로 푸시 및 업로드 (원본보다 작은 크기)

### Example 2. DynamoDB에 이미지 메타 데이터 삽입

람다 함수로 DynamoDB에 이미지의 이름, 크기, 생성 날짜 등 메타 데이터를 삽입

### Example 3. 서버리스 CRON 작업

- CRON -> 5분마다, 월요일 10시마다 등등을 지정
- 하지만, CRON은 EC2 인스턴스 등 가상 서버에 실행해야 함
- 만약 인스턴스가 실행되지 않거나 CRON이 아무 일도 안 하면 Lambda 시간이 낭비됨

**서버리스 CRON을 만드는 방법**
- CloudWatch 이벤트 규칙 또는 EventBridge 규칙 생성 후 1시간마다 작동되게 설정
=>  1시간마다 람다 함수와 통합되면 태스크를 수행 가능
-> CloudWatch 이벤트, 람다 함수 모두 서버리스

### Lambda 가격 책정 예시

호출 당 요금:
- 첫 1,000,000 번의 요청은 무료
- 백만 건의 요청 마다 $0.20 과금 ($0.0000002 per request)

기간 당 요금: (1 ms 단위 부과)

- 첫 한달간은 컴퓨팅 시간 400,000 second/GB는 무료
  - 함수가 1GB RAM을 가지면 400,000 초
  - 함수가 128 MB RAM을 가지면 3,200,000 초
- 이후 600,000 second/GB는 $1.00 과금

=> 매우매우 저렴한 가격


### Lambda Limitation ⭐️
*한도는 리전당 존재*

#### Execution

✔️ Memory allocation: 128 MB – 10GB (1 MB increments)
: 실행 시 메모리 할당량은 128MB에서 10GB이고 메모리는 1MB씩 증가 (메모리가 증가하면 더 많은 vCPU가 필요)

✔️ Maximum execution time: 900 seconds (15 minutes)
: 최대 실행 시간 900초 (15분)

✔️ Environment variables (4 KB)
: 환경변수는 4KB까지 가질 수 있는데 

✔️ Disk capacity in the “function container” (in /tmp): 512 MB to 10GB
: Disk 임시 공간 "function container": Lambda 함수를 생성하는 동안 큰 파일을 가져올 때 사용할 수 있는 용량이 있으며 /tmp 폴더 내로, 크기는 최대 10GB 가능

✔️ Concurrency executions: 1000 (can be increased)
: 최대 1,000개까지 동시 실행이 가능

요청 시 증가할 수 있지만 동시성은 미리 예약해 두는 것이 좋음


#### Deployment

✔️ Lambda function deployment size (compressed .zip): 50 MB
: 압축 시 배포 최대 크기는 50MB

✔️ Size of uncompressed deployment (code + dependencies): 250 MB
: 압축하지 않은 배포 최대 크기는 250MB

✔️ Can use the /tmp directory to load other files at startup
: 이 용량을 넘는 파일의 경우 /tmp 공간을 사용해야 할 수 있음

✔️ Size of environment variables: 4 KB
: 배포 시에도 환경변수의 한도는 4KB


예를 들어 30GB의 RAM과 30분의 실행 시간이 필요하고 3GB의 큰 파일을 있을 때는 워크로드 처리에 Lambda 함수가 적합하지 않다는 걸 판단

