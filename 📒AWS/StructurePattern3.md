# Structure Pattern

<br/><br/>

## Web System

다섯 배 빠른 AWS 전용 DB엔진 도입하기

<br/>

### Pattern 3. Intra Web System

*목표: 성능 요구사항을 만족하는 설계 - 다섯 배 빠른 AWS 전용 DB 엔진 도입*

<br/>

> #### Enterprise WebSite Condition
>
>
> - 회계, 급여, 수발주 등의 업무 시스템을 AWS에서 구축
> - 처리량이 증가하는 마감 전후에도 온라인 트랜잭션의 응답을 3초 이내로 안정시키고자 함

<br/>

```
#### 핵심 설계 사항

1. Auto Scaling을 사용한 동적 프로비저닝
업무 스케줄이나 피크 시의 리소스 상황에 맞춰 처리 능력을 증감

2. 인메모리 데이터 액세스 사용
높은 빈도로 액세스되는 데이터는 인메모리 캐시를 사용하여 데이터 액세스 지연을 줄임

3. 고속의 RDB 서비스 이용
Amazon RDS for Aurora를 활용
```

<br/><br/>
