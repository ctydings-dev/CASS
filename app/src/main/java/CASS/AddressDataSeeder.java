/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.address.AddressDTO;
import CASS.data.address.CityDTO;
import CASS.data.address.CountryDTO;
import CASS.data.address.StateDTO;
import CASS.services.AddressService;
import CASS.services.ServiceError;

/**
 *
 * @author ctydi
 */
public class AddressDataSeeder {
    public static int US, UK, CAN, FRN, AK, AL, CA,FL,NC,LELAND,ASHBURN, VA, ITY, ITY_ST, PADILAND, ANC,EAGLE_RIVER, SPAGETTILAND,GUE_LAND,GUE, HOME,PADI, MIKE,JD,KRIS, MARES;
    
    
    
    private static void createCities(AddressService addrSvc) throws ServiceError{
     
        CityDTO anc = new CityDTO("ANCHORAGE", AK,0);
        
        CityDTO eagle = new CityDTO("EAGLE RIVER", AK,0);
        
        CityDTO padi = new CityDTO("PADILAND", CA,0);
        
        CityDTO mares = new CityDTO("SPAGETTI LAND", ITY_ST,0);
        
        CityDTO gue = new CityDTO("CLEARWATER", FL,0);
        
        CityDTO leland = new CityDTO("LELAND", NC,0);
        
        CityDTO ash = new CityDTO("Ashburn", VA,0);
        
      ANC =  addrSvc.addCity(anc);
        
     PADILAND = addrSvc.addCity(padi);
     EAGLE_RIVER = addrSvc.addCity(eagle);
     SPAGETTILAND = addrSvc.addCity(mares);
     GUE_LAND = addrSvc.addCity(gue);
     LELAND = addrSvc.addCity(leland);
     ASHBURN = addrSvc.addCity(ash);
    }
    
    
    
    public static void seedAddresses(AddressService addrSvc) throws ServiceError{
           createCountries(addrSvc);
      createStates(addrSvc);
      createCities(addrSvc);
createAddresses(addrSvc);
    }
    
    
    private static void createAddresses(AddressService addrSvc) throws ServiceError{
        
        AddressDTO home = new AddressDTO("10030 GEBHART DR.", "","99515",ANC);
                AddressDTO mike = new AddressDTO("1234 U2GOOD4ANC DRIVE", "","99516",EAGLE_RIVER);
       
                AddressDTO jd = new AddressDTO("2458 COVESIDE CT.", "","99401",LELAND);
               AddressDTO kris = new AddressDTO("20936 CAPILANO CT.", "","28451",ASHBURN);
       
        
         AddressDTO padi = new AddressDTO("1234 PADI RD", "","123456",ANC);
         
          AddressDTO mares = new AddressDTO("TALKING W UR HANDS", "","123456",SPAGETTILAND);
         AddressDTO gue = new AddressDTO("ALL BLACK AND TAC", "","123456",GUE_LAND);
        
         
        MARES = addrSvc.addAddress(mares);
        HOME = addrSvc.addAddress(home);
        PADI = addrSvc.addAddress(padi);
     MIKE = addrSvc.addAddress(mike);
        JD = addrSvc.addAddress(jd);
        KRIS = addrSvc.addAddress(kris);
        GUE = addrSvc.addAddress(gue);
        

        
    }
    
    
    
    private static void createStates(AddressService addrSvc) throws ServiceError {

        String[] values = {"Alabama ", "AL ", "Kentucky ", "KY ", "Ohio ", "OH", "Alaska ", "AK ", "Louisiana ", "LA ", "Oklahoma ", "OK", "Arizona ", "AZ ", "Maine ", "ME ", "Oregon ", "OR", "Arkansas ", "AR ", "Maryland ", "MD ", "Pennsylvania ", "PA", "American Samoa ", "AS ", "Massachusetts ", "MA ", "Puerto Rico ", "PR", "California ", "CA ", "Michigan ", "MI ", "Rhode Island ", "RI", "Colorado ", "CO ", "Minnesota ", "MN ", "South Carolina ", "SC", "Connecticut ", "CT ", "Mississippi ", "MS ", "South Dakota ", "SD", "Delaware ", "DE ", "Missouri ", "MO ", "Tennessee ", "TN", "District of Columbia ", "DC ", "Montana ", "MT ", "Texas ", "TX", "Florida ", "FL ", "Nebraska ", "NE ", "Trust Territories ", "TT", "Georgia ", "GA ", "Nevada ", "NV ", "Utah ", "UT", "Guam ", "GU ", "New Hampshire ", "NH ", "Vermont ", "VT", "Hawaii ", "HI ", "New Jersey ", "NJ ", "Virginia ", "VA", "Idaho ", "ID ", "New Mexico ", "NM ", "Virgin Islands ", "VI", "Illinois ", "IL ", "New York ", "NY ", "Washington ", "WA", "Indiana ", "IN ", "North Carolina ", "NC ", "West Virginia ", "WV", "Iowa ", "IA ", "North Dakota ", "ND ", "Wisconsin ", "WI", "Kansas ", "KS ", "Northern Mariana Islands ", "MP ", "Wyoming ", "WY"};

        for (int index = 0; index < values.length; index += 2) {

            String name = values[index].trim();
            String abbv = values[index + 1].trim();
            StateDTO state = new StateDTO(name, abbv, US);
        int id =    addrSvc.addState(state);

        if(state.getAbbreviation().equals("AK")){
            AK = id;
        }
        
        if(state.getAbbreviation().equals("AL")){
            AL = id;
        }
        
           if(state.getAbbreviation().equals("CA")){
            CA = id;
        }
           if(state.getAbbreviation().equals("FL")){
            FL = id;
        }
         if(state.getAbbreviation().equals("NC")){
            NC = id;
        }
           if(state.getAbbreviation().equals("VA")){
            VA = id;
        } 
           
        
        }
        
        StateDTO state = new StateDTO("SPAGETTI STATE", "SGTY-OS", ITY);
        
        ITY_ST=  addrSvc.addState(state);

    }

    private static void createCountries(AddressService addrSvc) throws ServiceError {

        CountryDTO toAdd = new CountryDTO("UNITED STATES", "USA");

        US = addrSvc.addCountry(toAdd);
        toAdd = new CountryDTO("UNITED KINGDOM", "GB");

        UK = addrSvc.addCountry(toAdd);

        toAdd = new CountryDTO("CANADA", "CAN");

        CAN = addrSvc.addCountry(toAdd);
    toAdd = new CountryDTO("ITALY", "ITY");

        ITY = addrSvc.addCountry(toAdd);
        
        
        
    }

    
    
}
