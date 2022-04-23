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


<br/>
<br/>

### 자연어 Natural Language Full-Text Searches

기본적으로 또는 IN NATURAL Language MODE 한정자를 사용하여 MATCH() 함수는 텍스트 컬렉션에 대해 문자열에 대한 자연어 검색을 수행합니다.

집합은 FULLTEXT 색인에 포함된 하나 이상의 열 집합입니다.

하나의 컬렉션은 FULLTEXT 인덱스 내의 하나 혹은 그 이상의 열의 세트이다.

검색 문자열은 AGAINST()의 인수로 제공됩니다.

테이블의 각 행에 대해 MATCH()는 관련성 값, 즉 MATCH() 목록에 지정된 열의 해당 행에 있는 텍스트와 검색 문자열 간의 유사성 측도를 반환합니다.

기본적으로 검색은 대소문자를 구분하지 않는 방식으로 수행됩니다.대소문자를 구분하는 전체 텍스트 검색을 수행하려면 이진법을 사용합니다.인덱스 열에 대한 데이터 정렬입니다. 예를 들어, 의 latin1 문자 집합을 사용하는 열에는 전체 텍스트 검색에서 대소문자를 구분하도록 latin1_bin의 집합을 할당할 수 있습니다.

앞에서 설명한 예와 같이 WHERE 절에서 MATCH()를 사용하는 경우 다음 조건이 충족되는 한 반환되는 행은 가장 높은 관련성을 가진 가장 먼저 자동으로 정렬됩니다.

- 명시적인 ORDER BY 절이 없어야 합니다.
- 검색은 테이블 검색이 아닌 전체 텍스트 색인 검색을 사용하여 수행해야 합니다.
- 쿼리가 테이블을 조인하는 경우 전체 텍스트 색인 검사는 조인에서 가장 왼쪽에 있는 일정하지 않은 테이블이어야 합니다.

방금 나열된 조건을 고려한다면, 필요하거나 원하는 경우 명시적인 정렬 순서를 사용하여 순서를 지정하는 것보다 더 쉽게 설정할 수 있습니다.

Relevance values(관련성 값)은 음수가 아닌 부동 소수점 숫자입니다. 관련성이 0이면 유사성이 없습니다. 관련성은 행(문서), 행의 고유 단어 수, 집합의 총 단어 수 및 특정 단어를 포함하는 행 수를 기준으로 계산됩니다.

- row (document), the number of unique words in the row, the total number of words in the collection, and the number of rows that contain a particular word.

"문서"라는 용어는 "행"이라는 용어와 함께 사용할 수 있으며, 두 용어 모두 행의 색인된 부분을 나타냅니다. "수집"이라는 용어는 인덱싱된 열을 의미하며 모든 행을 포함합니다.

검색이 몇 개의 행과 일치하는 경우 인덱스 조회를 통해 첫 번째 쿼리가 더 빨라질 수 있습니다.

### 불리언 검색 Boolean Full-Text Searches

MySQL은 다음을 사용하여 부울 전체 텍스트 검색을 수행할 수 있습니다. `IN BOOLEAN MODE` 한정자입니다. 

이 한정자를 사용하면 특정 문자가 검색 문자열의 단어 시작 또는 끝에 특별한 의미를 가집니다. 다음 쿼리에서 + 연산자와 - 연산자는 일치하는 단어가 존재하거나 존재하지 않아야 한다는 것을 나타냅니다. 따라서 쿼리는 "MySQL" 단어를 포함하지만 "YourSQL" 단어를 포함하지 않는 모든 행을 검색합니다.

<aside>
🔥 이 기능을 구현할 때 MySQL은 때때로 암시적 부울 논리라고 하는 것을 사용합니다.

- + stands for AND
- stands for NOT
- [no operator] implies OR
</aside>

Boolean 검색은 아래와 같은 특징을 갖습니다.

- 관련성이 떨어지는 순서대로 행을 자동으로 정렬하지 않습니다.
- InnoDB 테이블은 부울 쿼리를 수행하려면 MATCH() 식의 모든 열에 FULLTEXT 인덱스가 필요합니다.  MyISAM 검색 인덱스에 대한 부울 쿼리는 FULLTEXT 인덱스가 없어도 작동할 수 있지만, 이러한 방식으로 실행되는 검색은 상당히 느립니다.
- 최소 및 최대 단어 길이 전체 텍스트 매개 변수는 기본 제공 FULLTEXT 구문 분석기 및 MeCab 구문 분석기 플러그인을 사용하여 작성된 FULLTEXT 색인에 적용됩니다.
`innodb_ft_min_token_size` 및 `innodb_ft_max_token_size`는 InnoDB 검색 인덱스에 사용됩니다.
MyISAM 검색 인덱스에는 ft_min_word_len 및 ft_max_word_len이 사용됩니다.
최소 및 최대 단어 길이 전체 텍스트 매개 변수는 ngram 파서를 사용하여 작성된 FULLTEXT 색인에 적용되지 않습니다. ngram 토큰 크기는 ngram_timeout_size 옵션으로 정의됩니다.
- stopword 목록은 innoDB 검색 인덱스의 경우 innodb_ft_enable_stopword, innodb_ft_server_stopword_table, innodb_ft_stopword_table로 설정할 수 있고,MyISAM 검색 인덱스의 경우 ft_stopword_file에 의해 제어됩니다.
- ‘++apple’ 이라는 검색을 할 때, InnoDB는 full-text 검색은 단일 검색어에 여러 연산자를 사용할 수 없지만, MyISAM full-text 검색은 단어 바로 옆에 있는 연산자를 제외한 것과 동일한 결과로 검색을 수행합니다.
- InnoDB는 후행 더하기 또는 빼기 기호를 지정하면 InnoDB에서 구문 오류를 보고합니다.
    - ‘apple+’, ‘+*’, ‘+-’ 와 같은 연산에 오류납니다.

- MyISAM 검색 인덱스에 적용되는 50% 임계값을 사용하지 않습니다.

| Operator | Description |
| --- | --- |
| + | Include, the word must be present. |
| – | Exclude, the word must not be present. |
| > | Include, and increase ranking value. |
| < | Include, and decrease the ranking value. |
| () | Group words into subexpressions (allowing them to be included, excluded, ranked, and so forth as a group). |
| ~ | Negate a word’s ranking value. |
| * | Wildcard at the end of the word. |
| “” | Defines a phrase (as opposed to a list of individual words, the entire phrase is matched for inclusion or exclusion). |

**`+`** : 선행 또는 후행 더하기 기호는 반환되는 각 행에 이 단어가 있어야 함을 나타냅니다. InnoDB는 선행 더하기 기호만 지원합니다.

**`-`** : 선행 또는 후행 마이너스 기호는 반환되는 행에 이 단어가 없어야 함을 나타냅니다. InnoDB는 선행 마이너스 기호만 지원합니다. 

참고: - 연산자는 다른 검색어와 일치하는 행만 제외합니다. 따라서 - 앞에 오는 용어만 포함하는 부울 모드 검색은 빈 결과를 반환합니다. 제외된 항을 포함하는 행을 제외한 모든 행은 반환되지 않습니다.

`(no operator)` : 기본적으로 (+ 또는 -가 지정되지 않은 경우) 단어는 선택 사항이지만 해당 단어가 포함된 행은 더 높은 등급이 지정됩니다. IN Boolean MODE 한정자가 없는 MATCH() AGAINST()의 동작을 모방합니다.

`@distance` : 이 연산자는 InnoDB 테이블에서만 작동합니다. 두 개 이상의 단어가 모두 단어 단위로 측정되는 지정된 거리 내에서 시작하는지 여부를 테스트합니다. 큰따옴표로 둘러싸인 문자열 내에서 검색어를 지정합니다.
예를 들어 @distance 연산자 바로 앞에는 MATCH(col1) AWANS('"word1 word2 word3" @8' IN Boolean MODE)가 있습니다.

`> <` : 이 두 연산자는 행에 할당된 관련성 값에 대한 단어의 기여도를 변경하는 데 사용됩니다. > 연산자는 기여도를 높이고 < 연산자는 감소시킵니다. 다음 목록의 예를 참조하십시오.

`( )` : 괄호는 단어를 하위 표현식으로 그룹화합니다. 괄호로 묶인 그룹을 중첩할 수 있습니다.

`~` :  선행 tilde(~)는 negation(부정) 연산자로 작용하여 행의 관련성에 대한 단어의 기여가 음수가 되도록 합니다. 이것은 "잡음" 단어를 표시하는 데 유용합니다. 이러한 단어를 포함하는 행은 다른 행보다 등급이 낮지만 - 연산자와 마찬가지로 모두 제외되지는 않습니다.

### 쿼리 확장검색

Full-Text StopwordsFull-Text RestrictionsFine-Tuning MySQL Full-Text SearchAdding a User-Defined Collation for Full-Text Indexingngram Full-Text ParserMeCab Full-Text Parser Plugin