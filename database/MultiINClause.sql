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