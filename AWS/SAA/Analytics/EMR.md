# EMR

: Elastic MapReduce

- **빅데이터** 작업을 위한 **하둡 클러스터** 생성에 사용
- 방대의 양의 데이터를 분석 & 처리
- 하둡 클러스터는 프로비저닝해야 하며 수백 개의 EC2 인스턴스로 구성될 수 있음
- 설정이 어려운 <code>Apache Spark</code>, <code>HBase</code>, <code>Presto Apache Flink</code>을 Amazon EMR이 프로비저닝 / 구성 설정을 대신 처리
- 전체 클러스터 자동 확장 가능
- 스팟 인스턴스와 통합되므로 가격 할인 혜택을 받을 수도 있음

⭐️ 사용 사례: 데이터 처리와 기계 학습, 웹 인덱싱 빅데이터 작업 - 하둡, Spark, HBase, Presto, Flink와 같은 빅데이터 관련 기술을 사용

### Node types & purchasing

: EMR은 EC2 인스턴스의 클러스터로 구성

| Node Name | Desc | Running |
|---|---|---|
| Master Node | cluster 관리, coordinate, manage health | 장기 실행 |
| Core Node | 태스크 실행, 데이터를 저장 | 장기 실행 |
| Task Node | 태스크 실행 | 주로 Spot Instance 사용 |

### 구매 옵션 

- On-demand: reliable, predictable, 절대 종료 X
- Reserved: 최소 1년, 비용 크게 절약
  - 가능한 경우에 EMR이 자동으로 예약 인스턴스를 사용
  - 장기 실행해야하는 마스터 노드와 코어 노드에 적합
- Spot Instances: cheaper, can be terminated, less reliable
    - 스팟 인스턴스는 종료될 수 있으므로 신뢰도는 떨어지지만 저렴
    - 태스크 노드에 활용하는 게 좋음

✔️ EMR에서 배포할 때는 장기 실행 클러스터에서 **예약 인스턴스**를 사용하거나 **임시 클러스터**를 사용해 특정 작업을 수행하고 **분석 완료 후에 삭제할 수 있음**