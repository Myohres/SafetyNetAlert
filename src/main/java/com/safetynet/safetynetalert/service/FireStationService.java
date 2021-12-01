package com.safetynet.safetynetalert.service;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.repository.FireStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FireStationService {

    /** Repository link for FireStationEntity.*/
    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * Get all FireStation from BDD.
     * @return a list with all fire stations
     */
    public List<FireStationEntity> getFireStations() {
        Iterable<FireStationEntity> iteFse = fireStationRepository.findAll();
        List<FireStationEntity> fseList = new ArrayList<>();
        iteFse.forEach(fseList::add);
        return fseList;
    }

    /**
     * Get a FireStation by his id from BDD.
     * @param id number of fire station
     * @return a fire station with id param
     */
    public FireStationEntity getFireStationById(final long id)
            throws NoSuchElementException {
        Optional<FireStationEntity> optFse = fireStationRepository.findById(id);
        return optFse.orElseThrow(()
                -> new NoSuchElementException(
                "No Station found with id number : " + id));
    }

    /**
     * Get FireStations by Address from BDD.
     * @param address street name and number
     * @return a fire station list who serve at this address
     */
    public List<FireStationEntity> getFireStationByAddress(
            final String address) throws NoSuchElementException {
        List<FireStationEntity> fseList =
                fireStationRepository.findFireStationEntityByAddress(address);
        if (fseList.isEmpty()) {
            throw new NoSuchElementException(
                    "No station(s) found with address : " + address);
        }
        return fseList;
    }

    /**
     * Get Addresses from a FireStation.
     * @param id number of fire station
     * @return addresses served by this fire station
     */
    public List<String> getAddressByFireStation(final long id) {
        FireStationEntity fse = getFireStationById(id);
        List<String> addressList = fse.getAddress();
        if (addressList.isEmpty()) {
            throw new NoSuchElementException("No address in station :" + id);
        }
        return addressList;
    }

    /**
     * Get Address from several FireStations.
     * @param fireStations stations list
     * @return addresses served by fire station list
     */
    public List<String> getAddressByFireStations(
            final List<Long> fireStations) throws NoSuchElementException {
        List<String> addressList = new ArrayList<>();
        fireStations.forEach(id -> {
            try {
                addressList.addAll(getFireStationById(id).getAddress());
            } catch (NoSuchElementException e) {
                log.warn(e.getMessage());
            }
        });
        if (addressList.isEmpty()) {
            throw new NoSuchElementException(
                    "Any2 station found with station list : " + fireStations);
        }
        return addressList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Change number station.
     * Create new FireStation with new Id,
     * Move addresses from old station to new Station.
     * If new Station is already existing, juste moving addresses.
     * Delete old station.
     * @param oldId station number to change
     * @param newId new number station
     * @return fireStation with new number
     */
    public FireStationEntity changeStationNumber(
            final long oldId, final String newId) {
        FireStationEntity oldFse;
        oldFse = getFireStationById(oldId);
        FireStationEntity newFse = new FireStationEntity();
        newFse.setStation(newId);
        newFse.setAddress(oldFse.getAddress());
        if (fireStationRepository.existsById(newFse.getStation())) {
            FireStationEntity nextFse =
                    getFireStationById(Long.parseLong(newId));
            newFse.getAddress().forEach(
                    address -> nextFse.getAddress().add(address));
            deleteFireStation(oldId);
            fireStationRepository.save(nextFse);
            return nextFse;
        } else {
            deleteFireStation(oldId);
            fireStationRepository.save(newFse);
            return newFse;
        }
    }

    /**
     * Copy an address from a station to an other station.
     * Delete Address from old station.
     * @param oldId origin station number
     * @param address street name and number
     * @param newId destination station number
     * @return destination fire station updated
     */
    public FireStationEntity moveAddressStation(
            final long oldId, final String address, final long newId) {
        FireStationEntity oldFse = getFireStationById(oldId);
        if (!oldFse.getAddress().contains(address)) {
            throw new NoSuchElementException(
                    "No address " + address + " found in station " + oldId);
        }
        oldFse.getAddress().remove(address);
        if (fireStationRepository.existsById(newId)) {
            FireStationEntity fse = getFireStationById(newId);
            fse.getAddress().add(address);
            fireStationRepository.save(fse);
            return fse;
        } else {
            FireStationEntity newFse = new FireStationEntity();
            newFse.setStation(Long.toString(newId));
            List<String> addressList = new ArrayList<>();
            addressList.add(address);
            newFse.setAddress(addressList);
            fireStationRepository.save(newFse);
            return newFse;
        }
    }

    /** add FireStationEntity to BDD.
     * @param fireStationEntity new fire station
     * @return new Fire station in BDD
     */
    public FireStationEntity addFireStation(
            final FireStationEntity fireStationEntity) {
        fireStationRepository.save(fireStationEntity);
        return fireStationEntity;
    }

    /**
     * Add an address to a fireStation.
     * @param address street name and number
     * @param id number of fire station
     * @return saving fireStation updated to BDD
     */
    public FireStationEntity addAddress(
            final String address, final long id) {
        FireStationEntity fse = getFireStationById(id);
        if (!fse.getAddress().contains(address)) {
            fse.getAddress().add(address);
            fireStationRepository.save(fse);
        }
        return fse;
    }

    /**
     * Delete fireStation by id.
     * @param id number of fire station
     */
    public void deleteFireStation(final long id) {
        fireStationRepository.delete(getFireStationById(id));
    }

    /**
     * Delete an address by fireStation id.
     * @param id number of fire station
     * @param address street name and number
     * @return fireStation updated
     */
    public FireStationEntity deleteAddressStation(
            final long id, final String address) {
        FireStationEntity fse = getFireStationById(id);
        List<String> addressList = fse.getAddress();
        if (addressList.contains(address)) {
            addressList.remove(address);
            fse.setAddress(addressList);
            fireStationRepository.save(fse);
            return fse;
        } else {
            throw new NoSuchElementException(
                    "There are no address " + address + " in station " + id);
        }
    }

    /**
     * Delete all addresses from a fireStation by id.
     * @param id number of fire station
     * @return fireStation updated
     */
    public FireStationEntity deleteAllAddressesStation(final long id) {
        FireStationEntity fse = getFireStationById(id);
        fse.getAddress().clear();
        fireStationRepository.save(fse);
        return fse;
    }
}
