# Table Count
SELECT count(*) FROM test.employee;
# 9,600,768 900만건

# Table Check
USE test;
DESCRIBE employee;

# Table Describe
SELECT * FROM test.employee limit 10;


SELECT * FROM employee WHERE first_name = "Parto";

-- Covering Index

SELECT first_name
FROM employee
WHERE first_name = "Parto";



SELECT state, ROUND(SUM(duration),5) AS `duration (summed) in sec` FROM information_schema.profiling WHERE query_id = 8 GROUP BY state ORDER BY `duration (summed) in sec` DESC; 

SELECT *
	FROM employee 
	WHERE gender = "M" 
	LIMIT 5000000, 1000;

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

