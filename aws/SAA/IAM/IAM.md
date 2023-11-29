# IAM

## 03. IAM Roles vs Resource-Based Policies

#### EX. S3 - Cross account(교차 계정) 접근:

1. **IAM 역할이 S3 버킷에 액세스**
   - 역할을 맡으면 기존의 권한을 모두 포기하고 해당 역할에 할당된 권한을 상속하게 됨
   - **⭐️ 해당 역할에 부여된 작업은 할 수 있지만, 기존 권한은 사용할 수 없음**
2. **S3 버킷 정책이 사용자의 액세스**
   - <small><i>리소스에 resource-based policy (example: S3 bucket policy) 추가</i></small>
   - 리소스 기반 정책을 사용하면 본인이 역할을 맡지 않으므로 권한을 포기할 필요가 없음
   - 가령, **계정 A**의 사용자가 **본인 계정의 DynamoDB 테이블**을 스캔 후 **계정 B**의 **S3 버킷**에 넣을 경우
     - *DynamoDB 테이블을 스캔 + 다른 계정의 S3 버킷에 쓰기 작업* 권한 필요
     - 리소스 기반 정책을 사용하는 것이 좋음

- Amazon S3 버킷, SNS topic, SQS queue, Lambda 등에서 지원

이 차이는 Amazon EventBridge에서 사용할 때 가장 크게 드러남

### Amazon EventBridge

- Amazon EventBridge에서 원하는 작업을 하려면, 대상에 대한 권한이 요구됨
- 리소스 기반 정책: Lambda, SNS, SQS, CloudWatch Logs, API Gateway 등
- IAM 역할: Kinesis Data Streams, Systems Manager Run Command, ECS 태스크


## 04. IAM Permissions boundary

- IAM 개체의 최대 권한을 정의
- User와 Role 지원, Group은 지원 X

**Example.**
1. John에게 권한 추가(Add permissions)로 AdministratorAccess에 연결 (슈퍼 사용자)
2. John 계정에 권한 경계로 AmazonS3FullAccess를 지정

### IAM 권한 경계 + AWS Organizations SCP

유효(effective) 권한, 사용자가 주어지는 권한은 아래 세 개의 교집합

- Organizations SCP: 사용자나 그룹에 부여된 자격 증명 기반 정책
- Identity-based policy: 그룹이 아닌 사용자나 역할에만 적용되는 권한
- Permission boundary: 계정상 모든 IAM 개체에 적용되는 Organizations SCP


### IAM Policy Evaluation Logic

<img src="../img/iamEvaluationRule.png" />


## 05. Amazon Cognito

Amazon Cognito를 알아보겠습니다

- **사용자에게 웹 및 모바일 앱과 상호 작용할 수 있는 자격 증명을 부여**
- 일반적으로 AWS 계정 외부 사용자 대상 (모르는 사용자들에게 자격 증명을 부여해 인식; Cognito)

#### Cognito User Pools

- 앱 사용자에게 가입 기능을 제공
- API Gateway & Application Load Balancer 원활한 통합

#### Cognito Identity Pools (Federated Identity):

- Cognito 자격 증명 풀 (페더레이션 자격 증명)
- 앱 사용자에게 임시 AWS 자격 증명을 제공해서 일부 AWS 리소스에 직접 액세스할 수 있도록 함 
- Cognito 사용자 풀과 원활히 통합

⭐️ AWS 외부의 **'수백 명의 사용자' '모바일 사용자' 'SAML을 통한 인증'** 웹과 모바일 앱 사용자를 대상


### Cognito User Pools (CUP) – User Features

- 웹 및 모바일 앱을 대상의 **서버리스 사용자 데이터베이스**
- 사용자 이름 / 이메일 / 비밀번호의 조합으로 간단한 로그인 절차를 정의
- 비밀번호 재설정 / 이메일 및 전화번호 검증 / 사용자 멀티팩터 인증 가능
- 소셜 로그인: Facebook / Google과 통합
- API Gateway / 애플리케이션 로드 밸런서와 통합

### Cognito User Pools (CUP) - Integrations

#### + API Gateway
1. 사용자는 Cognito 사용자 풀에 접속해서 토큰을 받음
2. 검증을 위해 토큰을 API Gateway에 전달
3. 검증 후 사용자 자격 증명으로 변환
4. 백엔드의 Lambda 함수로 전달
5. Lambda 함수는 처리할 사용자가 인증된 사용자 임을 인식

### + Application Load Balancer

1. Cognito 사용자 풀을 애플리케이션 로드 밸런서 위에 배치
2. 애플리케이션이 Cognito 사용자 풀에 연결
3. 애플리케이션 로드 밸런서에 전달해서 유효한 로그인인지 확인
4. 검증 후 요청을 백엔드로 리다이렉트; 사용자의 자격 증명과 함께 추가 헤더를 전송

- API Gateway나 ALB를 통해 사용자를 **한곳**에서 확실히 검증
- 검증 책임을 백엔드로에서 로드를 밸런싱하는 실제 위치(API gateway 또는 ALB)로 옮긴 것

### Cognito Identity Pools (Federated Identities)
- Cognito 자격 증명 풀 = 페더레이션 자격 증명
- API Gateway나 ALB를 통하지 않고 **임시 AWS 자격 증명**을 사용해 AWS 계정에 직접 액세스
- 사용자는 Cognito 사용자 풀 내의 사용자 or 타사 로그인
- 자격 증명에 적용되는 IAM 정책은 Cognito 자격 증명 풀 서비스에 사전 정의되어 있음
- user_id를 기반으로 사용자 정의하여 세분화된 제어 가능
- 게스트 사용자나 특정 역할이 정의되지 않은 인증된 사용자는 **기본 IAM 역할**을 상속
  - 원한다면 기본 IAM 역할을 정의할 수도 있음

<img src="./img/../../img/cognitoIdentityPools.png" />

조건: 웹 & 모바일 앱에서 **Cognito 자격 증명 풀을 사용**하여 S3 버킷 or DynamoDB 테이블에 직접 액세스

1. 웹, 모바일 앱 로그인으로 토큰을 받음
   - 인증 방식은 Cognito 사용자 풀 / 소셜 자격 증명 제공자 / SAML OpenID Connect
2. 토큰을 Cognito 자격 증명 풀 서비스에 전달 -> 임시 AWS 자격 증명과 교환
   - Cognito 자격 증명 풀은 전달 받은 토큰이 올바른지, 즉 유효한 로그인인지 평가
   - 해당 사용자에게 적용되는 IAM 정책을 생성
3. AWS의 S3 버킷이나 DynamoDB 테이블에 직접 액세스
   - IAM 정책이 적용된 임시 자격 증명 덕분

### Cognito Identity Pools Row Level Security in DynamoDB

Cognito 자격 증명 풀을 사용하면 DynamoDB에 행 수준 보안을 설정할 수 있음

<pre><code lang="json">
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "dynamodb:DeleteItem",
                "dynamodb:GetItem",
                "dynamodb:PutItem",
                "dynamodb:Query",
                "dynamodb:UpdateItem"
            ],
            "Resource": ["arn:aws:dynamodb:*:*:table/MyTable"],
            "Condition": {
                "ForAllValues:StringEquals": {
                    "dynamodb:LeadingKeys": [
                        <b>"${cognito-identity.amazonaws.com:sub}"</b>
                    ]
                }
            }
        }
    ]
}
</code></pre>

: DynamoDB의 LeadingKeys가 Cognito 자격 증명 사용자 ID와 같아야 한다는 조건

- 위 정책이 적용된 사용자는 DynamoDB 테이블의 모든 항목을 읽고 쓸 수 있는 것이 아니라, 해당 조건을 통해 액세스를 얻은 항목에만 액세스할 수 있음


## 06. AWS IAM Identity Center

- ⭐️ 여러 AWS 계정을 한 번에 로그인
- 싱글 사인온: 단 한 번만 로그인으로 사용
  - 모든 AWS 계정 및 조직 / 비즈니스 클라우드 애플리케이션 (Salesforce, Box, Microsoft 365) / SAML2.0 통합 가능한 모든 애플리케이션 / EC2 Windows 인스턴스

**Identity providers**
이 로그인을 위해 사용자는 두 위치에 저장: 
 - Built-in identity store in IAM Identity Center
 - 3rd party: Active Directory (AD), OneLogin, Okta

### AWS Identity Center Flow

<img src="../img/identityCenterFlow.png" />

1. Identity Center login page 
2. Store / retrieve User Identities
3. SSO - 다양한 서비스에 통합 사용

단 로그인 시 Permission Sets의 자격 증명이 허락하는 서비스에 제한되어 사용 가능

<img src="../img/identityCenterOU.png" />

1. FullAccess Permission Set 생성
2. Development OU와 연결
3. 사용자에게 FullAccess Permission Set 부여
4. ReadOnlyAccess Permission Set 생성
5. Production OU와 연결
6. 사용자에게 ReadOnlyAccess Permission Set 부여


### Fine-grained Permissions and Assignments

<img src="../img/identityCenterFineGrainedPermission.png" width="60%" />

- 해당 IAM 자격 증명 센터는 당신의 조직과 통합되어 있음
- 데이터베이스 관리자를 위한 권한 셋 - DB Admins - 을 정의할 수 있음
- Dev 계정의 RDS / Aurora나 Prod 계정의 RDS / Aurora에 액세스할 수 있음
- 사융자에 대해 IAM 역할이 자동으로 생성
- 데이터베이스 관리자가 그룹이고 사용자가 해당 그룹에 있을 때, 데이터베이스 관리자의 권한 셋을 할당하면, 사용자가 IAM 자격 증명 센터를 통해 로그인해서 Dev 계정 또는 Prod 계정의 콘솔에 액세스할 때 해당 계정에서 IAM 역할을 자동으로 위임


**Multi-Account Permissions**

- 다중 계정 권한
- 여러분의 조직에서 **여러 계정에 대한 액세스를 관리**할 수 있음
- Permission Sets(권한 셋): 사용자를 그룹에 할당하는 하나 이상의 IAM 정책을 정의
  - AWS에서 사용자가 무엇에 액세스할 수 있는지 정의하기 위함

**Application Assignments**
- **어떤 사용자 또는 그룹**이 **많은 SAML 2.0 business 애플리케이션** 중 어떤 것에 액세스할 수 있는지 정의 (Salesforce, Box, Microsoft 365, …)
- URL, 인증서, 메타데이터가 제공됨 (기본적으로 지원)

**Attribute-Based Access Control (ABAC)**
- Fine-grained permissions based on users’ attributes stored in IAM Identity Center Identity Store
- Example: cost center, title, locale, …
- Use case: Define permissions once, then modify AWS access by changing the attributes

- IAM 자격 증명 센터 스토어에 저장된 사용자 속성을 기반으로 세분화된 권한을 갖게 됨
  - 태그 같은 걸 갖게 되는 것
- Example. 사용자를 비용 센터에 할당하거나, 주니어나 시니어와 같은 직함을 주거나 로케일을 줘서 특정 리전에만 액세스하도록 할 수 있음
- 사용 사례. IAM 권한 셋을 한 번만 정의하고 이 속성을 활용하여 사용자 또는 그룹의 AWS 액세스만 수정


## 07. AWS Directory 서비스

### Microsoft Active Directory & AWS Directory 서비스

Microsoft AD: AD 도메인 서비스를 사용하는 모든 Windows 서버에 사용되는 소프트웨어

Active Directory: 객체 데이터베이스 - 객체: 사용자 계정, 컴퓨터 프린터, 파일 공유, 보안 그룹
- 중앙 집중식 보안 관리로 계정 생성, 권한 할당 등의 작업이 가능
- 모든 객체는 트리로 구성, 트리의 그룹을 Forest 라고 함

### AWS Directory Services

#### ⭐️ Keywords:
- **AWS Managed AD**: AWS 클라우드에서 사용자를 관리하고 **MFA**를 사용해야 할 때
- **AD Connector**: AD 커넥터. 온프레미스에서 사용자를 **프록시**
- **Simple AD**: 온프레미스가 없을 때


#### AWS Managed Microsoft AD
- <b>AWS 자체 액티브 디렉터리</b> + <b>사용자 풀을 가진 온프레미스 AD</b> 사이 신뢰 관계를 구축 <br/>
- MFA(멀티팩터 인증) 지원

<pre>
AWS 관리형 AD가 온프레미스 AD와 상호 신뢰 관계를 구축하게 됨
✔️ AWS 관리형 AD의 인증된 사용자 -> 온프레미스 AD 인증 가능
✔️ 온프레미스 AD 사용자 -> AWS AD에서 인증 가능
</pre>

**✔️ User 관리: AWS 자체 액티브 디렉터리 + 온프레미스 AD**

#### AD Connector
- 디렉터리 게이트웨이: **프록시**(온프레미스 AD로 리다이렉트)
- MFA(멀티팩터 인증) 지원

**✔️ User 관리: 온프레미스 AD에서만 관리**

#### Simple AD
- 온프레미스 AD가 없으나 AWS 클라우드에 액티브 디렉터리가 필요한 경우
- Microsoft AD 사용 X, 온프레미스 AD와 결합 X
- Windows를 실행하는 EC2 인스턴스와 디렉터리를 가까이 위치시키기 위함


### Active Directory Setup

*IAM Identity Center + 액티브 디렉터리 통합 방법*


#### 1. AWS Managed Microsoft AD (Directory Service) 연결
<small>**클라우드에 있는 액티브 디렉터리**에서 사용자를 관리하고 싶을 때</small>

- Integration is out of the box
- IAM Identity Center를 AWS 관리형 Microsoft AD에 통합하고 연결 및 설정

#### 2. Self-Managed Directory 연결
<small>API 호출만 프록시 하고 싶을 때 (지연 시간이 길어지긴 함)</small>

**연결 방법 1. AWS 관리형 Microsoft AD를 사용해 양방향 신뢰 관계를 구축**
<small>Two-way Trust Relationship using AWS Managed Microsoft AD</small>

1. Directory 서비스를 이용하여 관리형 Microsoft AD를 생성
2. 온프레미스에 있는 AD와 관리형 AD 간에 양방향 신뢰 관계를 구축
3. IAM Identity Center에서 싱글 사인온으로 간단히 통합

**연결 방법 2. AD 커넥터를 활용**
<small>AD Connector</small>

: AD 커넥터와 IAM Identity Center와 연결하는 역할을 연결
-> AD 커넥터가 자동으로 모든 요청을 자체 디렉터리로 프록시


## 08. AWS Control Tower

- **모범 사례를 기반**으로 안전하면서 규정을 준수하는 **다중 계정 AWS 환경**을 설정 및 관리
- 다중 계정을 생성하기 위해 AWS Organization 서비스를 사용해 계정 자동 생성

**Benefits:**

- 원하는 모든 것을 미리 구성이 가능
- 가드레일을 사용해 자동으로 지속적인 정책 관리
- 정책 위반을 감지하고 자동으로 교정
- 대화형 대시보드로 모든 계정의 전반적인 규정 준수를 모니터링

### Guardrails

<img src="../img/controlTowerGuardrail.png" />

- Control Tower 환경 내 모든 계정에 관한 지속적인 거버넌스 제공

#### Preventive Guardrail 
- 계정을 무언가로부터 보호하고 제한하는 것이 목적
- AWS Organization 서비스 제한 정책인 SCP을 사용: 모든 계정에 적용
- 예를 들어, 예방 가드레일을 생성해 모든 계정에서 리전을 제한하고 us-east-1과 eu-west-2의 리전에서만 작업하도록 할 수 있음
- Control Tower에서 Organization에 SCPs를 사용하도록 하는 것

#### Detective Guardrail
- using AWS Config (e.g., identify untagged resources)
- AWS Config 서비스를 사용하여 규정을 준수하지 않는 것 탐지
- 가령, 계정에서 태그가 지정되지 않은 리소스를 식별한다고 하면, Control Tower로 탐지 가드레일을 설정하고 AWS Config를 사용하면 모든 멤버 계정에 Config가 배포되어 규칙과 태그가 지정되지 않은 리소스를 모니터링 -> 규정을 준수하지 않으면 SNS 주제를 트리거 해서 관리자로서 알림을 받거나 SNS 주제도 Lambda 함수를 실행해 Lambda 함수가 자동으로 문제를 교정 (태그가 지정되지 않은 리소스에 태그를 추가 등..)
