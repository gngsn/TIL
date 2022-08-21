CREATE TABLE `multi_in_clause` (
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  `x1` int(11) NOT NULL,
  `y1` int(11) NOT NULL,
  `x2` int(11) NOT NULL,
  `y2` int(11) NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `IDX_XY` (`x1`,`y1`,`x2`,`y2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SELECT * FROM test.multi_in_clause;

truncate test.multi_in_clause;
DROP PROCEDURE IF EXISTS save_data;
DELIMITER $$
CREATE PROCEDURE save_data()
BEGIN
	DECLARE i INT DEFAULT 1;
    WHILE(i <= 1000000) DO
        INSERT INTO test.multi_in_clause (x1, y1, x2, y2) VALUES (
			FLOOR(RAND() * 1000), FLOOR(RAND() * 1000), FLOOR(RAND() * 1000), FLOOR(RAND() * 1000)
		);
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

CALL save_data();
