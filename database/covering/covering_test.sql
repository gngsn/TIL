# COVERING INDEX TEST


SELECT last_name FROM employee WHERE first_name = 'Parto' LIMIT 13000, 1000;
/*
+-----------------+
| last_name       |
+-----------------+
| Mandell         |
| Pietracaprina   |
| Godskesen       |
	   ...
+-----------------+
1000 rows in set (3.55 sec)
*/

SELECT emp_no FROM employee WHERE first_name = 'Parto' LIMIT 13000, 1000;
/*
mysql> SELECT emp_no FROM employee 
    -> WHERE first_name = 'Parto' LIMIT 13000, 1000;
+----------+
| emp_no   |
+----------+
| 17104872 |
| 17106040 |
| 17107827 |
    ...
+----------+
1000 rows in set (0.02 sec)
*/

EXPLAIN SELECT last_name FROM employee WHERE first_name = 'Parto' LIMIT 13000, 1000;
/*
+----+-------------+----------+------------+------+------------------------+------------------------+---------+-------+-------+----------+-------+
| id | select_type | table    | partitions | type | possible_keys          | key                    | key_len | ref   | rows  | filtered | Extra |
+----+-------------+----------+------------+------+------------------------+------------------------+---------+-------+-------+----------+-------+
|  1 | SIMPLE      | employee | NULL       | ref  | Idx_employee_firstname | Idx_employee_firstname | 58      | const | 26196 |   100.00 | NULL  |
+----+-------------+----------+------------+------+------------------------+------------------------+---------+-------+-------+----------+-------+
1 row in set, 1 warning (0.01 sec)
*/



SELECT * FROM employee WHERE first_name = 'Parto' LIMIT 0, 1000;
SELECT * FROM employee WHERE first_name = 'Parto' LIMIT 13000, 1000;
