DROP DATABASE IF EXISTS DA19785;
CREATE DATABASE DA19785;
USE DA19785;
 
 CREATE TABLE phone_numbers(phone_number_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, phone_number VARCHAR(16), phone_type VARCHAR(32));
 
 CREATE TABLE countries(country_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, country_name VARCHAR(64), country_abbv VARCHAR(4), created_date DATETIME);
 
 
 ALTER TABLE countries MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;
 
 
 CREATE TABLE note_types(note_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(64) UNIQUE);
 
 
 
 
 
 CREATE TABLE states(state_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, country_id INT, state_name varchar(64), state_abbv VARCHAR(4),created_date DATETIME, FOREIGN KEY(country_id) REFERENCES countries(country_id));
 

 ALTER TABLE states MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;

 CREATE TABLE cities(city_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, city_name VARCHAR(64), state_id INT, created_date DATETIME);
 
 
 ALTER TABLE cities MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;
 
 CREATE TABLE addresses(address_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, street VARCHAR(64), street_2 VARCHAR(64), post_code VARCHAR(16), city_id INT,
 FOREIGN KEY(city_id) REFERENCES cities(city_id), created_date DATETIME);
 
 
 
 ALTER TABLE addresses MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;

 
 CREATE TABLE persons(person_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, first_name VARCHAR(64), middle_name VARCHAR(64), last_name VARCHAR(64), nickname VARCHAR(64), alias VARCHAR(256) UNIQUE,
 sex CHAR, is_current BOOLEAN, is_active BOOLEAN,  address_id INT, created_date DATETIME, updated_date DATETIME, birthday DATE);
 
 ALTER TABLE persons MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;

  
  CREATE TABLE historic_persons(historic_person_id INT, current_person_id INT, PRIMARY KEY(historic_person_id) 
 , FOREIGN KEY(historic_person_id) REFERENCES persons(person_id), FOREIGN KEY(current_person_id) REFERENCES persons(person_id));
 
 
 CREATE TABLE person_notes(person_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, note TEXT, created_date DATETIME,note_type_id INT,
 FOREIGN KEY(person_id) REFERENCES persons(person_id), FOREIGN KEY(note_type_id) REFERENCES note_types(note_type_id));
 
  ALTER TABLE person_notes MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;

 CREATE TABLE person_phone_number(person_phone_number_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, phone_number_id INT,
 FOREIGN KEY(person_id) REFERENCES persons(person_id), FOREIGN KEY(phone_number_id) REFERENCES phone_numbers(phone_number_id));
  
  CREATE TABLE accounts(account_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, account_number VARCHAR(32) UNIQUE, account_name VARCHAR(64) UNIQUE, person_id INT , created_date DATE, closed_date DATE);
  
  CREATE TABLE employee_types(employee_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(64));
  
  CREATE TABLE employee_role_types(employee_role_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(64));
  
  CREATE TABLE employees(employee_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT UNIQUE, employee_type_id INT, 
  hire_date DATE, is_active BOOLEAN, employee_code VARCHAR(32) UNIQUE, created_date DATETIME,
  FOREIGN KEY(person_id) REFERENCES persons(person_id), FOREIGN KEY(employee_type_id) REFERENCES employee_types(employee_type_id) );
   
   ALTER TABLE employees MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;
  
  
  
  CREATE TABLE employee_roles(employee_role_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, employee_id INT, role_id INT, created_date DATETIME);
  

 CREATE TABLE companies(company_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, company_name VARCHAR(64) UNIQUE, company_code VARCHAR(64) UNIQUE, notes TEXT,
 address_id INT, is_active BOOLEAN, is_current BOOLEAN);
 
 CREATE TABLE company_persons(company_person_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, company_id INT, person_id INT, 
 FOREIGN KEY (company_id) REFERENCES companies(company_id), FOREIGN KEY(person_id) REFERENCES persons(person_id));
 
  CREATE TABLE certification_types(certification_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,type_name VARCHAR(32) UNIQUE);
 
 CREATE TABLE certifications(certification_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, company_id INT, certification_name VARCHAR(32), certification_type_id INT, FOREIGN KEY(company_id) REFERENCES companies(company_id));
 
 
 ALTER TABLE certifications ADD CONSTRAINT unique_cert UNIQUE ( certification_name, certification_type_id, company_id);
 
 CREATE TABLE person_agency_numbers( person_agency_number_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, company_id INT, agency_code VARCHAR(64), created_date DATETIME, awarded_date DATE, FOREIGN KEY(person_id) REFERENCES persons(person_id), FOREIGN KEY(company_id) REFERENCES companies(company_id));
 
 
 ALTER TABLE person_agency_numbers MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;

 
 CREATE TABLE person_certifications(person_certification_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, person_id INT, certification_id INT, certification_code VARCHAR(32), earned_date DATE, created_date DATETIME, FOREIGN KEY(person_id) REFERENCES persons(person_id), FOREIGN KEY(certification_id) REFERENCES certifications(certification_id));
 
 ALTER TABLE person_certifications MODIFY created_date datetime DEFAULT CURRENT_TIMESTAMP;

 CREATE TABLE certification_requirement_types(certification_requirement_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(64) UNIQUE);
 
 CREATE TABLE certification_requirements(certification_requirement_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, certification_id INT, certification_requirement_type_id INT, quantity INT, required_to_start BOOLEAN, expires BOOLEAN, duration INT, required_to_maintain BOOLEAN, FOREIGN KEY(certification_requirement_type_id) REFERENCES certification_requirement_types(certification_requirement_type_id), FOREIGN KEY(certification_id) REFERENCES certifications(certification_id));
 
 ALTER TABLE certification_requirements ADD CONSTRAINT unique_cert_req UNIQUE ( certification_id, certification_requirement_type_id);
 
  ALTER TABLE certification_requirements MODIFY quantity INT DEFAULT 1;


  ALTER TABLE certification_requirements MODIFY required_to_start BOOLEAN DEFAULT FALSE;
  

  ALTER TABLE certification_requirements MODIFY expires BOOLEAN DEFAULT FALSE;
  
  
  ALTER TABLE certification_requirements MODIFY required_to_maintain BOOLEAN DEFAULT FALSE;
    ALTER TABLE certification_requirements MODIFY duration INT DEFAULT 1;



 
 CREATE TABLE certification_requirements_fufilled(certification_requirement_fufilled_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, certification_id INT, certification_requirement_type_id INT, FOREIGN KEY(certification_requirement_type_id) REFERENCES certification_requirement_types(certification_requirement_type_id), FOREIGN KEY(certification_id) REFERENCES certifications(certification_id));
 
 ALTER TABLE certification_requirements_fufilled ADD CONSTRAINT unique_cert_req UNIQUE ( certification_id, certification_requirement_type_id);
 
 
 
 
 
 
 CREATE TABLE item_types(item_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_type_name VARCHAR(64) UNIQUE);
 
 CREATE TABLE items(item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,item_type INT, item_name VARCHAR(64),item_alias VARCHAR(128), is_for_sale BOOLEAN,
 company_id INT, created_date DATETIME, FOREIGN KEY(company_id) REFERENCES companies(company_id), FOREIGN KEY(item_type) REFERENCES item_types(item_type_id));
 
 CREATE TABLE serialized_items(serialized_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, 
 serial_number VARCHAR(64), FOREIGN KEY (item_id) REFERENCES items(item_id));
 
 CREATE TABLE item_notes(item_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, created_date DATETIME, note TEXT, note_type_id INT,
 FOREIGN KEY(item_id) REFERENCES items(item_id), FOREIGN KEY(note_type_id) REFERENCES note_types(note_type_id));
 
 CREATE TABLE serialized_item_notes(serailzied_item_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, serialized_item_id INT, 
 note TEXT, note_type_id INT,  created_date DATETIME, FOREIGN KEY(serialized_item_id) REFERENCES serialized_items(serialized_item_id), FOREIGN KEY(note_type_id) REFERENCES note_types(note_type_id));
 
 CREATE TABLE item_upc(item_upc_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, upc VARCHAR(32), FOREIGN KEY(item_id) REFERENCES items(item_id));
 
 CREATE TABLE inventory(inventory_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, item_id INT, stock INT);
 
 CREATE TABLE inventory_transaction_types(inventory_transaction_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(64) UNIQUE);
 
 CREATE TABLE inventory_transactions(inventory_transaction_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, inventory_id INT, amount INT, inventory_transaction_type_id INT,  transaction_date DATETIME, FOREIGN KEY(inventory_transaction_type_id) REFERENCES inventory_transaction_types(inventory_transaction_type_id));

  ALTER TABLE inventory_transactions MODIFY transaction_date datetime DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE inventory_transaction_notes(inventory_transaction_notes_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, inventory_transaction_id INT, notes TEXT);

 
 
 CREATE TABLE currencies(currency_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, currency_name VARCHAR(32), country_id INT, FOREIGN KEY(country_id) REFERENCES countries(country_id));
 
 CREATE TABLE prices(price_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, price_name VARCHAR(16), item_id INT, purchase_price DOUBLE, sell_price DOUBLE, 
 created_date DATETIME, ended_date DATETIME, is_special BOOLEAN, is_sale BOOLEAN, currency_id INT);
 
 CREATE TABLE invoices(invoice_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_name VARCHAR(32) UNIQUE, account_id INT,
 employee_id INT, amount_paid DOUBLE , created_date DATETIME, finished_date DATETIME, FOREIGN KEY(account_id) REFERENCES accounts(account_id),
 FOREIGN KEY(employee_id) REFERENCES employees(employee_id));
 
 CREATE TABLE invoice_notes(invoice_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_id INT, note TEXT, note_type_id INT , created_date DATETIME,
 FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id), FOREIGN KEY(note_type_id) REFERENCES note_types(note_type_id));
 
 CREATE TABLE invoice_item_types(invoice_item_type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, type_name VARCHAR(32) UNIQUE, created_date DATETIME);
 
 CREATE TABLE invoice_items(invoice_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_id INT,invoice_item_type_id INT, item_id INT, price_id INT,
 quantity INT, inventory_transaction_id INT,tax DOUBLE, adjustment DOUBLE, is_return BOOLEAN, FOREIGN KEY(invoice_id) REFERENCES invoices(invoice_id), 
 FOREIGN KEY(invoice_item_type_id) REFERENCES invoice_item_types(invoice_item_type_id), FOREIGN KEY(price_id) REFERENCES prices(price_id));
 
 CREATE TABLE invoice_item_notes(invoice_item_note_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, invoice_item_id INT, note TEXT,note_type_id INT, created_date DATETIME, 
 FOREIGN  KEY(invoice_item_id) REFERENCES invoice_items(invoice_item_id), FOREIGN KEY(note_type_id) REFERENCES note_types(note_type_id));
 
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
 

 
