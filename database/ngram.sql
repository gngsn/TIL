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





SELECT * FROM books WHERE MATCH(title) AGAINST("철학");