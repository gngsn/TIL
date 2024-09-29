# Airflow Architecture

(이번 장은 Airflow 의 운영 관점에서 Airflow를 이해하는 것이 목표입니다)

<pre>
<b>환경 변수 설정 방법</b>

1. Environment variable (`AIRFLOW__[SECTION]__[KEY]`)
2. Command environment variable (`AIRFLOW__[SECTION]__[KEY]_CMD`)
3. In `airflow.cfg`
4. Command in `airflow.cfg`
5. Default value
</pre>

Airflow 는 세 개의 컴포넌트로 구성되어 있습니다.

- Webserver
- Scheduler
- Database

<br/><img src="./img/img1.png" width="60%" /><br/>

`Webserver` 와 `Scheduler` 모두 Airflow process 입니다.
데이터베이스는 분리된 서비스로, `Webserver` 와 `Scheduler에서` 메타데이터를 저장하기 위해 사용됩니다.
DAG들이 정의된 파일은 반드시 `Scheduler`가 접근할 수 있어야 합니다.

