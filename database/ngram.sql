-- 테스트 테이블 생성 
CREATE TABLE IF NOT EXISTS `books` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(350) NULL DEFAULT NULL,
  `reg_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`), 
  FULLTEXT INDEX `ft_idx_title` (`title`)  WITH PARSER `ngram`
);

-- 테스트 데이터 추가 
INSERT INTO books(title, author) VALUES('철학은 어떻게 삶의 무기가 되는가','야마구치 슈');

-- aux table 설정
set global innodb_ft_aux_table = 'test/books';

-- 캐싱된 데이터 확인
SELECT * FROM INFORMATION_SCHEMA.INNODB_FT_INDEX_CACHE;

/*
+--------+--------------+-------------+-----------+--------+----------+
| WORD   | FIRST_DOC_ID | LAST_DOC_ID | DOC_COUNT | DOC_ID | POSITION |
+--------+--------------+-------------+-----------+--------+----------+
| 기가   |            2 |           2 |         1 |      2 |       30 |
| 는가   |            2 |           2 |         1 |      2 |       40 |
| 되는   |            2 |           2 |         1 |      2 |       37 |
| 떻게   |            2 |           2 |         1 |      2 |       13 |
| 무기   |            2 |           2 |         1 |      2 |       27 |
| 삶의   |            2 |           2 |         1 |      2 |       20 |
| 어떻   |            2 |           2 |         1 |      2 |       10 |
| 철학   |            2 |           2 |         1 |      2 |        0 |
| 학은   |            2 |           2 |         1 |      2 |        3 |
+--------+--------------+-------------+-----------+--------+----------+
9 rows in set (0.00 sec)
*/


-- token 사이즈 확인

SHOW GLOBAL VARIABLES LIKE "ngram_token_size";

/*
+------------------+-------+
| Variable_name    | Value |
+------------------+-------+
| ngram_token_size | 2     |
+------------------+-------+
1 row in set (0.01 sec)
*/


-- Index 생성 방법 1
CREATE TABLE articles (
      id INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
      title VARCHAR(200),
      body TEXT,
      FULLTEXT (title,body) WITH PARSER ngram
) ENGINE=InnoDB CHARACTER SET utf8mb4;

-- Index 생성 방법 2
ALTER TABLE articles ADD FULLTEXT INDEX ft_index (title,body) WITH PARSER ngram;

-- Index 생성 방법 3
CREATE FULLTEXT INDEX ft_index ON articles (title,body) WITH PARSER ngram;


-- 검색 방법 1
SELECT MATCH (a) AGAINST ('abc') FROM t GROUP BY a WITH ROLLUP;
SELECT 1 FROM t GROUP BY a, MATCH (a) AGAINST ('abc') WITH ROLLUP;

-- 검색 방법 2
SELECT 1 FROM t GROUP BY a WITH ROLLUP HAVING MATCH (a) AGAINST ('abc');

-- 검색 방법 3
SELECT 1 FROM t GROUP BY a WITH ROLLUP ORDER BY MATCH (a) AGAINST ('abc');