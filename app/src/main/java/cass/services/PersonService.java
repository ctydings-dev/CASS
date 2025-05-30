/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.services;

import cass.data.BaseDTO;
import cass.data.person.PersonDTO;
import java.util.List;

/**
 *
 * @author ctydi
 */
public interface PersonService {

    public PersonDTO getPerson(BaseDTO key) throws ServiceError;

    public List<PersonDTO> getPersons() throws ServiceError;

}
