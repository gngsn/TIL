<small><i>Data & Analytics</i></small>

# Amazon Athena

Keyword: 서버리스 SQL을 이용한 S3에 저장된 데이터 분석

- S3 버킷에 저장된 데이터 분석에 사용하는 서버리스 쿼리 서비스
  - Athena는 서버리스로 S3 버킷의 데이터를 바로 분석 (따로 로드나 이동 필요 X)
- 데이터를 분석: 표준 SQL 언어로 파일 쿼리 (Presto 엔진에 빌드)
- CSV, JSON, ORC, Avro, Parquet 지원
- Pricing: $5.00 per 스캔된 데이터의 TB
- 보고/대시보드를 위해 Amazon Quicksight와 자주 같이 사용됨

• Use cases: Business intelligence / analytics / reporting, analyze & query VPC Flow Logs, ELB Logs, CloudTrail trails , etc...

## Performance Improvement ⭐️⭐️ 

#### 첫 번째 방법: 적게 스캔할 유형의 데이터를 사용
- Use columnar data for cost-savings (less scan)
  - 열 기반 데이터 유형을 사용하면 필요한 열만 스캔하므로 비용을 절감할 수 있음
  - 비용을 지불할 때 스캔된 데이터의 TB당 가격을 지불하므로 데이터를 적게 스캔할 유형의 데이터를 사용
  - 권장 형식: Apache Parquet과 ORC (엄청난 성능 향상)
  - Glue: 적재(ETL) 작업으로 사용자 데이터(ie. CSV)와 Parquet or ORC간의 데이터 변환에 매우 유용


#### 두 번째 방법: 데이터를 압축해 더 적게 검색
- bzip2, gzip, lz4, snappy, zlip, zstd…

#### 세 번째 방법: 파티션 분할 
- 특정 열을 항상 쿼리한다면 데이터 세트를 분할
- S3 버킷에 있는 전체 경로를 슬래시로 분할
  - 각 슬래시에 다른 열 이름을 붙여 **열별로 특정 값을 저장**


<pre><code>
s3://yourBucket/pathToTable 
               /&lt;PARTITION_COLUMN_NAME&gt;=&lt;VALUE&gt;
                  /&lt;PARTITION_COLUMN_NAME&gt;=&lt;VALUE&gt;
                    /&lt;PARTITION_COLUMN_NAME&gt;=&lt;VALUE&gt;
                      /etc…
</code></pre>

- S3 데이터를 정리하고 분할: 데이터를 쿼리 시 Amazon S3의 어떤 폴더(경로)로 스캔할지 정확히 알 수 있음

Example: `s3://athena-examples/flight/parquet/year=1991/month=1/day=1/`

1. 연도별로 분할 시 Parquet 형식의 전송 데이터에 /year=1991을 입력하면 연도별로 한 개의 폴더가 생성
2. 다음으로 월별 분할을 위해 /month=1을 입력, 일별 분할을 위해 /day=1을 입력할 수 있음
3. 분할 후, Athena에서 쿼리를 실행할 때 특정 연도, 월, 일로 필터링하면
4. Amazon S3의 정확히 어떤 폴더에서 데이터를 가져와야 하는지 알 수 있어 데이터의 서브셋만 복구하면 됨


#### 네 번째 방법: 큰 파일 사용
- 파일이 클수록 스캔과 검색이 쉬우므로 128MB 이상의 파일을 사용 (> 128 MB)
- 오버헤드를 최소화하면 성능을 향상할 수 있음
- S3에 작은 파일이 너무 많으면 Athena 성능이 떨어짐

## Federated Query (연합 쿼리)

- **Data Source Connectors** 사용: 데이터 원본 커넥터, S3뿐만 아니라 어떤 곳의 데이터도 쿼리 가능
  - 관계형 데이터베이스 / 비관계형 데이터베이스 / 객체 / 사용자 지정 데이터 원본 (AWS or on-promises)...
  - Lambda 함수로 다른 서비스에서 **연합 쿼리**를 실행
  - CloutWacth Logs, DyanamoDB RDS 등에서 실행 & 매우 강력
  - ElastiCache, DocumentDB DynamoDB, Redshift, Aurora, SQL 서버, MySQL EMR 서비스의 HBase에서 쿼리를 실행할 수 있음
  - 쿼리 결과는 사후 분석을 위해 Amazon S3 버킷에 저장할 수 있음

