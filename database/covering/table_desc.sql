# COVERING INDEX TEST

DESC employee;
/*
+------------+-------------+------+-----+---------+----------------+
| Field      | Type        | Null | Key | Default | Extra          |
+------------+-------------+------+-----+---------+----------------+
| emp_no     | int(11)     | NO   | PRI | NULL    | auto_increment |
| birth_date | date        | NO   |     | NULL    |                |
| first_name | varchar(14) | NO   | MUL | NULL    |                |
| last_name  | varchar(16) | NO   |     | NULL    |                |
| gender     | varchar(1)  | NO   |     | NULL    |                |
| hire_date  | date        | NO   |     | NULL    |                |
+------------+-------------+------+-----+---------+----------------+
6 rows in set (0.02 sec)
*/

SHOW TABLE STATUS \G;
/*
*************************** 6. row ***************************
           Name: employee
         Engine: InnoDB
        Version: 10
     Row_format: Dynamic
           Rows: 18681872
 Avg_row_length: 47
    Data_length: 879755264
Max_data_length: 0
   Index_length: 373293056
      Data_free: 5242880
 Auto_increment: 19234414
    Create_time: 2022-07-26 10:10:55
    Update_time: 2022-07-26 14:42:51
     Check_time: NULL
      Collation: utf8mb4_0900_ai_ci
       Checksum: NULL
 Create_options: 
        Comment:
*/

SELECT * FROM employee LIMIT 5;
/*
+--------+------------+------------+-----------+--------+------------+
| emp_no | birth_date | first_name | last_name | gender | hire_date  |
+--------+------------+------------+-----------+--------+------------+
|      1 | 1953-09-02 | Georgi     | Facello   | M      | 1986-06-26 |
|      2 | 1964-06-02 | Bezalel    | Simmel    | F      | 1985-11-21 |
|      3 | 1959-12-03 | Parto      | Bamford   | M      | 1986-08-28 |
|      4 | 1954-05-01 | Chirstian  | Koblick   | M      | 1986-12-01 |
|      5 | 1955-01-21 | Kyoichi    | Maliniak  | M      | 1989-09-12 |
+--------+------------+------------+-----------+--------+------------+
5 rows in set (0.02 sec)
*/

SELECT COUNT(*) FROM employee;
/*
+----------+
| COUNT(*) |
+----------+
| 19201536 |
+----------+
*/


SHOW INDEX FROM employee;
/*
+----------+------------+------------------------+--------------+-------------+-----------+-------------+------------+---------+------------+
| Table    | Non_unique | Key_name               | Seq_in_index | Column_name | Collation | Cardinality | Index_type | Visible | Expression |
+----------+------------+------------------------+--------------+-------------+-----------+-------------+------------+---------+------------+
| employee |          0 | PRIMARY                |            1 | emp_no      | A         |    17237538 |  BTREE     |  YES    | NULL       |
| employee |          1 | Idx_employee_firstname |            1 | first_name  | A         |       18848 |  BTREE     |  YES    | NULL       |
+----------+------------+------------------------+--------------+-------------+-----------+-------------+------------+---------+------------+
2 rows in set (0.08 sec)
*/
