## Join 기법 비교

조인 기법으로는 NL 조인, 소트 머지 조인, 해시 조인 방식이 있고, 집합적인 사고를 바탕으로 적절한 Join Method 선정이 중요하다.
또한 시스템의 특성을 참고하여 적절한 Join Method 선정 또한 중요하다.

### 조인 기법 비교

|--- | ---| --- |
| NL 조인 | 소트 머지 조인 | 해시 조인 |
|--- | ---| --- |
| First Table | - 2개의 Table을 조인할 경우 먼저 처리되는 테이블을 의미. <br> - Where 절에 상수/바인드 변수 조건이 존재하는 것이 성능상 유리 | - Outer Table <br> - Driving table <br> - Build Input