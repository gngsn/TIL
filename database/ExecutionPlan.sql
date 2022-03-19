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
    (SELECT MAX(salary) as salary 
    FROM salaries 
    WHERE emp_no BETWEEN 10001 AND 20000
    ) as sal;


-- select_type : UNION RESULT
EXPLAIN
SELECT all_emp.*
FROM (
	SELECT hire_date FROM employees emp WHERE gender = 'M'
	UNION 
	SELECT hire_date FROM employees emp WHERE gender = 'M'
) as all_emp;


-- select_type : DEPENDENT SUBQUERY
EXPLAIN
SELECT manager.dept_no, (
	SELECT emp.first_name 
    FROM employees emp 
    WHERE gender = 'F' AND emp.emp_no = manager.emp_no
) as name
FROM dept_manager as manager;


-- select_type : DEPENDENT UNION
EXPLAIN
SELECT manager.dept_no, (
	SELECT emp.first_name FROM employees emp WHERE gender = 'F' AND emp.emp_no = manager.emp_no
	UNION 
	SELECT emp.first_name FROM employees emp WHERE gender = 'M' AND emp.emp_no = manager.emp_no
) as name
FROM dept_manager as manager;


-- select_type : UNCACHABLE SUNQUERY
EXPLAIN SELECT * FROM employees
WHERE emp_no = (SELECT ROUND(RAND()*1000000));


-- select_type : MATERIALIZED
EXPLAIN SELECT * FROM employees
WHERE emp_no IN (SELECT emp_no FROM salaries WHERE from_date > '2020-01-01' AND salary > 200000);


