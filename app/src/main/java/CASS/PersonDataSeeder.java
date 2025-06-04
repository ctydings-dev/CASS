/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.TypeRepository;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.util.DateUtils;

/**
 *
 * @author ctydi
 */
public class PersonDataSeeder {
    
    
    
    public static int ME,ME_EMP, MIKE, JD, MIKE_EMP,JD_EMP, KRIS, KRIS_EMP;
    
    public static void seedPersonData(PersonService svc) throws ServiceError{
        
        addMe(svc);
       addOthers(svc);
        addEmployees(svc);
        
    }
    
    
    private static void addMe(PersonService svc) throws ServiceError{

        PersonDTO meDTO = new PersonDTO("CHRISTOPHER","MICHAEL","TYDINGS","BUBBLES","CMT_MAIN",'M',true,true,AddressDataSeeder.HOME,"","","1990-06-10",0);
        
      ME =   svc.addPerson(meDTO);
    }
    
    
    private static void addOthers(PersonService svc) throws ServiceError {
       PersonDTO mikeDTO = new PersonDTO("MICHAEL","MIKE_MIDDLE","JONES","SEA DOG","SEA_DOG_ACC",'M',true,true,AddressDataSeeder.MIKE,"","","2001-05-15",0);
          PersonDTO jdDTO = new PersonDTO("JAKMES","DELANORE","LASTO","JD","BIG_HOSS_ACC",'M',true,true,AddressDataSeeder.JD,"","","1998-12-04",0);
         PersonDTO krissDTO = new PersonDTO("KRISS","UNKNOWN","CAPYPSO","KRIS","KRISS_ACC",'M',true,true,AddressDataSeeder.KRIS,"","","1995-07-07",0);
        
        MIKE = svc.addPerson(mikeDTO);
        JD = svc.addPerson(jdDTO);
        KRIS = svc.addPerson(krissDTO);
        
    }
    
    
    private static void addEmployees(PersonService svc) throws ServiceError{
   int type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.CONSULTANT);
   String hire = DateUtils.createDate(31,5,2025);
        EmployeeDTO meDTO = new EmployeeDTO(ME,hire,type,true,"DEVELOPER",0,"");
       type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.EMPLOYEE);
     hire = DateUtils.createDate(31,5,2024);
        
        EmployeeDTO mikeDTO = new EmployeeDTO(MIKE,hire,type,true,"MK-STD",0,"");
        hire = DateUtils.createDate(1,5,2020);
               type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.MANAGER);
        EmployeeDTO jdDTO = new EmployeeDTO(JD,hire,type,true,"JD-STD",0,"");
        hire = DateUtils.createDate(31,10,2023);
               type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.TECHNICIAN);
        EmployeeDTO krisDTO = new EmployeeDTO(KRIS,hire,type,true,"KR-STD",0,"");
        
        
        
        ME_EMP = svc.addEmployee(meDTO);
        MIKE_EMP = svc.addEmployee(mikeDTO);
        JD_EMP = svc.addEmployee(jdDTO);
        KRIS_EMP = svc.addEmployee(krisDTO);
        
        
        
        
    }
}
