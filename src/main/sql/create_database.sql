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
	CONSTRAINT fk_manager FOREIGN KEY(department_manager)
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
	ALTER COLUMN department SET NOT NULL;
	
-- Create project tables and insert some data into them
CREATE TABLE projects(
	project_id SERIAL,
	project_name VARCHAR(20) NOT NULL,
	project_description VARCHAR(300),
	CONSTRAINT pk_project PRIMARY KEY(project_id)
);

CREATE TABLE project_participations(
	employee INTEGER NOT NULL,
	project INTEGER NOT NULL,
	role VARCHAR(30) DEFAULT 'Team Member',
	hours_worked INTEGER,
	CONSTRAINT pk_project_participation PRIMARY KEY(employee, project),
	CONSTRAINT fk_employee FOREIGN KEY(employee)
		REFERENCES employees(employee_id) ON DELETE CASCADE,
	CONSTRAINT fk_project FOREIGN KEY(project)
		REFERENCES projects(project_id)
);		

INSERT INTO projects
	(project_name, project_description)
VALUES
	('Develop app', 'Build a database app to manage employees.'),
	('App Launch', 'Launching the developed app and marketing it.'),
	('Costumer Support', 'Build a platform for costumer support.'),
	('App Expansion', 'Develop new features for the app.'),
	('Mario Kart 2021', 'The offical company Mario Kart tournament of 2021. Last place loses job, winner gets a promotion.'),
	('Office Pool', 'Build a giant pool inside the office.'),
	('Mario Kart 2022', 'The offical company Mario Kart tournament of 2022. Last place loses job, winner gets a promotion.'),
	('Make Money', 'Earn stacks');
	
INSERT INTO project_participations
	(employee, project, role, hours_worked)
VALUES
	(1, 1, 'Manager', 564),
	(1, 2, 'Manager', 564),
	(1, 3, 'Manager', 124),
	(1, 4, 'Manager', 4531),
	(1, 5, 'Manager', 500),
	(1, 6, 'Manager', 1000),
	(1, 7, 'Manager', 500),
	(1, 8, 'Manager', 1),
	(2, 1, 'Administrator', 564),
	(4, 4, 'Coordinator', 532),
	(4, 5, 'Director', 664),
	(5, 3, 'Analyst', 564),
	(6, 7, 'Administrator', 134),
	(3, 7, 'Analyst', 623),
	(9, 4, 'Director', 1564),
	(4, 6, 'Administrator', 641);
	
INSERT INTO project_participations
	(employee, project, hours_worked)
VALUES
	(2, 4, 1433),
	(3, 4, 2310),
	(6, 5, 1320),
	(5, 4, 4321),
	(6, 6, 1423),
	(7, 4, 210),
	(8, 4, 143),
	(4, 7, 1310),
	(10, 4, 1310),
	(11, 4, 1310);
	
INSERT INTO project_participations
	(employee, project)
VALUES
	(5, 7),
	(2, 5),
	(7, 6),
	(11, 7);
	
