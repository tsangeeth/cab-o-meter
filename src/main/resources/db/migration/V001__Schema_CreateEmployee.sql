CREATE TABLE employee (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	EMP_ID VARCHAR(10) NOT NULL,
	FIRST_NAME VARCHAR(50) NOT NULL,
	LAST_NAME VARCHAR(50) NOT NULL,
	MIDDLE_NAME VARCHAR(50)  NULL,
	ROLE VARCHAR(25) NOT NULL,
	COST_CENTRE VARCHAR(20) NOT NULL,
	TEAM_NAME VARCHAR(100) NOT NULL,
	CONTACT_NO VARCHAR(10) NOT NULL,
	ALTERNATE_CONTACT_NO VARCHAR(10) NULL,
	LANDLINE_NO VARCHAR(10) NULL,
	EMAIL VARCHAR(100) NOT NULL,
	MANAGER_ID VARCHAR(10) NULL,
	GENDER VARCHAR(200) NOT NULL
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;


CREATE TABLE address (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ADDR_LINE1 VARCHAR(50) NOT NULL,
	ADDR_LINE2 VARCHAR(100)  NULL,
	LOCALITY VARCHAR(25) NOT NULL,
	CITY VARCHAR(25) NOT NULL,
	STATE VARCHAR(20) NOT NULL,
	LANDMARK1 VARCHAR(50) NOT NULL,
	LANDMARK2 VARCHAR(50)  NULL,
	EMPLOYEE_ID INT NOT NULL
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;

ALTER TABLE employee ADD CONSTRAINT employee_employee_id_uq UNIQUE (EMP_ID);
ALTER TABLE employee ADD CONSTRAINT employee_email_id_uq UNIQUE (EMAIL);
ALTER TABLE address  ADD CONSTRAINT address_employee_employee_id_fk FOREIGN KEY (EMPLOYEE_ID) REFERENCES employee(ID) ON UPDATE RESTRICT ON DELETE RESTRICT;

