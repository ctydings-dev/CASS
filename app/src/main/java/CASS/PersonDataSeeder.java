/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.manager.InventoryManager;
import CASS.util.DateUtils;

/**
 *
 * @author ctydi
 */
public class PersonDataSeeder {
    
    
    
    public static int ME,ME_EMP,ME_ACC,SHOP_ACC, MIKE,MIKE_ACC, JD,JD_ACC, MIKE_EMP,JD_EMP, KRIS, KRIS_EMP, GUE,XSCUBA,  MARES, PADI, LUX, MARES_MAN, LUX_MAN;
    
    public static void seedPersonData(PersonService svc) throws ServiceError{
        
     
       addPeople(svc);
     addEmployees(svc);
     addEmployeeRoles(svc);
        addCompanies(svc);
        
    }
    
    
    
    
    
   
    
    
    private static void addPeople(PersonService svc) throws ServiceError {
         PersonDTO meDTO = new PersonDTO("CHRISTOPHER","MICHAEL","TYDINGS","BUBBLES","CMT_MAIN",'M',true,true,AddressDataSeeder.HOME,"","","1990-06-10",0);
        
      
        PersonDTO mikeDTO = new PersonDTO("MICHAEL","MIKE_MIDDLE","JONES","SEA DOG","SEA_DOG_ACC",'M',true,true,AddressDataSeeder.MIKE,"","","2001-05-15",0);
          PersonDTO jdDTO = new PersonDTO("JAKMES","DELANORE","LASTO","JD","BIG_HOSS_ACC",'M',true,true,AddressDataSeeder.JD,"","","1998-12-04",0);
         PersonDTO krissDTO = new PersonDTO("KRISS","UNKNOWN","CAPYPSO","KRIS","KRISS_ACC",'M',true,true,AddressDataSeeder.KRIS,"","","1995-07-07",0);
        
              PersonDTO maresDTO = new PersonDTO("MARES","COMPANY","MARES","MARES", "MARES_ACC",'M',true,true,AddressDataSeeder.MARES,"","","1995-07-07",0);
        
         
            
              
              
         
          ME =   svc.addPerson(meDTO);
                MIKE = svc.addPerson(mikeDTO);
        JD = svc.addPerson(jdDTO);
        KRIS = svc.addPerson(krissDTO);
        MARES_MAN = svc.addPerson(maresDTO);
        
        TypeDTO  type = TypeRepository.getTypeDTO(TypeRepository.ACCOUNT_TYPE.CONSUMER);
      
        
        meDTO.setKey(ME);
        
        String name = InventoryManager.createAccountNumber(meDTO, type);
        
          AccountDTO meAcc = new AccountDTO(name,"CMT-"+ meDTO.getKey(),ME,type.getKey());
          type = TypeRepository.getTypeDTO(TypeRepository.ACCOUNT_TYPE.SHOP);
            AccountDTO shopAcc = new AccountDTO("DAK-SHOP","DAK-SHOP-1",JD,type.getKey());
          
 
          
          
          ME_ACC = svc.addAccount(meAcc).getKey();
          SHOP_ACC = svc.addAccount(shopAcc).getKey();
          
        
        
        
    }
    
    
    private static void addCompanies(PersonService svc) throws ServiceError{
        
       String gueNote = "ZE PURPOSE OF ZE SCUBA IS FUR PERCISION UNT EFFICENCY. ACTUNG! YOU ARE HAVING FUN!?!?!?! NEIN! NEIIIIIINNNNNN! ALSO, SINCE I HAVE YOUR ATTENTION HERE IS A LITTLE FUN FACT: THE MAX DEPTH FOR GUE DIVERS ON NITROX IS 100 FEET, WHICH IS CONSEQUENTLY SLIGHLY LESS THAT THE AVERAGE LENGTH OF STICK THAT A GUE DIVER WILL HAVE SHOVED UP THEIR ASS.";
        //gueNote = "test";
        CompanyDTO gue = new CompanyDTO("GLOBAL UNDERWATER EXPLORERS","GUE",AddressDataSeeder.GUE,true,true,gueNote);
        String maresNote = "NOT WELL LIKED, BUT COME ON, CLEARLY THE BEST. LET THE FLAME WARS BEGIN! STILL UGLY AS SIN. Except the Abyss. Ahhhhhh the Abyss. Beauty meets druability. Yeah I said it. The Abyss eats the ScubaPro S620 and shits it out. Which isn't saying much but still. I mean have you seen the 620? And don't even get met started on the D420. More like D420 Blaze It. Ammirite bois! Still the A700 is a looker and even I will admit it. However all of these punks are like 'its heavy.' Yeah, no shit jackass. It's heavy because its not made of tupperware, I mean 'Technopolymer.' ";
        CompanyDTO mares = new CompanyDTO("MARES","MARES",AddressDataSeeder.MARES,true,true,maresNote,0);
        CompanyDTO lux = new CompanyDTO("LUXIFOR","LUXIFOR",AddressDataSeeder.LUX,true,true,"INDUSTRY STANDARD TANK MAKER",0);
         CompanyDTO sherwood = new CompanyDTO("SHERWOOD","SHERWOOD",AddressDataSeeder.LUX,true,true,"FISHER PRICE MEETS SCUBA. Like seriously, these are the ugliest regulators known to man, and I dive a Mares!",0);
        CompanyDTO xscuba = new CompanyDTO("XSCUBA","XSCUBA" ,AddressDataSeeder.XSCUBA,true,true,"HOSES 'N SHIT",0);
         CompanyDTO scubapro = new CompanyDTO("SCUBAPRO","SCUBAPRO" ,AddressDataSeeder.XSCUBA,true,true,"Pretty a much a cult at this point.",0);
        
        
        CompanyDTO padi = new CompanyDTO("PADI","PADI",AddressDataSeeder.PADI,true,true,"PUT ANOTHER DOLLAR IN",0);
        
        
        
        GUE = svc.addCompany(gue);
        MARES = svc.addCompany(mares);
        PADI = svc.addCompany(padi);
         LUX = svc.addCompany(lux);
         XSCUBA = svc.addCompany(xscuba);
         svc.addCompany(sherwood);
         svc.addCompany(scubapro);
       mares = svc.getCompany(new BaseDTO(MARES));
        PersonDTO man = svc.getPerson(new BaseDTO(MARES_MAN));
        
        svc.addPersonToCompany(man, mares);
        
        
        
        
    }
    
    
    
    
    private static void addEmployees(PersonService svc) throws ServiceError{
   int type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.CONSULTANT);
   String hire = DateUtils.createDate(31,5,2025);
        EmployeeDTO meDTO = new EmployeeDTO(ME,hire,type,true,"CMT-CON",0,"");
       type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.EMPLOYEE);
     hire = DateUtils.createDate(31,5,2024);
        
        EmployeeDTO mikeDTO = new EmployeeDTO(MIKE,hire,type,true,"MK-STD",0,"");
        hire = DateUtils.createDate(1,5,2020);
               type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.EMPLOYEE);
        EmployeeDTO jdDTO = new EmployeeDTO(JD,hire,type,true,"JD-STD",0,"");
        hire = DateUtils.createDate(31,10,2023);
               type = TypeRepository.getKey(TypeRepository.EMPLOYEE_TYPE.EMPLOYEE);
        EmployeeDTO krisDTO = new EmployeeDTO(KRIS,hire,type,true,"KR-STD",0,"");
    
        ME_EMP = svc.addEmployee(meDTO);
        MIKE_EMP = svc.addEmployee(mikeDTO);
        JD_EMP = svc.addEmployee(jdDTO);
        KRIS_EMP = svc.addEmployee(krisDTO);
    }
    
    
    private static void addEmployeeRoles(PersonService svc) throws ServiceError{
        
        int type;
        TypeDTO role;
     EmployeeDTO jd = new EmployeeDTO(JD_EMP);
       EmployeeDTO mike = new EmployeeDTO(MIKE_EMP);
      
     EmployeeDTO kris = new EmployeeDTO(KRIS_EMP);
      
     type = TypeRepository.getKey(TypeRepository.EMPLOYEE_ROLE.MANAGER);
        role = new TypeDTO(type);
        svc.addRoleForEmployee(jd, role);
        
        type = TypeRepository.getKey(TypeRepository.EMPLOYEE_ROLE.CASHIER);
        role = new TypeDTO(type);
        svc.addRoleForEmployee(jd, role);
           svc.addRoleForEmployee(mike, role);
           svc.addRoleForEmployee(kris, role);
        
             type = TypeRepository.getKey(TypeRepository.EMPLOYEE_ROLE.TECHNICIAN);
        role = new TypeDTO(type);
        svc.addRoleForEmployee(jd, role);
                    svc.addRoleForEmployee(kris, role);
           
              type = TypeRepository.getKey(TypeRepository.EMPLOYEE_ROLE.INSTRUCTOR);
        role = new TypeDTO(type);
        svc.addRoleForEmployee(jd, role);
                    svc.addRoleForEmployee(kris, role);
           
        
    }
    
    
    
}
