## Hands-On Clean Architecture

한 계좌에서 다른 계좌로 송금하는 유스케이스 구현

### 1. Domain Mondel 구현


### 2. Usecase 둘러보기

1. 입력을 받음
2. 비즈니스 규칙을 검증
3. 모델 상태를 조작
4. 출력 반환

- 비즈니스 규칙 (business rule)을 검증할 책임이 있음
- 입력 유효성 검증은 유스케이스의 역할이 아님