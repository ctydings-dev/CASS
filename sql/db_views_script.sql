DROP VIEW IF EXISTS vw_addresses;
CREATE VIEW vw_addresses AS SELECT adr.address_id, adr.street,  adr.post_code, cty.city_name, st.state_abbv AS state_abbv,  con.country_abbv AS country_abbv, adr.created_date AS address_created_date, cty.city_id,  st.state_id, st.state_name,  con.country_id, con.country_name,adr.street_2 FROM addresses AS adr INNER JOIN cities AS cty ON adr.city_id = cty.city_id INNER JOIN states AS st ON cty.state_id = st.state_id INNER JOIN countries AS con ON st.country_id = con.country_id;

DROP VIEW IF EXISTS vw_persons;
CREATE VIEW vw_persons AS SELECT pr.first_name, pr.middle_name, pr.last_name, pr.nickname, pr.alias, pr.sex, pr.is_current AS is_person_current, pr.is_active AS is_person_active, pr.created_date AS person_created_date, pr.person_id , pr.updated_date AS person_updated_date, vwa.* FROM persons AS pr INNER JOIN vw_addresses AS vwa ON pr.address_id = vwa.address_id;

DROP VIEW IF EXISTS vw_employees;
CREATE VIEW vw_employees AS SELECT emp.employee_id,emp.employee_code, emp.employee_type_id, empt.type_name AS employee_type, emp.hire_date, emp.is_active AS is_employee_active,  vwp.* FROM employees AS emp INNER JOIN employee_types AS empt ON emp.employee_type_id = empt.employee_type_id INNER JOIN vw_persons AS vwp ON emp.person_id = vwp.person_id;

