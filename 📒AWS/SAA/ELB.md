Sticky Sessions (Session Affinity)

- stickiness: the same client is always redirected to the same instance.

  -> 로드 밸런서에서 2회 이상의 요청을 보낸 Client에게 동일한 인스턴스를 갖게 하는 것

- CLB와 ALB에서 설정할 수 있음

- how it works? "Cookie", Client에서 로드 밸런서로 요청의 일부로 전송

  - has stickiness and expiration date

- 사례: 사용자의 세션 정보 유지

- Caution: EC2 인스턴스 부하에 불균형을 초래할 수 있음