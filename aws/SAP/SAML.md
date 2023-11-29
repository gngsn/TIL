
## SAML 2.0 Federation

*Security Assertion Markup Language 2.0 (SAML 2.0), 보안 검증 마크업 언어*

- ADFS와 같이 여러 자격 증명 제공자가 사용하는 개방형 표준(Open standard by main identity providers)
    - Microsoft Active Directory와 AWS의 자격 증명 제공자인 모든 SAML 2.0 호환 IdP를 지원
- AWS Console, CLI, 임시 자격 증명을 이용하는 모든 API에 엑세스할 수 있음
    - 따라서 여러분의 ADFS 내 모든 직원에 대한 IAM 사용자를 생성할 필요가 없음
    - 물론 IAM과 여러분이 이용하는 SAML 2.0 자격 증명 제공자 상호 간 신뢰를 생성할 필요는 있음
- **Under-the-hood**: 이를 위해 STS 서비스가 제공하는 AssumeRoleWithSAML API를 이용할 텐데 이 API가 SAML Assertion으로 임시 자격 증명을 제공
- SAML 2.0은 오래된 방식으로 Amazon Single Sign은 서비스가 더욱 새로운 관리형이자 쓰기 쉬운 방법
    - 하지만 여전히 SAML 2.0 Federation에 대해 알아 둬야 할 필요는 있음

## SAML 2.0 Federation — AWS API Access

**Corporate (Identity Provider)**

자격 증명 제공자를 사용하는 회사

<aside>
🔥 포털 혹은 자격 증명 제공자가 존재

1. User → IdP: 사용자가 해당 IdP에 인증 요청을 전송하고 
2. IdP → LDAP-based Identity Store: IdP는 LDAP 기반 자격 증명 스토어 등을 통해 요청을 검증
3. IdP → User: 로그인이 성공적으로 이루어질 경우 SAML Assertion을 반환

</aside>

**SAML Assertion**: 해당 사용자에 대한 인증. 

**AWS Cloud**

사용자가 액세스하고자 하는 S3 버킷이 있는 AWS

<aside>
🔥

</aside>

4. 해당 SAML Assertion으로 사용자는 AssumeRoleWithSAML API의 STS 서비스를 호출할 수 있음. 

5. STS가 이 Assertion을 검증하여 신뢰할 수 있다는 판단이 이뤄지면 임시 보안 자격 증명을 제공 

6. 사용자가 AWS API에 액세스할 수 있게 됨

사용자가 AWS 콘솔에 액세스하려고 하는 과정과도 동일