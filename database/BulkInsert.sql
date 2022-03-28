DELIMITER $$ 
DROP PROCEDURE IF EXISTS BULK_INSERT;
CREATE PROCEDURE BULK_INSERT()
BEGIN
 DECLARE i INT DEFAULT 1;
 DECLARE lo INT DEFAULT 50000;
 WHILE(i <= lo) DO
  INSERT INTO test(NAME, email, PASSWORD) VALUES
  (CONCAT("gngsn", i), CONCAT(i, "@bulk.com"), UUID()),
  (CONCAT("gngsn", i+1), CONCAT(i+1, "@bulk.com"), UUID()), 
  (CONCAT("gngsn", i+2), CONCAT(i+2, "@bulk.com"), UUID()), 
  (CONCAT("gngsn", i+3), CONCAT(i+3, "@bulk.com"), UUID()), 
  (CONCAT("gngsn", i+4), CONCAT(i+4, "@bulk.com"), UUID()), 
  (CONCAT("gngsn", i+5), CONCAT(i+5, "@bulk.com"), UUID()), 
  (CONCAT("gngsn", i+6), CONCAT(i+6, "@bulk.com"), UUID()), 
  (CONCAT("gngsn", i+7), CONCAT(i+7, "@bulk.com"), UUID()),
  (CONCAT("gngsn", i+8), CONCAT(i+8, "@bulk.com"), UUID()),
  (CONCAT("gngsn", i+9), CONCAT(i+9, "@bulk.com"), UUID());
  SET i = i+1;
 END WHILE;
END $$
DELIMITER ;