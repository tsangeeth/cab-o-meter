CREATE TABLE employee (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  employee_id VARCHAR(10) NOT NULL,
  first_name VARCHAR(25) NOT NULL,
  middle_name VARCHAR(25),
  last_name VARCHAR(25) NOT NULL,
  role VARCHAR(20) NOT NULL,
  cost_centre VARCHAR(25) NOT NULL
);

CREATE TABLE employee_address(
	employee_key INT NOT NULL, 
	line1 VARCHAR(10) NOT NULL,
  	line2 VARCHAR(25),
  	locality VARCHAR(25),
  	city VARCHAR(30) NOT NULL,
  	state VARCHAR(30) NOT NULL,
  	landmark VARCHAR(20) NOT NULL
);


ALTER TABLE employee ADD CONSTRAINT employee_employee_id_uq UNIQUE (employee_id);
ALTER TABLE employee_address ADD CONSTRAINT employee_address_employee_key_uq UNIQUE (employee_key);
ALTER TABLE employee_address  ADD CONSTRAINT employee_address_employee_fk FOREIGN KEY (employee_key) REFERENCES employee(id);