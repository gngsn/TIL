# FullText Search

- content

MySQL의 fulltext search 방법에 대해 이해하고 습득하는 것이 해당 포스팅의 목표입니다.

## Full-Text Searches

Full-text index는 MySQL에서 에서 FULLTEXT 타입의 인덱스입니다.

Full-text 인덱스는 InnoDB나 MyISAM 엔진에서만 사용할 수 있으며, CHAR, VARCHAR 혹은 TEXT 타입의 컬럼에서만 생성할 수 있습니다.

MySQL은 빌트인(내장)된 ngram parser를 지원하며, 중국어와 일본어 그리고 한글(CJK)를 지원합니다. 또한, 일본어를 위한 플러그인 파서인 MeCab을 설치할 수도 있습니다.

Full-Text index를 정의하는 방법은 테이블 생성 시, CREATE TABLE 구문에 주거나, 후에 ALTER TABLE 이나 CREATE INDEX 구문을 통해 추가할 수 있다.

대규모 데이터셋의 경우에는 FULLTEXT가 없는 상태로 로딩하고, 로딩한 후에 인덱스를 거는게 인덱스가 걸린 채 로드하는 것보다 훨씬 더 빠릅니다.

전체 텍스트 검색은 MATCH() AGAINST 구문을 사용하여 수행됩니다. MATCH()는 쉼표로 구분되며 검색할 열을 지정합니다.

AGAINST는 검색할 문자열과 수행할 검색 유형을 나타내는 search modifier를 사용합니다.

```sql
MATCH (col1,col2,...)
AGAINST (expr [ IN NATURAL LANGUAGE MODE
                | IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION
                | IN BOOLEAN MODE
                | WITH QUERY EXPANSIONsearch_modifier]
)
```

검색 문자열은 쿼리 평가 중에 일정한 문자열 값이어야 합니다. 예를 들어 테이블 열은 행마다 다를 수 있으므로 이 경우 제외됩니다.

**📌 Natural Type Searches**

자연어 검색은 검색 문자열을 자연어 사용자 언어의 구(자유 텍스트의 구)로 해석합니다.큰따옴표(") 문자를 제외하고 특수 연산자는 없습니다. stopword 목록이 적용됩니다.

전체 텍스트 검색은 `IN NATURAL LANGUAGE MODE` 한정자가 지정되거나 한정자가 지정되지 않은 경우 자연어 검색입니다.

**📌  Boolean Type Searches**

부울 검색은 규칙을 사용하여 검색 문자열을 해석합니다. 특별한 질문 언어의 한 종류입니다. 문자열에 검색할 단어가 포함되어 있습니다.

또한 일치하는 행에 단어가 있거나 없거나, 단어가 평소보다 높거나 낮아야 하는 요구 사항을 지정하는 연산자를 포함할 수 있습니다.

특정 일반 단어(stopwords)는 검색 색인에서 생략되며 검색 문자열에 있는 경우 일치하지 않습니다. `IN BOOLEAN MODE` 한정자는 부울 검색을 지정합니다.

📌 **with Query Expansion**

쿼리 확장 검색은 자연어 검색을 수정한 것입니다. 그런 다음 검색에 의해 반환된 가장 관련성이 높은 행의 단어가 검색 문자열에 추가되고 검색이 다시 수행됩니다. 쿼리는 두 번째 검색에서 행을 반환합니다. `IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION` or `WITH QUERY EXPANSION`  확장 한정자는 쿼리 확장 검색을 지정합니다.
