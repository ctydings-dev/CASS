

INSERT INTO countries(country_name, abbv) VALUES ('United States','USA');

CALL create_state('ALABAMA','AL','USA');
CALL create_state('ALASKA','AK','USA');


CALL  create_address('1234 PADI DRIVE','PUT A DLR IN', 'PADILAND','AL','USA');

CALL get_address_id('1234 PADI DRIVE','PUT A DLR IN', 'PADILAND','AL','USA');

INSERT INTO companies(company_name,company_code, address_id, is_active, is_current) VALUES ('PROFESSIONAL ASSOCIATION OF DIVE INSTRUCTORS','PADI',@address_id, TRUE, TRUE);
INSERT INTO companies(company_name, company_code, address_id, is_active, is_current) VALUES ('RED CROSS','RED CROSS',@address_id, TRUE, TRUE);
INSERT INTO companies(company_name, company_code, address_id, is_active, is_current) VALUES ('SSI','SSI',@address_id, TRUE, TRUE);
INSERT INTO companies(company_name, company_code, address_id, is_active, is_current) VALUES ('GENERIC','GENERIC',@address_id, TRUE, TRUE);








INSERT INTO note_types(type_name) VALUES ('PERSON');
INSERT INTO note_types(type_name) VALUES ('CUSTOMER');
INSERT INTO note_types(type_name) VALUES ('EMPLOYMENT');
INSERT INTO note_types(type_name) VALUES ('INVOICE');
INSERT INTO note_types(type_name) VALUES ('COMPANY');
INSERT INTO note_types(type_name) VALUES ('OTHER');
INSERT INTO note_types(type_name) VALUES ('EDUCATION');

INSERT INTO certification_types(type_name) VALUES ('REC');
INSERT INTO certification_types(type_name) VALUES ('PRO');
INSERT INTO certification_types(type_name) VALUES ('FIRST AID');

INSERT INTO certification_requirement_types(type_name) VALUES ('BASIC OPEN WATER');
INSERT INTO certification_requirement_types(type_name) VALUES ('ADVANCED OPEN WATER');
INSERT INTO certification_requirement_types(type_name) VALUES ('DEEP DIVER');
INSERT INTO certification_requirement_types(type_name) VALUES ('RESCUE');


INSERT INTO certification_requirement_types(type_name) VALUES ('20 DIVES');

INSERT INTO certification_requirement_types(type_name) VALUES ('40 DIVES');

INSERT INTO certification_requirement_types(type_name) VALUES ('60 DIVES');


INSERT INTO certification_requirement_types(type_name) VALUES ('100 DIVES');

INSERT INTO certification_requirement_types(type_name) VALUES ('3 SPECIALTIES');

INSERT INTO certification_requirement_types(type_name) VALUES ('FIRST AID');
INSERT INTO certification_requirement_types(type_name) VALUES ('CPR');

INSERT INTO certification_requirement_types(type_name) VALUES ('EMERGENCY O2 DELIVERY');

INSERT INTO certification_requirement_types(type_name) VALUES ('DIVE MASTER');

CALL add_certification('OPEN WATER','REC','PADI');
CALL add_certification('OPEN WATER','REC','SSI');
CALL add_certification('ADVANCED OPEN WATER','REC','PADI');
CALL add_certification('ADVANCED OPEN WATER','REC','SSI');
CALL add_certification('RESCUE','REC','PADI');
CALL add_certification('RESCUE','REC','SSI');


CALL add_certification('DEEP DIVER','REC','PADI');
CALL add_certification('NITROX','REC','PADI');
CALL add_certification('DRY SUIT','REC','PADI');
CALL add_certification('NITROX','REC','SSI');
CALL add_certification('DIVE MASTER','PRO','PADI');
CALL add_certification('DIVE MASTER','PRO','SSI');
CALL add_certification('INSTRUCTOR','PRO','PADI');
CALL add_certification('INSTRUCTOR','PRO','SSI');
CALL add_certification('CPR','FIRST AID','RED CROSS');

CALL add_certification('BASIC FIRST AID','FIRST AID','RED CROSS');

CALL add_certification('EMERGENCY O2 DELIVERY','FIRST AID','PADI');

CALL add_certification('20 DIVES','REC','GENERIC');

CALL add_certification('40 DIVES','REC','GENERIC');

CALL add_certification('60 DIVES','REC','GENERIC');

CALL add_certification('100 DIVES','REC','GENERIC');




CALL add_cert_req('ADVANCED OPEN WATER','REC','PADI','BASIC OPEN WATER');
CALL add_cert_req('ADVANCED OPEN WATER','REC','SSI','BASIC OPEN WATER');
CALL add_cert_req_fufilled('ADVANCED OPEN WATER','REC','PADI','ADVANCED OPEN WATER');
CALL add_cert_req_fufilled('ADVANCED OPEN WATER','REC','SSI','ADVANCED OPEN WATER');




CALL add_cert_req('RESCUE','REC','PADI','ADVANCED OPEN WATER');
CALL add_cert_req('RESCUE','REC','SSI','ADVANCED OPEN WATER');
CALL add_cert_req('RESCUE','REC','PADI','DEEP DIVER');
CALL add_cert_req('RESCUE','REC','SSI','DEEP DIVER');
CALL add_cert_req_full('RESCUE','REC','PADI','FIRST AID',TRUE,TRUE,365,FALSE);
CALL add_cert_req_full('RESCUE','REC','SSI','FIRST AID',TRUE,TRUE,365,FALSE);
CALL add_cert_req_full('RESCUE','REC','PADI','CPR',TRUE,TRUE,365,FALSE);
CALL add_cert_req_full('RESCUE','REC','SSI','CPR',TRUE,TRUE,365,FALSE);

CALL add_cert_req_fufilled('RESCUE','REC','PADI','RESCUE');
CALL add_cert_req_fufilled('RESCUE','REC','SSI','RESCUE');



CALL add_cert_req('DIVE MASTER','PRO','PADI','RESCUE');
CALL add_cert_req('DIVE MASTER','PRO','SSI','RESCUE');

CALL add_cert_req_full('DIVE MASTER','PRO','PADI','FIRST AID',TRUE,TRUE,365,TRUE);
CALL add_cert_req_full('DIVE MASTER','PRO','SSI','FIRST AID',TRUE,TRUE,365,TRUE);
CALL add_cert_req_full('DIVE MASTER','PRO','PADI','CPR',TRUE,TRUE,365,TRUE);
CALL add_cert_req_full('DIVE MASTER','PRO','SSI','CPR',TRUE,TRUE,365,TRUE);

CALL add_cert_req_full('DIVE MASTER','PRO','PADI','40 DIVES',TRUE,FALSE,365,TRUE);
CALL add_cert_req_full('DIVE MASTER','PRO','SSI','40 DIVES',TRUE,FALSE,365,TRUE);
CALL add_cert_req_full('DIVE MASTER','PRO','PADI','60 DIVES',FALSE,FALSE,365,TRUE);
CALL add_cert_req_full('DIVE MASTER','PRO','SSI','60 DIVES',FALSE,FALSE,365,TRUE);



CALL add_cert_req_full('INSTRUCTOR','PRO','PADI','FIRST AID',TRUE,TRUE,365,TRUE);
CALL add_cert_req_full('INSTRUCTOR','PRO','SSI','FIRST AID',TRUE,TRUE,365,TRUE);
CALL add_cert_req_full('INSTRUCTOR','PRO','PADI','CPR',TRUE,TRUE,365,TRUE);
CALL add_cert_req_full('INSTRUCTOR','PRO','SSI','CPR',TRUE,TRUE,365,TRUE);






INSERT INTO employee_types(type_name) VALUES ('EMPLOYEE');
INSERT INTO employee_types(type_name) VALUES ('MANAGER');
INSERT INTO employee_types(type_name) VALUES ('CONSULTANT');
INSERT INTO employee_types(type_name) VALUES ('INSTRUCTOR');


INSERT INTO inventory_transaction_types(type_name) VALUES ('SHIPMENT');
INSERT INTO inventory_transaction_types(type_name) VALUES ('PURCHASE');
INSERT INTO inventory_transaction_types(type_name) VALUES ('RETURN');
INSERT INTO inventory_transaction_types(type_name) VALUES ('ADJUSTMENT');


