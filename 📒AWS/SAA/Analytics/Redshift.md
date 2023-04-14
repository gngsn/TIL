# Redshift

- PostgreSQL 기술 기반의 온라인 분석 처리(OLAP) 유형의 데이터베이스 (analytics and data warehousing)
  - 온라인 트랜잭션 처리(OLTP) X
- 다른 어떤 데이터 웨어하우징보다 성능이 10배 이상 좋고 데이터가 PB 규모로 확장
- 열 기반 데이터 스토리지로 성능 향상 가능 (행 기반이 아니라 병렬 쿼리 엔진이 있는 것)
- Redshift 클러스터에서 **프로비저닝**한 인스턴스에 대한 비용만 지불
- 쿼리 수행에 SQL문 사용 가능
- Amazon Quicksight 같은 BI 도구나 Tableau 같은 도구와 통합 가능
- vs Athena: 
  - faster queries: S3의 임시 쿼리라면 Athena가 좋은 사용 사례가 되지만, 쿼리가 많고 복잡하며 조인하거나 집계하는 등 집중적인 데이터 웨어하우스라면 Redshift가 더 좋음
  - joins: S3에서 모든 데이터를 Redshift에 로드 하면, 쿼리가 더 빠르고 조인과 통합을 훨씬 더 빠르게 할 수 있음 
  - aggregations(indexes): Athena에는 없는 인덱스가 있기 때문

<small>BI: business intelligence</small>
