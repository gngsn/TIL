# AWS Hybrid Cloud

: AWS 하이브리드 클라우드(Hybrid Cloud) 권장 -> 일부 인프라는 AWS 클라우드 + 일부는 그대로 온프레미스

### 왜?
- 긴 클라우드 마이그레이션 (Cloud Migrations)
- 보안(Security) 또는 규정 준수(Compliance) 요건
- IT Strategy: 일부 워크로드에만 클라우드를 활용

**S3** -- 독점 스토리지 기술(propriety storage technology, NFS 규정 준수 파일 시스템인 EFS와는 다름) -- **를 온프레미스에 두고 싶다면**?
=> AWS Storage Gateway가 S3와 여러분의 온프레미스 인프라를 연결해줄 수 있음

## AWS Storage Cloud Native Options
- Block: Amazon EBS, EC2 Instance store
- File: Amazon EFS, Amazon FSx
- Object: Amazon S3, Amazon Glacier


