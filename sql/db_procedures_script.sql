  
 SET @address_id :=0 ;
 SET @person_id := 0;
 SET @employee_id := 0;
 SET @invoice_id := 0;
 SET @alias := '';
 SET @new_alias := ''; 
 SET @type_id := 0;
 
 
 
 DROP PROCEDURE IF EXISTS create_state;
 DELIMITER //
 CREATE PROCEDURE create_state(state_name varchar(64), state_abbv VARCHAR(4), country_abbv VARCHAR(4))
 BEGIN
 IF( SELECT COUNT(*) < 1 FROM states AS st INNER JOIN countries AS con ON st.country_id = con.country_id WHERE st.state_abbv = state_abbv AND con.country_abbv = country_abbv)
 THEN
 INSERT INTO states(state_name, state_abbv, country_id) SELECT state_name, state_abbv, country_id FROM countries WHERE country_abbv = country_abbv;
 END IF;
 END //
 DELIMITER ;
 
 
 DROP PROCEDURE IF EXISTS create_city;
  DELIMITER //
 CREATE PROCEDURE create_city(cty_name varchar(64), state_abbv VARCHAR(4), country_abbv VARCHAR(4))
 BEGIN
 IF( SELECT COUNT(cit.city_id) < 1 FROM cities AS cit INNER JOIN states AS st ON cit.state_id = st.state_id INNER JOIN countries AS con ON st.country_id = con.country_id WHERE st.state_abbv = state_abbv AND con.country_abbv = country_abbv AND cit.city_name = cty_name) 
 THEN
 INSERT INTO cities(city_name, state_id) SELECT cty_name , st.state_id FROM states AS st INNER JOIN countries AS con ON st.country_id = con.country_id WHERE st.state_abbv = state_abbv AND con.country_abbv = country_abbv;
 END IF;
 END //
 DELIMITER ;
 
 
 
 
 
 
 DROP PROCEDURE IF EXISTS create_address;
  DELIMITER //
 CREATE PROCEDURE create_address(street VARCHAR(64), post_code VARCHAR(16), city VARCHAR(64), state_abbv VARCHAR(4), country_abbv VARCHAR(4))
 BEGIN
 CALL create_city(city,state_abbv, country_abbv);
 IF( SELECT COUNT(adr.address_id)< 1 FROM addresses AS adr INNER JOIN cities AS  cty ON adr.city_id = cty.city_id INNER JOIN states AS st ON cty.state_id = st.state_id INNER JOIN countries AS con ON st.country_id = con.country_id WHERE adr.street = street AND adr.post_code = post_code AND cty.city_name = city AND st.state_abbv = state_abbv AND con.country_abbv = country_abbv)
 THEN
 INSERT INTO addresses(street, street_2, post_code, city_id) SELECT street, '', post_code, cty.city_id FROM cities AS cty INNER JOIN states AS st ON cty.state_id = st.state_id INNER JOIN countries AS con ON st.country_id = con.country_id WHERE cty.city_name = city AND st.state_abbv = state_abbv AND con.country_abbv = country_abbv;
 END IF;
 END //
 DELIMITER ;
 
 
 
 DROP PROCEDURE IF EXISTS get_address_id;
  DELIMITER //
 CREATE PROCEDURE get_address_id(street VARCHAR(64), post_code VARCHAR(16), city VARCHAR(64), state_abbv VARCHAR(4), country_abbv VARCHAR(4))
 BEGIN
SET @address_id := 0;
 SELECT @address_id := adr.address_id FROM addresses AS adr INNER JOIN cities AS  cty ON adr.city_id = cty.city_id INNER JOIN states AS st ON cty.state_id = st.state_id INNER JOIN countries AS con ON st.country_id = con.country_id WHERE adr.street = street AND adr.post_code = post_code AND cty.city_name = city AND st.state_abbv = state_abbv AND con.country_abbv = country_abbv;

 END //
 DELIMITER ;
 
 
 
 
 DROP PROCEDURE IF EXISTS create_person_alias;
    DELIMITER //
    SET @alias := '';
 CREATE PROCEDURE create_person_alias(first_name  VARCHAR(64), middle_name  VARCHAR(64), last_name  VARCHAR(64))
 BEGIN
  SET @alias := CONCAT(first_name,'-',middle_name,'-' ,last_name);
  END //
 DELIMITER ;
 
 
 
 DROP PROCEDURE IF EXISTS create_person;
   DELIMITER //
 CREATE PROCEDURE create_person(first_name  VARCHAR(64), middle_name  VARCHAR(64), last_name  VARCHAR(64))
 BEGIN
 CALL create_person_alias(first_name, middle_name,last_name);
 IF( SELECT COUNT(*) < 1 FROM persons WHERE alias = @alias) 
 THEN
 INSERT INTO persons(first_name, middle_name, last_name, alias, is_current, is_active) VALUES (first_name, middle_name, last_name, @alias,TRUE, TRUE);
 END IF;
 END //
 DELIMITER ;
 
 DROP PROCEDURE IF EXISTS add_person_address;
    DELIMITER //
 CREATE PROCEDURE add_person_address(first_name  VARCHAR(64), middle_name  VARCHAR(64), last_name  VARCHAR(64),street VARCHAR(64), post_code VARCHAR(16), city VARCHAR(64), state_abbv VARCHAR(4), country_abbv VARCHAR(4))
 BEGIN

CALL create_person(first_name, middle_name, last_name);
CALL create_address(street,post_code,city,state_abbv, country_abbv);
 CALL create_person_alias(first_name, middle_name,last_name);
 SELECT @address_id :=  adr.address_id FROM addresses AS adr INNER JOIN cities AS ct ON adr.city_id = ct.city_id INNER JOIN states AS st ON ct.state_id = st.state_id INNER JOIN countries AS co ON st.country_id = co.country_id WHERE adr.street = street AND adr.post_code = post_code AND ct.city_name = city AND st.state_abbv = state_abbv AND co.country_abbv = country_abbv;
UPDATE persons SET address_id = @address_id WHERE alias = @alias;
 END //
 DELIMITER ;
 
 
 DROP PROCEDURE IF EXISTS update_person_date_alias;
    DELIMITER //
 CREATE PROCEDURE update_person_date_alias(alias_id VARCHAR(64))
 BEGIN
UPDATE persons SET updated_date = CURRENT_TIMESTAMP WHERE alias = alias_id;
 END //
 DELIMITER ;
 
 DROP PROCEDURE IF EXISTS update_person_date;
     DELIMITER //
 CREATE PROCEDURE update_person_date(first_name  VARCHAR(64), middle_name  VARCHAR(64), last_name  VARCHAR(64))
 BEGIN
CALL create_person_alias(first_name, middle_name, last_name);
CALL update_person_date_alias(@alias);

 END //
 DELIMITER ;
 
 
DROP PROCEDURE IF EXISTS create_historic_person ;
 
      DELIMITER //
 CREATE PROCEDURE create_historic_person(alias_id_val VARCHAR(256))
 BEGIN
  SELECT @new_alias := COUNT(*) FROM historic_persons AS hp INNER JOIN persons AS pr ON hp.current_person_id = pr.person_id WHERE pr.alias = alias_id_val;
 SET @new_alias := CONCAT(alias_id_val ,@new_alias);
 INSERT INTO persons(first_name, middle_name, last_name , nickname, alias , sex , is_current, is_active,  address_id, created_date, updated_date, birth_date)  SELECT first_name, middle_name, last_name, nickname, @new_alias,sex, FALSE ,     is_active,address_id,  created_date, updated_date, birth_date FROM persons WHERE alias = alias_id_val;
 SELECT @new_alias := person_id FROM persons WHERE alias = @new_alias;
 SELECT @alias := person_id FROM persons WHERE alias = alias_id_val;
 INSERT INTO historic_persons(historic_person_id , current_person_id) VALUES ( @new_alias, @alias);
 CALL update_person_date_alias(alias_id_val);
 END //
 DELIMITER ;
 
 
 DROP PROCEDURE IF EXISTS add_person_birthday;
  DELIMITER //
CREATE PROCEDURE  add_person_birthday(alias_id VARCHAR(256), birthday DATE)
  BEGIN
  UPDATE persons SET birthday = birthday WHERE alias = alias_id;
 CALL update_person_date_alias(alias_id);
 END //
 DELIMITER ;
 
 
 
 DROP PROCEDURE IF EXISTS add_person_notes;
  DELIMITER //
CREATE PROCEDURE  add_person_notes(alias_id VARCHAR(256), note TEXT, note_type VARCHAR(32))
  BEGIN
  SET @type_id := 0;
SELECT @type_id := note_type_id FROM note_types WHERE type_name = note_type;
INSERT INTO person_notes(note,note_type_id, person_id) SELECT note, @type_id, person_id FROM persons WHERE alias = alias_id;
SET @type_id := 0;
 END //
 DELIMITER ;
 
 
 
 DROP PROCEDURE IF EXISTS make_employee;
   DELIMITER //
 CREATE PROCEDURE   make_employee(alias_id VARCHAR(256),role_name VARCHAR(32))
  BEGIN
  
  SELECT @new_alias := (COUNT(*)+1) FROM employees;
    SET @new_alias := CONCAT('EMPLOYEE-' ,@new_alias);
CALL add_person_notes(alias_id,  CONCAT('HIRED AS ',role_name), 'EMPLOYMENT');
   SELECT @type_id := employee_type_id FROM employee_types WHERE type_name = role_name;

INSERT INTO employees(person_id, employee_type_id,  employee_code ,hire_date , is_active ) SELECT person_id, @type_id, @new_alias , CURDATE(), TRUE FROM persons WHERE alias= alias_id;




 END //
 DELIMITER ;
 
 
 
  DROP PROCEDURE IF EXISTS make_employee_instructor;
   DELIMITER //
 CREATE PROCEDURE   make_employee_instructor(alias_id_value VARCHAR(256), cert_name VARCHAR(64), cert_number VARCHAR(32), cert_date DATE, company VARCHAR(64))
  BEGIN

CALL add_person_certification(alias_id_value, cert_number, cert_date, cert_name,'PRO', company);

CALL add_person_notes(alias_id_value,  'MADE INSTRUCTOR', 'EMPLOYMENT');

 END //
 DELIMITER ;
 
 
 
 DROP PROCEDURE IF EXISTS add_certification;
    DELIMITER //
 CREATE PROCEDURE   add_certification(certification_name VARCHAR(32), cert_type VARCHAR(64), company_name_val VARCHAR(64))
  BEGIN
  SELECT @type_id := certification_type_id FROM certification_types WHERE type_name =cert_type;
  INSERT INTO certifications(certification_name, certification_type_id, company_id) SELECT certification_name, @type_id, company_id FROM companies WHERE company_code = company_name_val;
  
 END //
 DELIMITER ;
 

 DROP PROCEDURE IF EXISTS add_person_certification;
    DELIMITER //
 CREATE PROCEDURE   add_person_certification(alias_id_value VARCHAR(256), cert_number VARCHAR(32), cert_date DATE, cert_name VARCHAR(32), cert_type VARCHAR(64), company_name_val VARCHAR(64))
  BEGIN
  SET @type_id := 0;
  SET @cert_id := 0;
  
    SELECT @type_id := certification_type_id FROM certification_types WHERE type_name =cert_type;
SELECT @type_id;
  SELECT @cert_id := certification_id FROM certifications AS cert INNER JOIN companies AS con ON cert.company_id = con.company_id WHERE cert.certification_name = cert_name AND con.company_code = company_name_val AND cert.certification_type_id = @type_id;
SELECT COUNT(pr.person_id) < 1 FROM person_certifications AS pc INNER JOIN persons AS pr ON pr.person_id = pc.person_id WHERE pr.alias = alias_id_value AND pc.certification_id = @cert_id AND pc.certification_code = cert_number;
IF( SELECT COUNT(pr.person_id) < 1 FROM person_certifications AS pc INNER JOIN persons AS pr ON pr.person_id = pc.person_id WHERE pr.alias = alias_id_value AND pc.certification_id = @cert_id AND pc.certification_code = cert_number)
THEN
CALL add_person_notes(alias_id_value,  CONCAT('ADDED CERT ',cert_name), 'EDUCATION');
  INSERT INTO person_certifications(person_id, certification_id, certification_code, earned_date) SELECT person_id , @cert_id, cert_number,cert_date FROM persons WHERE alias= alias_id_value;
END IF;
 END //
 DELIMITER ;
 

 DROP PROCEDURE IF EXISTS add_cert_req;
    DELIMITER //
 CREATE PROCEDURE   add_cert_req(certification_name VARCHAR(32), cert_type VARCHAR(64), company_code_val VARCHAR(64), req_val VARCHAR(64))
  BEGIN
  SELECT @type_id := certification_type_id FROM certification_types WHERE type_name =cert_type;
  SET @cert_id := 0;
  SELECT @cert_id := cert.certification_id FROM certifications AS cert INNER JOIN companies AS con ON cert.company_id = con.company_id WHERE cert.certification_name = certification_name AND cert.certification_type_id = @type_id AND con.company_code = company_code_val;
INSERT INTO certification_requirements(certification_id, certification_requirement_type_id) SELECT @cert_id, certification_requirement_type_id FROM certification_requirement_types WHERE type_name = req_val;
 END //
 DELIMITER ;
 
  DROP PROCEDURE IF EXISTS add_cert_req_full;
    DELIMITER //
 CREATE PROCEDURE   add_cert_req_full(certification_name VARCHAR(32), cert_type VARCHAR(64), company_code_val VARCHAR(64), req_val VARCHAR(64), required_to_start_val BOOLEAN, expires_val BOOLEAN, duration_val INT, required_to_maintain_val BOOLEAN )
  BEGIN
  SELECT @type_id := certification_type_id FROM certification_types WHERE type_name =cert_type;
  SET @cert_id := 0;
  SELECT @cert_id := cert.certification_id FROM certifications AS cert INNER JOIN companies AS con ON cert.company_id = con.company_id WHERE cert.certification_name = certification_name AND cert.certification_type_id = @type_id AND con.company_code = company_code_val;
INSERT INTO certification_requirements(certification_id, certification_requirement_type_id, required_to_start, expires, duration, required_to_maintain) SELECT @cert_id, certification_requirement_type_id, required_to_start_val, expires_val, duration_val, required_to_maintain_val FROM certification_requirement_types WHERE type_name = req_val;
 END //
 DELIMITER ;
 

  DROP PROCEDURE IF EXISTS add_cert_req_fufilled;
    DELIMITER //
 CREATE PROCEDURE   add_cert_req_fufilled(certification_name VARCHAR(32), cert_type VARCHAR(64), company_code_val VARCHAR(64), req_val VARCHAR(64))
  BEGIN
  SELECT @type_id := certification_type_id FROM certification_types WHERE type_name =cert_type;
  SET @cert_id := 0;
  SELECT @cert_id := cert.certification_id FROM certifications AS cert INNER JOIN companies AS con ON cert.company_id = con.company_id WHERE cert.certification_name = certification_name AND cert.certification_type_id = @type_id AND con.company_code = company_code_val;
INSERT INTO certification_requirements_fufilled(certification_id, certification_requirement_type_id) SELECT @cert_id, certification_requirement_type_id FROM certification_requirement_types WHERE type_name = req_val;
  
 END //
 DELIMITER ;
 
 
 
 
 