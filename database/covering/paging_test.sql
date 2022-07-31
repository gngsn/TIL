# Paging performance test

SELECT * FROM employee WHERE first_name = 'Parto' LIMIT 0, 1000;
/*
+---------+------------+------------+-----------------+--------+------------+
| emp_no  | birth_date | first_name | last_name       | gender | hire_date  |
+---------+------------+------------+-----------------+--------+------------+
|       3 | 1959-12-03 | Parto      | Bamford         | M      | 1986-08-28 |
|     387 | 1952-11-03 | Parto      | Wrigley         | F      | 1987-02-19 |
|    1052 | 1960-03-16 | Parto      | Hitomi          | F      | 1988-11-11 |
									...
+---------+------------+------------+-----------------+--------+------------+
1000 rows in set (0.27 sec)
*/


SELECT * FROM employee WHERE first_name = 'Parto' LIMIT 13000, 1000;
/*
+----------+------------+------------+-----------------+--------+------------+
| emp_no   | birth_date | first_name | last_name       | gender | hire_date  |
+----------+------------+------------+-----------------+--------+------------+
| 17104872 | 1954-01-09 | Parto      | Mandell         | F      | 1986-12-21 |
| 17106040 | 1964-07-01 | Parto      | Pietracaprina   | M      | 1988-07-23 |
| 17107827 | 1959-12-15 | Parto      | Godskesen       | M      | 1985-11-08 |
			   			   			...
+----------+------------+------------+-----------------+--------+------------+
1000 rows in set (3.62 sec)
*/

SELECT * FROM employee
WHERE first_name = 'Parto'
ORDER BY emp_no DESC
LIMIT 13000, 1000;
-- 1000 rows in set (1.45 sec)


SELECT * FROM employee 
WHERE emp_no < 13000 
AND first_name = 'Parto'
ORDER BY emp_no DESC
LIMIT 1000;
/*
mysql> SELECT * FROM employee 
    -> WHERE first_name = 'Parto' AND emp_no > 13000 
    -> ORDER BY emp_no DESC LIMIT 1000;
mysql> SELECT * FROM employee  WHERE emp_no > 13000 AND first_name = 'Parto' LIMIT 1000;
+---------+------------+------------+-----------------+--------+------------+
| emp_no  | birth_date | first_name | last_name       | gender | hire_date  |
+---------+------------+------------+-----------------+--------+------------+
|   16964 | 1957-05-10 | Parto      | Bernini         | M      | 1992-09-27 |
|   17458 | 1957-06-10 | Parto      | Auyong          | M      | 1985-11-18 |
|   19026 | 1953-03-18 | Parto      | Byoun           | F      | 1985-06-12 |
			   			   			...
+---------+------------+------------+-----------------+--------+------------+
1000 rows in set (0.02 sec)
*/



EXPLAIN SELECT e.* FROM employee as e JOIN (
	SELECT emp_no
	FROM employee 
	WHERE first_name = 'Parto'
	LIMIT 13000, 1000) as cover
ON e.emp_no = cover.emp_no;


