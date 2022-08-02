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
ORDER BY emp_no DESC
LIMIT 13000, 1000;
-- 1000 rows in set (1.45 sec)

SELECT * FROM employee
WHERE gender = 'M'
AND emp_no < 1000
ORDER BY emp_no DESC
LIMIT 13000, 1000;

SELECT * FROM employee 
WHERE emp_no < 1000
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


/* No Offset */
SELECT * FROM employee 
WHERE gender = 'M' 
AND emp_no > 5000000 
ORDER BY emp_no DESC LIMIT 1000;
/*
mysql> SELECT * FROM employee 
    -> WHERE gender = 'M' 
    -> AND emp_no > 5000000 
    -> ORDER BY emp_no DESC LIMIT 1000;
+----------+------------+----------------+------------------+--------+------------+
| emp_no   | birth_date | first_name     | last_name        | gender | hire_date  |
+----------+------------+----------------+------------------+--------+------------+
| 19201536 | 1958-05-01 | Sachin         | Tsukuda          | M      | 1997-11-30 |
| 19201535 | 1956-09-05 | Patricia       | Breugel          | M      | 1993-10-13 |
| 19201534 | 1961-08-03 | Berhard        | Lenart           | M      | 1986-04-21 |
...
+----------+------------+----------------+------------------+--------+------------+
1000 rows in set (0.06 sec)
*/


SELECT * FROM employee 
WHERE gender = 'M'
ORDER BY emp_no DESC 
LIMIT 5000000, 1000;
/*
mysql> SELECT * FROM employee  WHERE gender = 'M' ORDER BY emp_no DESC  LIMIT 5000000, 1000;
+----------+------------+----------------+----------------+--------+------------+
| emp_no   | birth_date | first_name     | last_name      | gender | hire_date  |
+----------+------------+----------------+----------------+--------+------------+
| 10866490 | 1960-07-31 | George         | Fortenbacher   | M      | 1986-12-09 |
| 10866488 | 1956-06-28 | Weiyi          | Bodoff         | M      | 1990-06-27 |
| 10866487 | 1959-08-25 | Gilbert        | Panangaden     | M      | 1986-07-06 |
   ...
+----------+------------+----------------+----------------+--------+------------+
1000 rows in set (5.37 sec)
*/


SELECT * FROM employee 
ORDER BY emp_no DESC 
LIMIT 5000000, 1000;
/*
+----------+------------+---------------+-----------------+--------+------------+
| emp_no   | birth_date | first_name    | last_name       | gender | hire_date  |
+----------+------------+---------------+-----------------+--------+------------+
| 14201536 | 1956-12-30 | Anestis       | Reinhard        | F      | 1987-07-14 |
| 14201535 | 1955-04-21 | Mansur        | Pauthner        | M      | 1986-09-21 |
| 14201534 | 1956-01-01 | Hideo         | Kornyak         | M      | 1990-09-30 |
									   ...
+----------+------------+---------------+-----------------+--------+------------+
1000 rows in set (2.76 sec)
*/

SELECT e.* FROM employee as e JOIN (
	SELECT emp_no
	FROM employee
	ORDER BY emp_no DESC 
	LIMIT 5000000, 1000) as cover
ON e.emp_no = cover.emp_no;
/*
mysql> SELECT e.* FROM employee as e JOIN (
    -> SELECT emp_no
    -> FROM employee
    -> ORDER BY emp_no DESC 
    -> LIMIT 5000000, 1000) as cover
    -> ON e.emp_no = cover.emp_no;
+----------+------------+---------------+-----------------+--------+------------+
| emp_no   | birth_date | first_name    | last_name       | gender | hire_date  |
+----------+------------+---------------+-----------------+--------+------------+
| 14201536 | 1956-12-30 | Anestis       | Reinhard        | F      | 1987-07-14 |
| 14201535 | 1955-04-21 | Mansur        | Pauthner        | M      | 1986-09-21 |
| 14201534 | 1956-01-01 | Hideo         | Kornyak         | M      | 1990-09-30 |
									   ...
+----------+------------+---------------+-----------------+--------+------------+
1000 rows in set (1.48 sec)
*/


SELECT * FROM employee 
WHERE gender = 'M'
ORDER BY emp_no DESC 
LIMIT 5000000, 1000;
/*
+----------+------------+----------------+----------------+--------+------------+
| emp_no   | birth_date | first_name     | last_name      | gender | hire_date  |
+----------+------------+----------------+----------------+--------+------------+
| 10866490 | 1960-07-31 | George         | Fortenbacher   | M      | 1986-12-09 |
| 10866488 | 1956-06-28 | Weiyi          | Bodoff         | M      | 1990-06-27 |
| 10866487 | 1959-08-25 | Gilbert        | Panangaden     | M      | 1986-07-06 |
			       						...
+----------+------------+----------------+----------------+--------+------------+
1000 rows in set (4.71 sec)
*/

SELECT e.* FROM employee as e JOIN (
	SELECT emp_no
	FROM employee
    WHERE gender = 'M'
	ORDER BY emp_no DESC 
	LIMIT 5000000, 1000) as cover
ON e.emp_no = cover.emp_no;
/*
+----------+------------+----------------+----------------+--------+------------+
| emp_no   | birth_date | first_name     | last_name      | gender | hire_date  |
+----------+------------+----------------+----------------+--------+------------+
| 10866490 | 1960-07-31 | George         | Fortenbacher   | M      | 1986-12-09 |
| 10866488 | 1956-06-28 | Weiyi          | Bodoff         | M      | 1990-06-27 |
| 10866487 | 1959-08-25 | Gilbert        | Panangaden     | M      | 1986-07-06 |
			       						...
+----------+------------+----------------+----------------+--------+------------+
1000 rows in set (3.39 sec)
*/


