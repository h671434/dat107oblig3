-- Skript for å opprete databasen og legge inn data
	-- Skjema = oblig3
		-- Tabeller = Ansatt, Avdeling, ProsjektDeltakelse, Prosjekt

DROP SCHEMA IF EXISTS oblig3 CASCADE;
CREATE SCHEMA oblig3;
SET search_path TO oblig3;
    
-- Create and insert employees
CREATE TABLE employees(
    employee_id SERIAL,
    username  CHAR(3) UNIQUE NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    employment_date DATE NOT NULL,
    position VARCHAR(20) NOT NULL,
    monthly_salary DECIMAL NOT NULL,
    CONSTRAINT pk_employees PRIMARY KEY(employee_id)
);

INSERT INTO employees
	(username, first_name, last_name, employment_date, position, monthly_salary)
VALUES
	('jbo', 'Jonas', 'Brother', '2019-10-23', 'Manager', 30000.00),
	('klu', 'Kyle', 'Lindell', '2019-10-23', 'Developer', 25000.00),
	('pto', 'Peter', 'Tolkien', '2019-10-23', 'Accountant', 25000.00),
	('blo', 'Benny', 'Lotto', '2019-10-23', 'Developer', 25000.00),
	('rta', 'Ronja', 'Tarkov', '2019-11-24', 'Salesperson', 25000.00),
	
	('eer', 'Eirik', 'Ermantraut', '2019-12-23', 'Developer', 25000.00),
	('pne', 'Pilly', 'Nesen', '2020-01-03', 'Accountant', 25000.00),
	('sit', 'Sofie', 'Itle', '2020-10-23', 'Salesperson', 25000.00),
	('kot', 'Ken', 'Otterman', '2020-02-23', 'Developer', 25000.00),
	('cba', 'Charlotte', 'Banana', '2021-10-20', 'Developer', 25000.00),
	('man', 'Miss', 'Andersen', '2021-10-23', 'Salesperson', 25000.00),
	('boy', 'Big', 'Oyster', '2022-10-23', 'Developer', 25000.00);
	
SELECT * FROM employees;

-- Create and insert departments
CREATE TABLE departments(
	department_id SERIAL,
	department_name VARCHAR(30) NOT NULL,
	department_manager INTEGER NOT NULL,
	CONSTRAINT pk_departments PRIMARY KEY(department_id),
	CONSTRAINT fk_employees FOREIGN KEY(department_manager)
		REFERENCES employees(employee_id)
);

INSERT INTO departments
	(department_name, department_manager)
VALUES
	('Administration', 1),
	('IT', 2),
	('Finance', 3),
	('Sales', 5);
	
-- Add department and constraints to employees
ALTER TABLE employees
	ADD department INTEGER,
	ADD CONSTRAINT fk_departments FOREIGN KEY(department)
		REFERENCES departments(department_id);
	
UPDATE employees
	SET department = 1 
	WHERE position LIKE 'Manager';
UPDATE employees
	SET department = 2 
	WHERE position LIKE 'Developer';
UPDATE employees
	SET department = 3 
	WHERE position LIKE 'Accountant';
UPDATE employees
	SET department = 4 
	WHERE position LIKE 'Salesperson';
	
ALTER TABLE employees 
	ALTER COLUMN department SET NOT NULL,

		
		
