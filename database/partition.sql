USE mirrorline;


-- ========== CREATE PARTITION ========== -----

-- METHOD1. 새로운 테이블 생성하면서 파티션 나누기
CREATE TABLE mirrorline.users (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `age` INT NOT NULL,
  `reg_at` DATETIME NOT NULL,
  PRIMARY KEY (`user_id`, `reg_at`)
) PARTITION BY RANGE (TO_DAYS(reg_at)) (
	PARTITION P_202206 VALUES LESS THAN (TO_DAYS('2022-07-01')),
	PARTITION P_202207 VALUES LESS THAN (TO_DAYS('2022-08-01')),
	PARTITION P_202208 VALUES LESS THAN (TO_DAYS('2022-09-01')),
	PARTITION P_maxvalue VALUES LESS THAN MAXVALUE
);

-- METHOD2. 기존의 테이블을 파티션으로 나누기 
CREATE TABLE mirrorline.users_after (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `age` INT NOT NULL,
  `reg_at` DATETIME NOT NULL,
  PRIMARY KEY (`user_id`, `reg_at`)
);

    -- 데이터가 존재할 때에도 파티션 적용이 가능할까? - YES
INSERT INTO users_after(email, name, age, reg_at) VALUES('gngsn@gmail.com', 'gngsn', 25, '2022-08-23 00:00:00');

	-- 생성한 테이블에 ALTER로 파티션 적용
ALTER TABLE users_after PARTITION BY RANGE (TO_DAYS(reg_at)) (
	PARTITION P_202206 VALUES LESS THAN (TO_DAYS('2022-07-01')),
	PARTITION P_202207 VALUES LESS THAN (TO_DAYS('2022-08-01')),
	PARTITION P_202208 VALUES LESS THAN (TO_DAYS('2022-09-01')),
	PARTITION P_maxvalue VALUES LESS THAN MAXVALUE
);

-- 생성된 파티션 확인 
SELECT PARTITION_NAME,TABLE_ROWS
FROM INFORMATION_SCHEMA.PARTITIONS
WHERE TABLE_NAME = 'users';

-- 제약 사항: Partition Key 는 테이블에 존재하는 pk와 unique를 반드시 모두 포함해야 한다. 
ALTER TABLE mirrorline.users
ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE;
-- Error Code: 1503. A UNIQUE INDEX must include all columns in the table's partitioning function

-- ========== INSERT INTO PARTITION ========== -----

-- users 테이블에 데이터 추가하기
INSERT INTO users(email, name, age, reg_at) VALUES('gngsn@gmail.com', 'gngsn', 25, '2022-07-23 00:00:00');

SELECT	TO_DAYS('2022-07-23 00:00:00') < TO_DAYS('2022-07-01') as '202206',
	    TO_DAYS('2022-07-23 00:00:00') < TO_DAYS('2022-08-01') as '202207',
        TO_DAYS('2022-07-23 00:00:00') < TO_DAYS('2022-09-01') as '202208';
/*
+--------+--------+--------+
| 202206 | 202207 | 202208 |
+--------+--------+--------+
|      0 |      1 |      1 |
+--------+--------+--------+
1 row in set (0.00 sec)
*/

INSERT INTO users(email, name, age, reg_at) VALUES('gngsn@gmail.com', 'gngsn', 25, '2022-07-23 00:00:00');

