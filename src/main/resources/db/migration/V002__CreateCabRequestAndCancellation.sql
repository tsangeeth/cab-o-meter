CREATE TABLE cab_request (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	EMPLOYEE_ID INT NOT NULL,
	CREATE_DATE TIMESTAMP NOT NULL,
	UPDATE_DATE TIMESTAMP NULL,
	CREATED_BY VARCHAR(255) NOT NULL,
	START_DATE DATE NOT NULL,
	LOGIN_TIME TIME NOT NULL,
	END_DATE DATE NOT NULL,
	LOGOUT_TIME TIME NOT NULL,
	REQ_REASON VARCHAR(1000)  NULL,
	PREAPPROVED CHAR(1) ASCII NULL,
	ISAPPROVED CHAR(1) ASCII NULL,
	APPROVER_ID VARCHAR(10)  NULL,
	APPR_REASON VARCHAR(1000)  NULL,
	REOCURR_DAYS VARCHAR(20) NULL
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;


CREATE TABLE request_cancellation (
	ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	REQUEST_ID INT NOT NULL,
	EMPLOYEE_ID INT NOT NULL,
	ISCOMP_REQ_CANCELLED CHAR(1) ASCII NULL,
	CANCELLED_DATE TIMESTAMP NOT NULL,
	CANCELLED_BY VARCHAR(10) NOT NULL,
	CANC_REASON VARCHAR(500)  NULL,
	UPDATE_TIME TIMESTAMP  NULL,
	FORFEIT_CANCEL_REQ CHAR(1) ASCII NULL
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;


ALTER TABLE cab_request  ADD CONSTRAINT cab_request_employee_id_fk FOREIGN KEY (EMPLOYEE_ID) REFERENCES employee(ID) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE request_cancellation  ADD CONSTRAINT request_cancellation_employee_id_fk FOREIGN KEY (EMPLOYEE_ID) REFERENCES employee(ID) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE request_cancellation  ADD CONSTRAINT request_cancellation_request_id_fk FOREIGN KEY (REQUEST_ID) REFERENCES cab_request(ID) ON UPDATE RESTRICT ON DELETE RESTRICT;