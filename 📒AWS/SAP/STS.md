# STS

*Security Token Service*

## Using STS to Assume a Role

STS는 동일한 계정 내에서 다른 계정 간 역할 인수를 막고 자격 증명 패더레이션을 활성화한다.

- 먼저, **당신의 계정 혹은** 역할을 인수하려는 **교차 계정(cross-account)에 IAM 역할을 정의**한다.
- **어떤 Principals 요소가 해당 IAM 역할에 액세스 할 수 있는지 정의**한다.
- AWS STS(Security Token Service, 보안 토큰 서비스)를 사용해서 임시 자격 증명을 발급. 이 서비스로 AssumeRole API를 사용하여 액세스가 있는 IAM 역할을 부여할 수 있음.
- 임시 자격 증명이기 때문에 15분에서 12시간의 유효 기간을 가진다.
    - 이 보안 자격 증명을 이용하여 대상 역할에 액세스 할 수 있다.

User  —              AssumeRole API          —> AWS STS

         <— temporary security credential — 

…

## Assuming a Role with STS

그럼, 어떤 상황에서 STS를 사용하여 역할을 인수해야 할까? 

- **여러분의 계정의 IAM 사용자가 다른 계정 리소스에 액세스할 수 있도록 권한을 부여할 때 사용**할 수 있다.
- 아니면 타사(Third Parites) AWS 계정 내 IAM 사용자가 여러분의 계정에 엑세스 할 수 있도록 권한을 부여하거나
- **AWS나 AWS 리소스에 제공되는 서비스 접근을 제공**하는데, 서비스 역할을 이용하여 AWS 서비스에 작업을 지정할 때 사용할 수도 있다.
- **외부 인증된 유저의 접근을 위해 자격 증명 패더레이션(identity federation)**을 실행할 때도 사용할 수 있는데, 자격 증명 페더레이션에 대해 자세히 알아야 한다.
- STS에는 active 세션과 역할에 대한 자격 증명을 취소(revoke)하는 기능이 있다. 정책을 변경하고 시간 표시(time statement) 문장을 추가하거나 AWSRevokeOlderSessions 관리형 정책을 사용하면 된다. 시간에 관한 Condition을 사용하면 역할을 더 이상 사용할 수 없다고 지정할 수 있으므로 추가 보안 조치가 가능하다.

**STS를 사용해서 사용자나 애플리케이션, 서비스에 역할을 인수할 시 기존의 권한을 포기하고 해당 역할에 할당된 권한을 얻게 된다.** 

역할과 리소스 기반 정책 중 무엇을 사용할지 판단하는 방법 ?

## Providing Access to an IAM User in Your or Another AWS Account That You Own

이제 여러분의 계정이나 다른 AWS 계정에 있는 IAM 사용자에게 액세스를 부여하는 방법을 자세히 알아보자.(여러분이 신뢰할 수 있는 범위 내에서 이루어지는 작업)

> (Picture) *User Account A → Role, Account A* —(Terminate EC2 Instance)→EC2*
> 

예를 들어 A 계정 혹은 여러분의 또 다른 계정인 A* 계정에 EC2 인스턴스를 종료하는 역할을 생성한다고 해보자. EC2 인스턴스를 종료하는 역할을 생성한다고 해보자. 

**CASE1. 모든 계정은 여러분의 소유일 때 )** 

- 사용자가 역할을 인수한다고 했을 때, 해당 역할에서 받은 권한으로 EC2 인스턴스를 종료할 수 있다.
- **Benefits**
    - **사용자 권한을 명시적으로 부여함으로써 EC2 인스턴스 종료라는 작업을 수행할 역할을 인수**하도록 할 수 있다.
    - 그 밖에도 사용자가 관리 콘솔이나 CLI AssumeRole API를 이용해 역할을 바꾸도록 강제할 수도 있다. (이 자체로 추가 보안 조치가 됨)
    - MFA(멀티 팩터 인증, Multi Factor Authentication)을 추가할 수 있다. 오직 MFA를 사용이 지정된 사용자만이 역할을 인수할 수 있도록 만듦
    - 최소 권한의 법칙을 만족시키고 CloudTrail을 통한 감사 또한 가능 (Least privilege + auditing using CloudTrail)

이러한 중간 단계를 거쳐서 EC2 인스턴스를 종료한다 → 여러 추가 보안 조치를 추가하는 방식으로, AWS 에서 자주 사용하며 솔루션 아키텍트라면 항상 염두해 두어야 하는 부분

## Cross account access with STS

**Production 계정**

1. 관리자는 *Development* 계정에 *productionapp* 버킷에 접근(읽고 씀)할 수 있도록 액세스를 부여하는 **역할**을 생성해야 한다. 
- Role: *UpdateApp*
- S3 bucket: *productionapp*

→ 이 역할을 이용해 S3 버킷에 액세스할 수 있다.

**Development 계정**

1. 관리자는 ***Developers*** 그룹의 구성원이 ***UpdateApp*** 역할을 인수할 수 있도록 권한을 부여해야 한다
- Group: *Developers*

1. **Producton 계정(Role:*updateApp*) ← Development 계정:** 그룹의 사용자, 즉 **Developers** 그룹만이 해당 STS API를 이용하여 역할에 액세스하거나 액세스를 요청할 수 있다
2. **Producton 계정(Role:*updateApp*) → Development 계정:** STS는 임시 자격 증명(Role credential)을 반환한다
3. **Producton 계정(S3 bucket:*productonapp*) ← Development 계정:** 임시 자격 증명을 이용하여 사용자는 S3 버킷에 액세스할 수 있다.

*어렵지만 중요한, 시험에 출제되는 내용, 시작*

## Providing Access to AWS Accounts Owned by Third Parties

타사의 AWS 계정에 액세스를 부여하고자 할 경우 외부 ID라고 하는 추가 요소가 필요

- 신뢰 구역(Zone of trust) = 여러분이 소유한 모든 계정과 조직이 들어간다
- 이 신뢰 구역을 벗어나면 타사라고 한다 → Outside Zone of Trust = 3rd parties
    - 서비스를 제공받는 파트너사가 있거나 컨설팅 회사가 있는 경우, 즉, 해당 컨설팅 회사에 여러분 계정에 대한 액세스를 부여하고자 할 때가 있다.
    - 이들은 여러분의 신뢰 영역 밖에 있음 → 여러분 조직의 일환이 아님
- 이 때 IAM Access Analyzer로 여러분 계정의 신뢰 영역을 벗어나는 리소스가 무엇인지 알 수 있다.
    - 가령 타사에 대해 액세스를 승인해야 할 S3 버킷 등을 구분
- **3rd party 접근 부여**:
    1. **The 3rd party AWS account ID**
        - 타사에게 여러분의 리소스 중 일부에 대한 액세스를 부여할 경우, 당연하게도 타사 AWS 계정 ID가 필요
    2. **An External ID(secret between you and the 3rd party)**
        - 가장 중요한 건 바로 여러분이 외부 ID를 정의해야 한다는 점. 이 외부 ID는 여러분과 타사 간의 비밀로 여러분이 직접 정의한다.
        - 해당 역할에는 그 타사에게만 액세스가 있도록 정의
        - 따라서, 이 외부 ID는 여러분과 타사 간 해당 역할에만 관련이 있음. 또, 외부 ID는 신뢰를 정의하고 역할을 인수할 때 설정되어야 하며, 타사가 반드시 이를 선택해야 한다.
    3. Degine permissions in the IAM policy
        - 여러분은 IAM 정책에서 권한을 정의하기만 하면 된다

왜 External ID (외부 ID)가 중요할까?

## The confused deputy

🟠 Your AWS Account

🟢 Corp’s AWS Account 

🟣 Another AWS Account

(당신의 계정을 공격하려는, 당신이 소유하고 있지 않은 계정, Attacker이며, 공격을 위해서는 가운데 있는 계정을 이용해야 함)

### 공격이 이루어지는 과정 (시험 출제 ⭐️)

**CASE 1. 외부 ID를 사용하지 않으면 발생하는 일 )**

1. 🟠 당신의 계정이 ExampleRole이라는 역할 생성
2. ExampleRole은 🟢 타사 계정으로 인수. 해당 ExampleRole의 ARN이 있으니 해당 역할을 사용하는 여러분의 계정 내 일부 리소스에 액세스할 수 있다.
3. 🟣 외부 계정은 공격 시도: 중간 계정에 Role을 제시하며 이 역할로 공격하려는 계정에 액세스할 수 있다고 할 것. 이 때 공격을 주도하는 계정에서 역할을 주는 대신 중간 계정에 동일한 AWS1:ExampleRole ARN을 제공.
4. 중간 계정은 해당 역할이 좌측 🟠 과 우측 🟣 중 어디에서 왔는지 검증할 수 없음. 따라서 중간 계정은 외부 계정에서 준 그대로 역할을 인수. 해당 역할에 대한 인수가 이루어지며 중간 계정은 우측 계정이 아닌 좌측 계정으로부터 일지도 못한 채 역할을 인수 
5. 중간 계정은 우측에 대해 액세스가 이루어진다고 생각하겠으나, 실제 액세스는 좌측에 대해 이루어지는 것 

이를 **혼동된 대리자(The confused deputy)**라고 함 

(중간에 있는 계정이 혼동된 상태이기 때문)

⇒ 중간 계정은 우측의 계정에서 작업을 수행한다고 생각했지만, 실제로는 좌측의 계정에서 작업을 수행

**CASE 2. 외부 ID를 사용하면 발생하는 일 )**

외부 ID는 여러분의 계정과 중간에 있는 계정이 공유하는 비밀 인수해야 하는 계정 ARN이 있는데 해당 ARN을 인수할 때에 미리 두 계정이 함께 정의한 외부 ID를 입력해야 하는 것.

→ 두 계정 모두 아는 외부 ID를 입력했을 때, 역할에 액세스 할 수 있도록 하는 것

**모든 과정을 알 필요는 없고, External ID가 왜 사용되는지, 왜 중요한지를 알면 됨**

정리: **타사 계정을 사용하면서 여러분의 AWS 계정에 대한 액세스를 부여하고 이 과정의 보안을 유지하려 할 때 STS AssumeRole API와 외부 ID를 이용하도록 권장한다는 것**

## STS Important APIs

- **AssumeRole**: 여러분의 계정 내, 혹은 계정 간 액세스를 돕는 API
- **AssumeRoleWithSAML**: SAML Federation 자격 증명을 위한 API
- **AssumeRoleWithWebIdentity**: (AWS가 권장하지 않으며 대신 Cognito를 권장) IdP를 이용하며 여기에는 Amazon Cognito, Amazon 로그인, Facebook, Google 혹은 그 이외에도 OpenID Connect 호환 IdP(Identity provider)가 포함
- **GetSessionToken**: MFA에 사용. 자격 증명을 얻기 위해 MFA를 사용하는 경우에 해당
- **GetFederationToken API**: Proxy 앱을 사용하는 경우 임시 자격 증명을 얻을 때 사용
    - 예) 회사 네트워크 내 토큰을 분배해야 하는 경우

✍🏻 **핵심. API를 거시적으로 알아두어야 한다. 이 중 네 가지의 API가 가장 중요. 다섯 번째는 시험에 나오지는 않지만 개념은 알아두어야 한다**

# Identity Federation in AWS

- ID Federation은 AWS 외부에 있는 사용자에게 여러분 계정 내 AWS 리소스에 대한 액세스 권한을 줄 때 사용
- 해당 사용자는 이미 여러분의 조직 디렉터리에 존재하니 특정 IAM 사용자를 따로 생성할 필요는 없
    - 사용자 관리가 AWS 외부에서 이루어지기 때문 → 자격 증명 페더레이션이 필요
- 활용 사례:
    - 회사에 Active Directory와 같은 자체 자격 증명 시스템을 두는 경우
    - AWS 리소스에 액세스해야 하는 웹 및 모바일 애플리케이션이 있는 경우
- (Identity Provider와 AWS는 신뢰 관계(trust relationship)가 설정되어 있어야 함
- 해당 자격 증명 제공자로부터 자격 증명을 제공받는 사실을 AWS에 알려야 함
- 이후 사용자가 해당 자격 증명 제공자를 통해 로그인을 하고 AWS에 대한 자격 증명을 반환 받으면 해당 임시 자격 증명을 가지고 AWS에 액세스함
- 자격 증명 Federation에는 여러 유형이 존재:  (시험을 위해서는 네 가지 전부 알아야 함)
    - SAML 2.0
    - Custom Identity Broker (사용자 지정 자격 증명 브로커)
    - Web Identity Federation with(out) Amazon Cognito
    - Single Sign-On (SSO): 가장 최신
