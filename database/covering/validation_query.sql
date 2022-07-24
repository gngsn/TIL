# Table Count
SELECT count(*) FROM test.employee;
# 9,600,768 900만건

# Table Check
USE test;
DESCRIBE employee;

# Table Describe
SELECT * FROM test.employee limit 10;
SELECT * FROM employee WHERE first_name = "Parto";

# Paging TEST 1: OFFSET Performance 
-- offset 0 limit 1,000
SELECT *
FROM employee 
WHERE gender = "M" 
LIMIT 0, 1000;

-- offset 5,000,000 limit 1,000
SELECT *
FROM employee 
WHERE gender = "M" 
LIMIT 5000000, 1000;

-- Covering Index

SELECT *
FROM employee as e
JOIN (
	SELECT emp_no
	FROM employee 
	WHERE gender = "M" 
	LIMIT 5000000, 1000) as cover
ON e.emp_no = cover.emp_no;



ALTER TABLE employee ADD INDEX Idx_employee_firstName (first_name);
ALTER TABLE employee ALTER INDEX Idx_employee_firstName invisible;

ALTER TABLE employee ADD INDEX Idx_employee_gender (gender);

