package com.safetynet.safetynetalert.service;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsByFireStationAddressesDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAddressPhoneDTO;
import com.safetynet.safetynetalert.web.dto.groupProfil.FamilyByAddressDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameAgeDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNameDTO;
import com.safetynet.safetynetalert.web.dto.groupProfil.PersonsWhenFireDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonNamePhoneAgeMedical;
import com.safetynet.safetynetalert.web.dto.personProfil.MedicalRecordDTO;
import com.safetynet.safetynetalert.web.dto.personProfil.PersonFullDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class URLService {
    /**
     * Person Service layer.
     */
    @Autowired
    private PersonService personService;
    /**
     * FireStation service layer.
     */
    @Autowired
    private FireStationService fireStationService;

    /**
     * Get persons served by a fire Station.
     * Get an addresses list by a fireStation
     * Get persons by this addresses list,
     * iterate adult and child number.
     * @param id station number
     * @return Persons list
     */
    public PersonsByFireStationAddressesDTO getPersonByFireStation(
            final long id) {
        PersonsByFireStationAddressesDTO personFFSA =
                new PersonsByFireStationAddressesDTO();
        List<PersonNameAddressPhoneDTO> personsList = new ArrayList<>();
        List<String> addressList =
                fireStationService.getAddressByFireStation(id);
        List<PersonEntity> peList =
                personService.getPersonsByAddresses(addressList);
        peList.forEach(personEntity -> {
            PersonNameAddressPhoneDTO person = new PersonNameAddressPhoneDTO();
            person.setLastName(personEntity.getLastName());
            person.setFirstName(personEntity.getFirstName());
            person.setAddress(personEntity.getAddress());
            person.setPhone(personEntity.getPhone());
            if (personService.majorPerson(personEntity)) {
                personFFSA.setNbAdult(personFFSA.getNbAdult() + 1);
            } else {
                personFFSA.setNbChild(personFFSA.getNbChild() + 1);
            }
            personsList.add(person);
        });
        personFFSA.setPersons(personsList);
        return personFFSA;
    }

    /**
     * Get child list by address.
     * Get adult List by address.
     * @param address street name and number
     * @return child list and adult list
     */
    public FamilyByAddressDTO getFamilyByAddress(final String address) {
        FamilyByAddressDTO familyByAddressDTO = new FamilyByAddressDTO();
        List<PersonEntity> family = personService.getPersonsByAddress(address);
        List<PersonNameAgeDTO> childList = family
                .stream()
                .filter(personEntity ->
                        (!(personService.majorPerson(personEntity))))
                .map(personEntity -> {
                    PersonNameAgeDTO child = new PersonNameAgeDTO();
                    child.setLastName(personEntity.getLastName());
                    child.setFirstName(personEntity.getFirstName());
                    child.setAge(personEntity.getBirthDate());
                    return child;
                }).collect(Collectors.toList());
        List<PersonNameDTO> adultList = family
                .stream()
                .filter(personEntity -> personService.majorPerson(personEntity))
                .map(personEntity -> {
                    PersonNameDTO adult = new PersonNameDTO();
                    adult.setLastName(personEntity.getLastName());
                    adult.setFirstName(personEntity.getFirstName());
                    return adult;
                }).collect(Collectors.toList());
        familyByAddressDTO.setAdultList(adultList);
        familyByAddressDTO.setChildList(childList);
        return familyByAddressDTO;
    }

    /**
     * Get phone number from person served by a fireStation.
     * Get addresses List by FireStation.
     * Get persons List by Adresses List,
     * Get phone List by persons List.
     * @param id station number
     * @return phone List
     */
    public List<String> getPhoneByFireStation(final long id) {
        List<String> phoneList = new ArrayList<>();
        List<String> addressList =
                fireStationService.getAddressByFireStation(id);
        List<PersonEntity> peList =
                personService.getPersonsByAddresses(addressList);
        peList.forEach(personEntity ->
                phoneList.add(personEntity.getPhone()));
        return phoneList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Get Persons lived in address fire.
     * Get FireStations List by address,
     * Send fireStation to fireStation list.
     * Get persons List by address,
     * Send persons List to personDTO List,
     * Send personDTO list to object list person
     * @param address address on fire
     * @return fireStations List and Persons List
     */
    public PersonsWhenFireDTO getPersonsWhenFireByAddress(
            final String address) {
        PersonsWhenFireDTO personWhenFireDTO = new PersonsWhenFireDTO();
        List<Long> fireStations =
                fireStationService.getFireStationByAddress(address)
                        .stream()
                        .map(FireStationEntity::getStation)
                        .collect(Collectors.toList());
        List<PersonNamePhoneAgeMedical> personFireList =
                personService.getPersonsByAddress(address)
                        .stream()
                        .map(any -> {
                            PersonNamePhoneAgeMedical peDTO =
                                    new PersonNamePhoneAgeMedical();
                            peDTO.setPersonPhAgMrDTO(any);
                            return peDTO;
                        }).collect(Collectors.toList());
        personWhenFireDTO.setStations(fireStations);
        personWhenFireDTO.setPersons(personFireList);
        return personWhenFireDTO;
    }

    /**
     * Get addresses list by fireStation id.
     * for each address from addresses list
     *  Get personsEntity List by address
     *  send personEntity to personDTO
     *  add personDTO to List personsDTO
     *  add list personsDTO and address to foyerDTO
     * @param fireStations station list
     * @return  Address List with persons associated
     */
    public Map<String, List<PersonNamePhoneAgeMedical>> getFoyersByFireStations(
            final List<Long> fireStations) {
        Map<String, List<PersonNamePhoneAgeMedical>> foyerMap = new TreeMap<>();
        List<String> addressList =
                fireStationService.getAddressByFireStations(fireStations);
        addressList.forEach(address -> {
            List<PersonNamePhoneAgeMedical> personsDTOList =
                    personService.getPersonsByAddress(address)
                            .stream()
                            .map(any -> {
                                PersonNamePhoneAgeMedical peDTO =
                                        new PersonNamePhoneAgeMedical();
                                peDTO.setPersonPhAgMrDTO(any);
                                return peDTO;
                            }).collect(Collectors.toList());
                    foyerMap.put(address, personsDTOList);
                });
        return foyerMap;
    }

    /**
     * Get persons List by Name.
     * Send personsEntity to personDTO
     * @param firstName firstname person
     * @param lastName lastname person
     * @return persons DTO list
     */
    public List<PersonFullDTO> getPersonInfo(
            final String firstName, final String lastName) {
       return personService.getPersonByName(lastName, firstName)
                .stream()
                .map(personEntity -> {
                    PersonFullDTO peDTO = new PersonFullDTO();
                    peDTO.setLastName(personEntity.getLastName());
                    peDTO.setFirstName(personEntity.getFirstName());
                    peDTO.setAddress(personEntity.getAddress());
                    peDTO.setCity(personEntity.getCity());
                    peDTO.setZip(personEntity.getZip());
                    peDTO.setAge(personEntity.getBirthDate());
                    peDTO.setEmail(personEntity.getEmail());
                    MedicalRecordDTO mrDTO = new MedicalRecordDTO();
                    mrDTO.setAllergies(personEntity.
                            getMedicalRecord().getAllergies());
                    mrDTO.setMedications(
                            personEntity.getMedicalRecord().getMedications());
                    peDTO.setMedicalRecord(mrDTO);
                    return peDTO;
                }).collect(Collectors.toList());
    }

    /**
     * Get all persons List by city.
     * Collect Person email.
     * @param city city name
     * @return Emails list
     * */
    public List<String> getEmailsCity(final String city)  {
            List<PersonEntity> peList = personService.getPersonByCity(city);
            List<String> emailList = new ArrayList<>();
            peList.forEach(personEntity ->
                    emailList.add(personEntity.getEmail()));
            return emailList;
    }
}

