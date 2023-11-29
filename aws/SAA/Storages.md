### AWS에는 여러 스토리지 옵션

1. Amazon S3: 객체 스토리지로 상당히 구체적인 API, 대부분의 AWS와 연결 가능
2. S3 Glacier: 객체를 아카이브할 때는 서비스를 사용.
3. EBS 볼륨: 한 번에 한 개의 EC2 인스턴스에만 스토리지를 연결할 때 사용. IO1과 IO2 볼륨에 다중 연결 기능을 지원. GP3 볼륨, IO2 볼륨 등 존재
4. EC2 Instance 스토리지: IOPS가 높은 EC2 인스턴스에 네트워크 스토리지가 아닌, 고성능 물리 스토리지를 필요로 하는 경우
5. Amazon EFS: 인스턴스가 네트워크 파일 시스템을 필요로 하며, **다중 가용 영역 간 마운트**해야 하면서, POSIX 파일 시스템을 쓸 때
6. Amazon FSx for Windows Service: Windows 서버 파일 시스템을 필요로 하는 경우
7. FSx for Lustre: 고성능 연산 Linux 파일 시스템이며 Lustre 클라이언트와 호환 가능해야 하는 경우
8. FSx NetApp ONTAP 파일 시스템: 높은 운영 체제 호환성과 네트워크 파일 시스템이 필요할 때면
9. FSx for OpenZFS: 관리형 ZFS 파일 시스템이 필요할 때

### 온프레미스와 AWS 간 스토리지를 연결
10. Storage Gateway
  - S3와 FSx 파일 게이트웨이: Amazon S3 또는 Amazon FSx에 파일을 동기화할 수 있음
  - 볼륨 게이트웨이: 온프레미스 서버에 볼륨을 마운트할 수 있고, 클라우드에 백업도 가능
  - 테이프 게이트웨이: 테이프 형식으로 백업을 실행 
11. AWS Transfer Family: FTP, FTPS SFTP 인터페이스가 필요한 경우
12. DataSync: 온프레미스에서 AWS, 혹은 AWS에서 AWS로 일정에 따라 데이터를 동기화할 때 사용합니다
13. Snowcone, Snowball 또는 Snowmobile: 데이터를 전송에 쓸 네트워크 용량이 없지만, 물리적으로 대용량의 데이터를 옮겨야 할 때. 직접 장치를 주문 후 온프레미스에 설치한 다음 클라우드로 옮겨야 함
  - Snowcone: DataSync 에이전트가 사전 설치되어 온다는 특징이 있음