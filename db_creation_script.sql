DROP DATABASE IF EXISTS DA19785;
CREATE DATABASE DA19785;
USE DA19785;
 
 SET @address_id :=0 ;
 SET @person_id := 0;
 SET @employee_id := 0;
 SET @invoice_id := 0;
 
 
 CREATE TABLE phone_numbers(phone_number_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, phone_number VARCHAR(16), phone_type VARCHAR(32));
 
 CREATE TABLE countries(country_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, country_name VARCHAR(64), country_abbreviation VARCHAR(4));
 
 
 
 CREATE TABLE states(state_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, country_id INT, state_name varchar(64), state_abbreviation VARCHAR(4), FOREIGN KEY(country_id) REFERENCES countries(country_id));
 
  DELIMITER //
 CREATE PROCEDURE create_state(state_name varchar(64), state_abbv VARCHAR(4), country_abbv VARCHAR(4))
 BEGIN
  INSERT INTO states(state_name, state_abbreviation, country_id) SELECT state_name, state_abbv, country_id FROM countries WHERE country_abbreviation = country_abbv;
 END //
 DELIMITER ;
 

 
 
 
 CREATE TABLE addresses(address_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, street VARCHAR(64), street_2 VARCHAR(64), city VARCHAR(64), state_id INT, 
 FOREIGN KEY(state_id) REFERENCES states(state_id), created_date DATETIME);
 
  DELIMITER //
 CREATE PROCEDURE create_address(street VARCHAR(64), city VARCHAR(64), state_abbv VARCHAR(4))
 BEGIN
  IF( SELECT COUNT(address_id) < 1 FROM addresses AS addr INNER JOIN states AS sts ON addr.state_id = sts.state_id INNER JOIN countries AS con ON con.country_id = sts.country_id WHERE addr.street = street  AND addr.city = city AND sts.state_abbreviation = state_abbv AND con.country_abbreviation = 'USA')
 THEN
 INSERT INTO addresses (street, street_2, city, state_id) SELECT street, '',city, state_id FROM states AS sts INNER JOIN
 countries AS con ON sts.country_id = con.country_id
 WHERE sts.state_abbreviation = state_abbv AND con.country_abbreviation = 'USA';
 END IF;
 CALL get_us_address_id(street,state_abbv);
 END //
 DELIMITER ;
 
 
DELIMITER //
 CREATE PROCEDURE get_us_address_id(street VARCHAR(64), state_abbv VARCHAR(4))
 BEGIN
  SET @address_id :=0 ;
  SELECT @address_id := addr.address_id FROM  addresses AS addr INNER JOIN states AS sts  ON addr.state_id = sts.state_id INNER JOIN
 countries AS con ON sts.country_id = con.country_id
 WHERE sts.state_abbreviation = state_abbv AND con.country_abbreviation = 'USA';
 END //
 DELIMITER ;
 
 
 
 
 CREATE TABLE persons(person_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, first_name VARCHAR(64), middle_name VARCHAR(64), last_name VARCHAR(64), alias VARCHAR(64),
 sex CHAR, is_current BOOLEAN, is_active BOOLEAN,  address_id INT, created_date DATETIME, updated_date DATETIME, birth_date DATE,
 FOREIGN KEY(address_id) REFERENCES addresses(address_id));
 
 ALTER TABLE persons MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;
 
 
 
  DELIMITER //
 CREATE PROCEDURE create_person(first_name VARCHAR(64),  middle_name VARCHAR(64), last_name VARCHAR(64), sex CHAR,street VARCHAR(64),city VARCHAR(64), state_abbv VARCHAR(4))
 BEGIN
CALL get_person_id(first_name,middle_name,last_name);

IF(@person_id < 1)
THEN
CALL create_address(street, city, state_abbv);

INSERT INTO persons(first_name, middle_name,last_name,address_id,sex, is_current, is_active) VALUES(first_name, middle_name,last_name,@address_id,sex, true,true);
  END IF;
  CALL get_person_id(first_name,middle_name,last_name);

  END //
 DELIMITER ;
 
 DELIMITER //
 CREATE PROCEDURE get_person_id(first_name VARCHAR(64),  middle_name VARCHAR(64), last_name VARCHAR(64))
 BEGIN
  SET @person_id :=0 ;
 SELECT @person_id := person_id FROM persons AS per WHERE per.first_name = first_name AND per.middle_name= middle_name AND per.last_name = last_name;
 END //
 DELIMITER ;
  
  
  
  
  CREATE TABLE historic_persons(historic_person_id INT, current_person_id INT, PRIMARY KEY(historic_person_id) 
 , FOREIGN KEY(historic_person_id) REFERENCES persons(person_id), FOREIGN KEY(current_person_id) REFERENCES persons(person_id));
 
 CREATE TABLE person_notes(person_notes_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, notes BLOB, created_date DATETIME,
 FOREIGN KEY(person_notes_id) REFERENCES persons(person_id));
 
  ALTER TABLE person_notes MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;
 
 CREATE TABLE person_phone_number(person_phone_number_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, phone_number_id INT,
 FOREIGN KEY(person_id) REFERENCES persons(person_id), FOREIGN KEY(phone_number_id) REFERENCES phone_numbers(phone_number_id));
  
  CREATE TABLE accounts(account_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, account_number VARCHAR(32) UNIQUE, account_name VARCHAR(64) UNIQUE, person_id INT , created_date DATE, closed_date DATE);
  
  
  CREATE TABLE employees(employee_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, role_name VARCHAR(32), 
  hire_date DATE, is_active BOOLEAN, is_instructor BOOLEAN, is_technician boolean, employee_code VARCHAR(16), created_date DATETIME,
  FOREIGN KEY(person_id) REFERENCES persons(person_id) );
  
  ALTER TABLE employees MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;
 
 
  
   DELIMITER //
 CREATE PROCEDURE create_employee(first_name VARCHAR(64),  middle_name VARCHAR(64), last_name VARCHAR(64), role_name VARCHAR(32))
 BEGIN
CALL get_person_id(first_name, middle_name,last_name);
INSERT INTO employees(person_id, role_name,is_active, is_instructor, is_technician, employee_code, hire_date)
 VALUES (@person_id, role_name, TRUE, FALSE, FALSE, 'DEFAULT', CURDATE());
 END //
 DELIMITER ;
  
  
  
  
  
  
  
  
 CREATE TABLE companies(company_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, company_name VARCHAR(64), notes BLOB,
 address_id INT, is_active BOOLEAN, is_current BOOLEAN);
 
 CREATE TABLE company_persons(company_person_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, company_id INT, person_id INT, 
 FOREIGN KEY (company_id) REFERENCES companies(company_id), FOREIGN KEY(person_id) REFERENCES persons(person_id));
 
 CREATE TABLE certifications(certification_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, company_id INT, certification VARCHAR(32));
 
 CREATE TABLE certification_types(certification_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, certification_id INT, certification_class VARCHAR(32));
 
 CREATE TABLE certification_requirements(certification_requirement_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, certification_id INT, required_certification_type INT);
 
 CREATE TABLE person_certifications(person_certification_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, certification_id INT);
 
 
 
 CREATE TABLE item_types(item_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_type_name VARCHAR(64) UNIQUE);
 
 CREATE TABLE items(item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,item_type INT, item_name VARCHAR(64), is_for_sale BOOLEAN,
 company_id INT, created_date DATETIME, FOREIGN KEY(company_id) REFERENCES companies(company_id), FOREIGN KEY(item_type) REFERENCES item_types(item_type_id));
 
 CREATE TABLE serialized_items(serialized_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, 
 serial_number VARCHAR(64), FOREIGN KEY (item_id) REFERENCES items(item_id));
 
 CREATE TABLE item_notes(item_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, created_date DATETIME, notes BLOB,
 FOREIGN KEY(item_id) REFERENCES items(item_id));
 
 CREATE TABLE serialized_item_notes(serailzied_item_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, serialized_item_id INT, 
 note BLOB, created_date DATETIME, FOREIGN KEY(serialized_item_id) REFERENCES serialized_items(serialized_item_id));
 
 CREATE TABLE item_upc(item_upc_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, upc VARCHAR(32), FOREIGN KEY(item_id) REFERENCES items(item_id));
 
 CREATE TABLE currencies(currency_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, currency_name VARCHAR(32), country_id INT, FOREIGN KEY(country_id) REFERENCES countries(country_id));
 
 CREATE TABLE prices(price_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, price_name VARCHAR(16), item_id INT, purchase_price DOUBLE, sell_price DOUBLE, 
 created_date DATETIME, ended_date DATETIME, is_special BOOLEAN, is_sale BOOLEAN, currency_id INT);
 
 CREATE TABLE invoices(invoice_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_name VARCHAR(32) UNIQUE, account_id INT,
 employee_id INT, amount_paid DOUBLE , created_date DATETIME, finished_date DATETIME, FOREIGN KEY(account_id) REFERENCES accounts(account_id),
 FOREIGN KEY(employee_id) REFERENCES employees(employee_id));
 
 CREATE TABLE invoice_notes(invoice_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_id INT, note BLOB, created_date DATETIME,
 FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id));
 
 CREATE TABLE invoice_item_types(invoice_item_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(32), created_date DATETIME);
 
 CREATE TABLE invoice_items(invoice_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_id INT,invoice_item_type_id INT, item_id INT, price_id INT,
 quantity INT,tax DOUBLE, adjustment DOUBLE, is_return BOOLEAN, FOREIGN KEY(invoice_id) REFERENCES invoices(invoice_id), 
 FOREIGN KEY(invoice_item_type_id) REFERENCES invoice_item_types(invoice_item_type_id), FOREIGN KEY(price_id) REFERENCES prices(price_id));
 
 CREATE TABLE invoice_item_notes(invoice_item_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_item_id INT, note BLOB, created_date DATETIME, 
 FOREIGN  KEY(invoice_item_id) REFERENCES invoice_items(invoice_item_id));
 
 CREATE TABLE rental_item(rental_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_item_id INT, serialized_item_id INT,
 FOREIGN KEY(invoice_item_id) REFERENCES invoice_items(invoice_item_id), FOREIGN KEY(serialized_item_id) REFERENCES serialized_items(serialized_item_id));
 
 CREATE TABLE servicing_types(servicing_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, servicing_type_name VARCHAR(64));
 
CREATE TABLE servicings(servicing_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_item_id INT);

CREATE TABLE servicing_items(servicing_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, serialized_item_id INT, servicing_id INT,  started_date DATETIME,
 completed_date DATETIME, next_service_date DATE, employee_id INT, servicing_type INT, FOREIGN KEY (serialized_item_id) REFERENCES serialized_items(serialized_item_id), 
 FOREIGN KEY (servicing_id) REFERENCES servicings(servicing_id), FOREIGN KEY( servicing_type) REFERENCES servicing_types(servicing_type_id),
 FOREIGN KEY(employee_id) REFERENCES employees(employee_id));
 
 CREATE TABLE servicing_item_parts(servicing_item_part_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, servicing_item_id INT, invoice_item_id INT , 
 FOREIGN KEY(servicing_item_id) REFERENCES servicing_items(servicing_item_id));
 
 
 
 INSERT INTO countries(country_name, country_abbreviation) VALUES ('UNITED STATES', 'USA');
 



 
CALL create_state('ALASKA','AK','USA'); 

CALL create_address('1234 STREET','ANCHORAGE','AK');

CALL create_person('NOT','A','PERSON','M','10030 GEBHART DR','ANCHORAGE','AK');


CALL create_person('CHRISTOPHER','MICHAEL','TYDINGS','M','10030 GEBHART DR','ANCHORAGE','AK');

CALL create_employee('CHRISTOPHER','MICHAEL','TYDINGS','STANDARD');



SELECT * FROM persons;

SELECT * FROM employees;


 
