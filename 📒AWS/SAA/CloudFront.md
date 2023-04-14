## Global Accelerator vs. CloudFront

### 공통점
- AWS 글로벌 네트워크를 사용
- 둘 다 AWS가 생성한 전 세계의 엣지 로케이션을 사용
- DDoS 보호를 위해 둘 다 AWS Shield와 통합

### 차이점

**CloudFront**
- 캐싱 컨텐츠 / 동적 컨텐츠 둘 다 성능 향상에 도움을 줌
- 엣지 로케이션으로부터 제공: 대부분의 경우 캐시된 내용을 엣지로부터 가져와서 전달

**Global Accelerator**
- TCP나 UDP상의 다양한 애플리케이션 성능을 향상
- 패킷은 엣지 로케이션으로부터 하나 이상의 AWS 리전에서 실행되는 애플리케이션으로 프록시 됨
- 모든 요청이 애플리케이션 쪽으로 전달
- 캐싱 불가능
- non-HTTP / static IP address 사용 시 유용
  - 비 HTTP: 게임이나 IoT 또는 Voice over IP 같은 경우
  - 글로벌한 고정 IP를 요구하는 HTTP를 사용할 경우
- 결정적이고 신속한 리전 장애 조치가 필요할 때도 좋음
