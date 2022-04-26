# ngram

ngram?

n-gram은 n개의 연속적인 단어 나열을 의미하며, n개의 문자로 단위로 끊어서 이를 하나의 토큰으로 간주합니다. 

예를 들어 토큰 크기가 2인 경우, ngram 파서는 문자열 abcdef를 "ab", "bc", "de", "ef"의 네 개의 토큰으로 구문 분석한다.

내장된 MySQL 전체 텍스트 파서는 단어 사이의 공백을 구분 기호로 사용하여 단어의 시작과 끝을 결정하는데, 이는 단어 구분 기호를 사용하지 않는 표상 언어로 작업할 때 제한 사항이다.

이 제한을 해결하기 위해 MySQL은 중국어, 일본어, 한국어(CJK)를 지원하는 전체 텍스트 파서를 제공합니다.

ngram 전체 텍스트 파서는 InnoDB 및 MyISAM과 함께 사용할 수 있습니다.

앤그램은 주어진 텍스트 시퀀스에서 n개의 문자를 연속적으로 배열한 것이다.

ngram 파서는 일련의 텍스트를 n개의 문자로 구성된 연속된 시퀀스로 토큰화합니다.

예를 들어, n그램 전체 텍스트 파서를 사용하여 n의 다른 값에 대해 "abcd"를 토큰화할 수 있습니다.

ngram 전체 텍스트 파서는 기본 제공 서버 플러그인입니다. 다른 기본 제공 서버 플러그인과 마찬가지로 서버가 시작될 때 자동으로 로드됩니다.



섹션 12.10 "전체 텍스트 검색 기능"에 설명된 전체 텍스트 검색 구문은 ngram 파서 플러그인에 적용됩니다.

구문 분석 동작의 차이점은 이 절에서 설명합니다.

최소 및 최대 단어 길이 옵션(innodb_ft_min_token_size, innodb_ft_max_token_size, ft_min_word_len, ft_max_word_len)을 제외한 전체 텍스트 관련 구성 옵션도 적용됩니다.

ngram 토큰 크기 구성

ngram 파서의 기본 ngram 토큰 크기는 2(bigram)입니다. 예를 들어 토큰 크기가 2인 경우, ngram 파서는 문자열 abcdef를 "ab", "bc", "de", "ef"의 네 개의 토큰으로 구문 분석한다.

ngram 토큰 크기는 ngram_timeout_size 구성 옵션을 사용하여 구성할 수 있습니다. 이 옵션은 최소 값이 1이고 최대 값이 10입니다.

일반적으로 ngram_token_size는 검색하려는 가장 큰 토큰의 크기로 설정됩니다.

한 글자(문자)만 검색하려면 ngram_token_size를 1로 설정하십시오.

토큰 크기가 작을수록 전체 텍스트 검색 색인이 작아지고 검색 속도가 빨라집니다.

두 개 이상의 문자로 구성된 단어를 검색해야 하는 경우 그에 따라 ngram_token_size를 설정하십시오.

예를 들어 생일축하(Happy Birthday)는 중국어로 "생일축하"(생일축하)이고, "생일축하"(생일축하)는 "해피"(happy)로 번역된다.

이와 같은 두 개의 문자 단어를 검색하려면 ngram_token_size를 2 이상의 값으로 설정하십시오

ngram_token_size는 읽기 전용(read-only) 변수로, startup string(mysqld 실행 시 option)으로 설정하거나 구성 파일에서 설정할 수 있습니다.

  

- Startup string:

`mysqld --ngram_token_size=2`

- Configuration file:

```bash
[mysqld]
ngram_token-size=2
```

ngram 파서를 사용하는 FULLTEXT 인덱스의 경우 innodb_ft_min_token_size, innodb_ft_max_token_size, ft_min_word_len 및 ft_max_word_len 최소 및 최대 단어 길이 구성 옵션이 무시됩니다.

## Creating a FULLTEXT Index that Uses the ngram Parser

```sql
CREATE TABLE IF NOT EXISTS `books` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(350) NULL DEFAULT NULL,
  `reg_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`), 
  FULLTEXT INDEX `ft_idx_title` (`title`)  WITH PARSER `ngram`
);

INSERT INTO books(title, author) VALUES('철학은 어떻게 삶의 무기가 되는가','야마구치 슈');

set global innodb_ft_aux_table = 'test/books';

SELECT * FROM INFORMATION_SCHEMA.INNODB_FT_INDEX_CACHE;

```