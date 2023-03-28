# Serverless


## Customization At the Edge

: 엣지에서의 사용자 지정

주로 함수와 애플리케이션은 특정 리전에서 배포하곤 하지만, 종종 CloudFront과 같은 몇몇의 서비스를 사용하여 배포할 때는 Edge Location(엣지 로케이션)을 통해 콘텐츠를 배포합니다.

가끔 모던 애플리케이션에서는 그 애플리케이션 자체에 도착하기 전에 Edge에서 어떤 형식(some form)의 로직을 실행하도록 요구하기도 합니다.

이를 바로 Edge Function이라고 합니다.

Edge Function:

- 직접 작성하는 코드이며, CloudFront 배포에 연결될 코드
- 목적: 지연 시간을 최소화하게끔 요청자 주변(사용자 근처)에서 실행
- 두 가지 종류가 존재: CloudFront Function / Lambda@Edge
- 전역으로 배포(Globally)되기 때문에 서버를 관리할 필요가 없음

- 사용한 만큼만 비용을 지불
- 완전 서버리스

사용 사례 (PPT 확인)

- CloudFront의 CDN 콘텐츠를 커스터마이징(사용자 지정)하는 경우
- Website Security and Privacy - 웹사이트 보안과 프라이버시
- 엣지에서의 동적 웹 애플리케이션
- 검색 엔진 최적화(SEO)
- 오리진 및 데이터 센터 간 지능형 경로에도 활용
- 엣지에서의 봇 완화 엣지에서의 실시간 이미지 변환
- A/B 테스트 사용자 인증 및 권한 부여
- 사용자 우선순위 지정 사용자 추적 및 분석

*다양한 사용자 지정에 CloudFront 함수와 Lambda@Edge가 활용*

### CloudFront Function

*(CloudFront 함수의 활용과 원리)*

일반적인 CloudFront의 요청 처리 과정 - 도식 참고

1. Viewer Request (뷰어 요청): CloudFront에 클라이언트가 요청을 보내는 것. 클라이언트가 뷰어이기 때문
2. Origin Request : CloudFront가 오리진 요청을 오리진 서버에 전송
3. Origin Response : 서버가 CloudFront에 오리진 응답을 보냄
4. Viewer Response : CloudFront가 클라이언트에게 뷰어 응답을 전송

소개

- JavaScript로 작성된 경량 함수
- High-scale, latency-sensitive CDN Customization: 확장성이 높고 지연 시간에 민감한 CDN 사용자 지정에 사용
- startup 시간은 1밀리초 미만이며 초당 백만 개의 요청을 처리 (millions of requests/second)
- 역할: Viewer 요청과 Viewer 응답 수정할 때만 사용
    - Viewer Request: CloudFront가 뷰어에게 요청을 받은 다음에 **뷰어 요청을 수정**할 수 있음
    - Viewer Response: CloudFront가 뷰어에게 응답을 보내기 전에 뷰어 응답을 수정할 수 있음
- CloudFront Function은 CloudFront의 네이티브 기능 (모든 코드가 CloudFront에서 직접 관리)

**Keyword:** High-scale, latency-sensitive, Only Viewer 요청과 Viewer 응답 수정

→ CloudFront 함수는 고성능, 고확장성이 필요할 때 뷰어 요청과 뷰어 응답에만 사용됨

### Lambda@Edge

Lambda@Edge의 기능은 좀 더 많습니다

- 이 함수는 Node.js나 Python으로 작성
- 초당 수천 개의 요청을 처리 (1000s of requests/seconds)

- 역할: 모든 CloudFront Viewer 요청/응답을 포함한 Origin 요청/응답을 변경할 수 있음
    - Viewer Request: CloudFront가 뷰어에게 요청을 받은 다음 - Viewer Request(뷰어 요청)을 수정할 수 있음
    - Origin Request: CloudFront가 오리진에 요청을 전송하기 전 - Origin Request(뷰어 요청)을 수정할 수 있음
    - Origin Response: CloudFront가 오리진에게 응답 받은 후 - Origin Response(뷰어 응답)수정할 수 있음
    - Viewer Response: CloudFront가 뷰어에게 응답을 보내기 전 - Viewer Response(뷰어 응답) 수정할 수 있음


- Lambda@Edge는 us-east-1 리전에만 작성할 수 있고, CloudFront 배포를 관리하는 리전과 같은 리전
- Lambda@Edge를 작성하면 CloudFront가 모든 로케이션에 해당 함수를 복제


### CloudFront 함수와 Lambda@Edge의 비교

<<table>>

- 가장 눈에 띄는 차이점은 런타임 지원
  - CloudFront는 JavaScript만 Lambda@Edge는 Node.js와 Python을 지원

- # of Requests(확장성): 
  - CloudFront 함수: 수백만 개 요청 처리(매우 높음)
  - Lambda@Edge: 수천 개 요청 처리

- 트리거 발생 위치도 크게 다름
  - Lambda@Edge: 뷰어 & 오리진 모두에게 영향
  - CloudFront 함수: 뷰어에만 영향

- 최대 실행 시간 ⭐️⭐️⭐️
  - CloudFront 함수: 1밀리초 미만 소요 (아주 빠르고 간단)
  - Lambda@Edge: 5~10초 소요 -> 여러 로직을 실행할 수 있음


### 사용 사례

**CloudFront 함수**
  - 캐시 키를 정규화: 요청 속성을 변환하여 최적의 캐시 키를 생성
  - 헤더 조작: 요청이나 응답에 HTTP 헤더를 삽입, 수정, 삭제하도록 헤더를 조작하고 URL을 다시 쓰거나 리디렉션
  - URL rewrite or redirect
  - 요청 인증 및 인가:요청을 허용 또는 거부하기 위해 JWT를 생성하거나 검증하는 요청 인증 및 권한 부여에도 사용
  -> 모든 작업이 1밀리초 이내에 이뤄짐


**Lambda@Edge**
  - 실행 시간: 10초가 걸릴 수도 있음
  - CPU와 메모리 조절 가능 (여러 라이브러리를 로드할 수 있음)
  - 타사 라이브러리에 코드를 의존시킬 수 있음 (가령, SDK에서 다른 AWS 서비스에 액세스할 수 있도록)
  - 네트워크 액세스를 통해 외부 서비스에서 데이터를 처리할 수 있어 대규모 데이터 통합도 수행할 수 있음
  - 파일 시스템이나 HTTP 요청 본문에도 바로 액세스할 수 있음 -> 다양한 사용자 지정이 가능


## Lambda in VPC

Lambda 네트워킹 기초

- Lambda 배포의 기본 설정: VPC 외부에 위치 → VPC 내의 리소스에 액세스할 권한이 없음
    - 즉, 기본적으로 생성된 RDS 나 ElastiCache 내부 로드 밸런서에 액세스할 수 없음
    - 당연히 인터넷의 퍼블릭 API에 액세스하는 것은 가능
    - DynamoDB에 액세스할 수 있는 것도 DynamoDB가 AWS 클라우드의 퍼블릭 리소스이기 때문
    - Private한 RDS 데이터베이스에는 연결할 수 없음
- 접근 허용? VPC에서 Lambda 함수를 시작하도록 설정
    1. VPC ID 정의
        1. Lambda 함수를 실행할 **서브넷과 Lambda 함수에 보안 그룹을 추가**해야 함
        2. → 그럼 Lambda가 서브넷에 ENI(Elastic Network Interface) 생성 
        3. → 그럼 RDS 같은 VPC에서 실행되는 서비스에 액세스할 수 있게 됨

VPC 내 모든 항목에 비공개로 연결하기 위해 Lambda 함수를 프라이빗 서브넷에서 실행

RDS가 프라이빗 서브넷에 있어도 Lambda 함수로 직접 해당 DB에 액세스할 수 있음

하지만, 수 많은 Lambda 함수가 생성/삭제 되면서 (개방된) 연결이 너무 많아짐

→ RDS 데이터베이스의 로드가 상승해 시간 초과 등의 문제 발생

→ RDS Proxy로 해결

### RDS Proxy

- VPC에서 Lambda를 사용하는 대표적인 사용 사례
- 외부 연결을 한곳으로 모으고 RDS 인스턴스 연결 수를 줄여줌
- 아키텍처: Lambda 함수 (N) ⇒ RDS 프록시 → RDS DB 인스턴스

세 가지 장점

- 데이터베이스 연결의 풀링과 공유를 통해 확장성을 향상
- 장애가 발생할 경우 장애 조치 시간을 66%까지 줄여 가용성을 향상시키고 연결을 보존 (RDS, Aurora 모두 적용)
- RDS 프록시 수준에서 IAM 인증을 강제하여 보안 강화 + 자격 증명은 Secrets Manager에 저장

당연히 VPC에서 Lambda 함수를 시작해야 Lambda 함수가 RDS 프록시에 연결 (RDS 프록시는 퍼블릭이 아니기 때문, Lambda 함수를 퍼블릭으로 시작하면 RDS 프록시에 네트워크 연결을 할 수가 없음)

---

### Amazon DynamoDB

- 완전 관리형 데이터베이스, 데이터가 다중 AZ 간에 복제되어 가용성이 높음
- NoSQL 데이터베이스 + 트랜잭션 지원, Cloud Native (AWS의 독점 서비스)
- 데이터베이스가 내부에서 분산되기 때문에 방대한 워크로드로 확장 가능
- 초당 수백만 개의 요청(Millions of requests)을 처리하고 수조 개의 행(trillions of row), 수백 TB의 스토리지를 갖게 됨
- ⭐️⭐️⭐️ 성능: 한 자릿수 밀리초를 자랑하고 일관성 또한 높음 (single-digit millisecond)
- 보안과 관련된 모든 기능은 IAM과 통합 - security, authorization, administration(보안, 권한 부여, 관리 기능)이 포함
- 비용이 적게 들고 오토 스케일링 capabilities(사용 가능)
- 유지 관리나 패치 없이도 항상 사용 가능 - 데이터베이스를 프로비저닝할 필요가 없음. 항상 사용할 수 있으므로 테이블을 생성해 해당 테이블의 용량만 설정하면 됨
- 테이블 클래스 두 종류
    - 액세스가 빈번한 데이터 - Standard 클래스에 저장
    - 액세스가 빈번하지 않는 데이터 - IA 테이블 클래스에 저장

- DynamoDB는 테이블로 구성됨: 데이터베이스를 생성할 필요가 없음
    - Aurora나 RDS와 달리 DynamoDB는 데이터베이스가 이미 존재하는 서비스
- DynamoDB에 테이블을 생성하면 각 테이블에 Primary Key(기본 키)가 부여되는데 Primary Key는 생성 시 결정
- 각 테이블에 추가 항목 데이터 - 행을 무한히 추가할 수 있음
- 각 항목은 Attribute(속성)을 가지며 속성은 열에 표시 - 속성은 나중에 추가할 수도 있고 null이 될 수도 있음(엄청난 장점)