# IAM

IAM allows you to manage users and their level of access to the AWS Console.

It is important to understand IAM and how it works, both for the exam and for administrating a company's AWS account in real life



IAM을 사용하면 사용자와 AWS 콘솔에 대한 액세스 수준을 관리할 수 있다.

IAM과 그것이 어떻게 작동하는지 이해하는 것은 시험과 실생활에서 회사의 AWS 계정을 관리하는데 중요하다.

Identity Access Management (IAM) offers the following features;

- Centralised control of your AWS account
- Shared Access to your AWS account
- Granular Permissions
- Identity Federation (including Actice Directory, Facebook, Linkedin etc )
- Multifactor Authentication
- Provide temporary access for users/devices and services where necessary
- Allows you to set up your own password rotation policy
- Integrates with many different AWS servies
- Supports PCI DSS Compliance



- AWS 계정의 중앙 집중식 제어
- AWS 계정에 대한 공유 액세스
- 세부 권한
- ID 연합(Actice 디렉토리, Facebook, Linkedin 등 포함)
- 멀티팩터 인증
- 필요한 경우 사용자/기기 및 서비스에 대한 임시 액세스 제공
- 고유한 암호 순환 정책 설정 가능
- 다양한 AWS 서비스와의 통합
- PCI DSS Compliance 지원





## Key Terminology For IAM

### 1. User

End Users such as people, employees of an organization etc.



### 2. Groups

A collection of users. Each user in the group will inherit the permossions of the group.



### 3. Policies

Policies are made up of documents, called Policy documents. these documents are in a format called JSON and they give permissions as to what a User/Group/Role is able to do.



### 4. Roles

You create roles and then assign them to AWS Resources.





## What is the best way to learn IAM❓ 



# Identity Access Management - LAB

MultiFactor Authentication 덕분에 누군가가 내 아이디와 패스워드를 훔쳐가도 로그인할 수 없음.

-> Manage MFA







- IAM is universal . It does not apply to regions at this time.

- The "root account" is simply the account created when first setup your AWS account. It has complete Admin access.

- New Users have NO permissions when first created.

- New Users are assigned **Access Key ID & Secret Access Keys** s when first created.

- **These are not the same as a password.** You cannot use the Access key ID & Secret Access Key to login in to the console. You can use this to access AWS via the APIs and Command Line, however.

- **You only get to view these once.** If you lose them, you have to regenerate them. So, save the, in a secure location.

- Always setup Multifactor Authentication on your root account. (All you need is a smart phone and you just need to download the Google Authenticator app)
- You can create and customise your own password rotation policies.

