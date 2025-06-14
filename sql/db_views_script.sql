DROP VIEW IF EXISTS vw_addresses;
CREATE VIEW vw_addresses AS SELECT adr.address_id, adr.street,  adr.post_code, cty.city_name, st.state_abbv AS state_abbv,  con.country_abbv AS country_abbv, adr.created_date AS address_created_date, cty.city_id,  st.state_id, st.state_name,  con.country_id, con.country_name,adr.street_2 FROM addresses AS adr INNER JOIN cities AS cty ON adr.city_id = cty.city_id INNER JOIN states AS st ON cty.state_id = st.state_id INNER JOIN countries AS con ON st.country_id = con.country_id;

DROP VIEW IF EXISTS vw_persons;
CREATE VIEW vw_persons AS SELECT pr.first_name, pr.middle_name, pr.last_name, pr.nickname, pr.alias, pr.sex, pr.is_current AS is_person_current, pr.is_active AS is_person_active, pr.created_date AS person_created_date, pr.person_id , pr.updated_date AS person_updated_date, vwa.* FROM persons AS pr INNER JOIN vw_addresses AS vwa ON pr.address_id = vwa.address_id;

DROP VIEW IF EXISTS vw_employees;
CREATE VIEW vw_employees AS SELECT emp.employee_id,emp.employee_code, emp.employee_type_id, empt.type_name AS employee_type, emp.hire_date, emp.is_active AS is_employee_active,  vwp.* FROM employees AS emp INNER JOIN employee_types AS empt ON emp.employee_type_id = empt.employee_type_id INNER JOIN vw_persons AS vwp ON emp.person_id = vwp.person_id;

DROP VIEW IF EXISTS vw_employee_roles;

CREATE VIEW vw_employee_roles AS SELECT prs.person_id, emp.employee_id, prs.first_name, prs.middle_name, prs.last_name, emp.employee_code, et.type_name FROM persons AS prs INNER JOIN employees AS emp ON prs.person_id = emp.person_id INNER JOIN employee_roles AS er ON emp.employee_id = er.employee_id INNER JOIN employee_role_types AS et ON er.role_id = et.employee_role_type_id;
 
 

DROP VIEW IF EXISTS vw_transactions;

CREATE VIEW vw_transactions AS SELECT emp.employee_code,itm.item_name,  it.quantity, itt.type_name, it.facility_id, it.inventory_transaction_id, it.item_id, itt.inventory_multiplyer FROM  items AS itm INNER JOIN inventory_transactions AS it  ON itm.item_id = it.item_id INNER JOIN inventory_transaction_types AS itt on it.inventory_transaction_type_id = itt.inventory_transaction_type_id INNER JOIN employees AS emp ON it.employee_id = emp.employee_id;
