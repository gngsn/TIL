# AWS CloudFront

- CDN (컨텐츠 전송 네트워크)
- 서로 다른 Edge Location에 웹사이트의 컨텐츠를 **미리 캐싱**하여 읽기 성능을 높이는 것
- Low Latency -> Improve User Experience
- 현 전세계 216개의 엣지 로케이션로 구성
- DDoS 공격의 보호를 받음 / Shield 통합 / WAF(어플리케이션 방화벽)

<pre>
<b>📌 EX. 가령 호주에 위치한 S3 버킷에 웹사이트를 미국에서 호출</b>

1. 미국에 있는 사용자가 컨텐츠를 요청 (미국에 있는 CloudFront Edge에 요청)
2. CloudFront가 호주에서 해당 컨텐츠를 호출 후 패치
3. 요청자에게 전송
4. 이후, 미국의 다른 사용자가 똑같은 컨텐츠를 요청한다면, 호주에서 출발하지 않고 미국 지점의 엣지에서 직접 컨텐츠를 제공
</pre>

<br/><br/>

## CloudFront – Origins
*CloudFront의 원본 제공 방식*

### S3 버킷
- CloudFront를 통해 파일을 분산하고 캐싱 가능하게 함
- 버킷에는 CloudFront만 접근할 수 있게 보장
  - OAC:Origin Access Control(원본 접근 제어), 기존의 OAI를 대체
- Ingress: CloudFront를 통해 버킷에 데이터를 업로드

### Custom Origin, 사용자 정의 원본 (HTTP)
- ALB, EC2 Instance, S3 Website, Any HTTP backend

<br/><br/>

## CloudFront vs. S3 CRR(Cross Region Replication, 교차 리전 복제)

| CloudFront | Cross Region Replication |
|---|---|
| <ul><li>전세계의 엣지 네트워크를 이용</li><li>Edge Location에 하루 동안 파일들이 캐싱</li></ul> | <ul><li>복제를 원하는 리전마다 따로 설정 (전세계 대상 X)</li><li>Near Real-ime</li><li>캐싱 X, 읽기 전용</li></ul> |
| 전세계를 대상으로 한 정적 컨텐츠를 사용하고자 할 때 용이 | 일부 리전에 동적 컨텐츠를 낮은 지연 시간으로 제공하고자 할 때 유용 |

**⭐️ 즉, CloudFront는 전세계 대상 컨텐츠 전송 네트워크, S3 교차 리전 복제는 다른 리전으로의 버킷 복제**


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
