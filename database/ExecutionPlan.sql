USE employees;

-- id 
EXPLAIN SELECT e.emp_no as 사번, e.first_name as 이름, e.gender as 성별, s.salary,
	(SELECT MAX(dept_no) as 매핑 FROM dept_emp WHERE dept_emp.emp_no = e.emp_no)
FROM employees e, salaries s
WHERE e.emp_no = s.emp_no AND e.emp_no = 10001;

-- select_type : select
EXPLAIN SELECT emp_no as 사번, first_name as 이름, gender as 성별
FROM employees WHERE emp_no = 10001;

-- select_type : select 2
EXPLAIN SELECT e.emp_no as 사번, e.first_name as 이름, e.gender as 성별, s.salary as 연봉
FROM employees e, (SELECT emp_no, salary FROM salaries WHERE salary > 700000) as s
WHERE e.emp_no = s.emp_no AND e.emp_no BETWEEN 10001 AND 10010;

-- select_type : primary - SUBQUERY
EXPLAIN SELECT e.emp_no as 사번, e.first_name as 이름, e.gender as 성별,
	(SELECT MAX(dept_no) as 매핑 FROM dept_emp WHERE dept_emp.emp_no = e.emp_no)
FROM employees e
WHERE e.emp_no = 10001;

-- select_type : primary - UNION
EXPLAIN 

SELECT employee1.emp_no, employee1.first_name, employee1.last_name
FROM employees employee1
WHERE employee1.emp_no = 10001

UNION ALL

SELECT employee2.emp_no, employee2.first_name, employee2.last_name
FROM employees employee2
WHERE employee2.emp_no = 10002;

-- select_type : SUBQUERY
EXPLAIN SELECT 
	(SELECT COUNT(*) FROM dept_emp) as count,
    (SELECT MAX(salary) FROM salaries) as salary;

-- select_type : DERIVED
EXPLAIN
SELECT emp.emp_no, sal.salary
FROM employees as emp,
    (SELECT emp_no as emp_no, salary FROM salaries WHERE emp_no BETWEEN 10001 AND 20000) as sal
WHERE emp.emp_no = sal.emp_no;
