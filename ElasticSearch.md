# ElasticSearch

<br/><br/>

Elasticsearch에서 제공하는 ELK스택은  Elasticsearch, Logstash, Kibana로 구성되어있다.

<br/>

Elasticsearch: 실시간 검색과 분석을 위한 도구
Logstash: 데이터를 가공하기 위한 도구, 모든 종류의 로그를 받고 처리하고 출력하는 도구
Kibana: Logstash로 가공된 데이터를 Elasticsearch로 색인하여 그 결과로 각종 그래프와 도표를 통한 데이터 시각화 기능을 제공하는 도구

<br/>

ELK stack은 모두 오픈소스 기반이며, 데이터 처리의 강력한 도구로 활용된다.

<br/><br/>

## Logstash

Logstash는 **모든 종류의 로그를 받고 처리하고 출력하는 도구**로, 백엔드의 데이터 저장소로는 Elasticsearch, 프론트엔드의 데이터 시각화 도구로 Kibana를 사용한다.

<br/>

<pre>
<code>
<b>권장 사용 스팩</b>

- JDK 1.7.X 이상 권장 
- OpenJDK 또는 Oracle 버전 권장 
</code>
</pre>

<br/>

Logstash는 동작 방식이 단순해서 사용하기 쉽고 프로그램과 연동하기 쉬우며, 다양한 로그 형태가 있다.

Logstash의 기본 동작방식은 다음과 같다.


