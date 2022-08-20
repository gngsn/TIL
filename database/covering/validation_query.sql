# Table Count
SELECT count(*) FROM test.employee_hire;
INSERT INTO employee_hire SELECT * FROM employee;

# 9,600,768 900만건

# Cardinality 고려
# CREATE TABLE employee_hire AS SELECT * FROM employee;

# Table Check
USE test;
DESCRIBE employee;

# Table Describe
SELECT * FROM test.employee limit 10;
SELECT * FROM employee WHERE first_name = "Parto";
SHOW INDEX FROM employee;

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


## Paging TEST 1-1: OFFSET Performance (more high cardinality)
SHOW INDEX FROM employee_tmp;  -- Cardinality: 1
SHOW INDEX FROM employee;      -- Cardinality: first_name(1276), hire_date(5096)
ALTER TABLE employee ALTER INDEX `Idx_employee_hireDate` INVISIBLE;


# Paging TEST 2: OFFSET Performance 
-- offset 5,000,000 limit 1,000 without Covering Index
SELECT *
FROM employee 
WHERE gender = "M" 
LIMIT 5000000, 1000;

-- offset 5,000,000 limit 1,000 with Covering Index
SELECT *
FROM employee as e
JOIN (
	SELECT emp_no
	FROM employee 
	WHERE gender = "M" 
	LIMIT 5000000, 1000) as cover
ON e.emp_no = cover.emp_no;


# Paging TEST 3: Index - firstName::OFFSET Performance 
-- offset 13,000 limit 1,000 without Covering Index
SELECT *
FROM employee 
WHERE first_name = 'Parto'
LIMIT 13000, 1000;


-- offset 13,000 limit 1,000 with Covering Index
EXPLAIN SELECT e.* FROM employee as e JOIN (
	SELECT emp_no
	FROM employee 
	WHERE first_name = 'Parto'
	LIMIT 13000, 1000) as cover
ON e.emp_no = cover.emp_no;