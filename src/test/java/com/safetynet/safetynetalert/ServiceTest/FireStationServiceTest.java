package com.safetynet.safetynetalert.ServiceTest;

import com.safetynet.safetynetalert.entity.FireStationEntity;
import com.safetynet.safetynetalert.repository.FireStationRepository;
import com.safetynet.safetynetalert.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @InjectMocks
    private static FireStationService fireStationService;

    @Test
    void getFireStationsTest() {
        List<FireStationEntity> fseList = fireStationService.getFireStations();
        verify(fireStationRepository,times(1)).findAll();
        assertNotNull(fseList);
    }

    @Test
    void getFireStationByIdTest() {
        when(fireStationRepository.findById(any()))
                .thenReturn(Optional.of(new FireStationEntity()));
        fireStationService.getFireStationById(1);
        verify(fireStationRepository,times(1)).findById(1L);
    }

    @Test
    void getFireStationByIdNoIdFoundTest() {
        when(fireStationRepository.findById(any()))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.getFireStationById(1));
    }

    @Test
    void getFireStationByAddressTest() {
        List<FireStationEntity> fseList = new ArrayList<>();
        FireStationEntity fse = new FireStationEntity();
        fseList.add(fse);
        when(fireStationRepository.findFireStationEntityByAddress(any()))
                .thenReturn(fseList);
        fireStationService.getFireStationByAddress("");
        verify(fireStationRepository, times(1))
                .findFireStationEntityByAddress("");
    }

    @Test
    void getFireStationByAddressNoAddressFoundTest() {
        when(fireStationRepository.findFireStationEntityByAddress(any()))
                .thenReturn(new ArrayList<>());
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.getFireStationByAddress(any()));
    }

    @Test
    void getAddressByFireStationTest() {
        FireStationEntity fse = new FireStationEntity();
        List<String> addressList = new ArrayList<>();
        addressList.add("address1");
        fse.setAddress(addressList);
        when(fireStationRepository.findById(any())).thenReturn(Optional.of(fse));
        List<String> address = fireStationService.getAddressByFireStation(1);
        assertEquals(address.get(0),"address1");
    }

    @Test
    void getAddressByFireStationNoStationFoundTest() {
        when(fireStationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,()-> fireStationService.getAddressByFireStation(1L));
    }

    @Test
    void getAddressByFireStationNoAddressInStationFoundTest() {
        FireStationEntity fse = new FireStationEntity();
        fse.setStation("1");
        List<String> addressList = new ArrayList<>();
        fse.setAddress(addressList);
        when(fireStationRepository.findById(any())).thenReturn(Optional.of(fse));
        assertThrows(NoSuchElementException.class,()-> fireStationService.getAddressByFireStation(1));
    }

    @Test
    void getAddressByFireStationsTest() {
        List<Long> stationList = new ArrayList<>();
        stationList.add(1L);
        stationList.add(2L);
        FireStationEntity fse1 = new FireStationEntity();
        List<String> addressList1 = new ArrayList<>();
        addressList1.add("address1");
        fse1.setAddress(addressList1);
        FireStationEntity fse2 = new FireStationEntity();
        List<String> addressList2 = new ArrayList<>();
        addressList2.add("address2");
        fse2.setAddress(addressList2);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(fse1));
        when(fireStationRepository.findById(2L)).thenReturn(Optional.of(fse2));
        List<String> list = fireStationService.getAddressByFireStations(stationList);
        assertEquals(list.get(0),"address1");
        assertEquals(list.get(1),"address2");
    }

    @Test
    void getAddressByFireStationsNoSomeStationsFoundTest() {
        List<Long> stationList = new ArrayList<>();
        stationList.add(1L);
        stationList.add(2L);
        FireStationEntity fse = new FireStationEntity();
        List<String> addressList = new ArrayList<>();
        addressList.add("address1");
        fse.setAddress(addressList);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(fse));
        when(fireStationRepository.findById(2L)).thenReturn(Optional.empty());
        List<String> list = fireStationService.getAddressByFireStations(stationList);
        assertThrows(NoSuchElementException.class, () -> fireStationService.getFireStationById(2L));
        assertEquals(list.size(),1);
    }

    @Test
    void getAddressByFireStationsWithSomeStationWithNoAddressTest() {
        FireStationEntity fse1 = new FireStationEntity();
        List<String> addressList = new ArrayList<>();
        addressList.add("address1");
        fse1.setAddress(addressList);
        FireStationEntity fse2 = new FireStationEntity();
        List<String> addressList2 = new ArrayList<>();
        fse2.setAddress(addressList2);
        List<Long> stationList = new ArrayList<>();
        stationList.add(1L);
        stationList.add(2L);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(fse1));
        when(fireStationRepository.findById(2L)).thenReturn(Optional.of(fse2));
        List<String> address = fireStationService.getAddressByFireStations(stationList);
        assertEquals(address.size(),1);
    }

    @Test
    void getAddressByFireStationsWithNoStationFoundTest() {
        List<Long> stationList = new ArrayList<>();
        stationList.add(1L);
        when(fireStationRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->
                fireStationService.getAddressByFireStations(stationList));
    }

    @Test
    void changeStationNumberWithExistingIdTest() {
        FireStationEntity fse = new FireStationEntity();
        fse.setStation("1");
        List<String> addressList = new ArrayList<>();
        addressList.add("address1");
        fse.setAddress(addressList);
        FireStationEntity fse2 = new FireStationEntity();
        List<String> addressList2 = new ArrayList<>();
        addressList2.add("address2");
        fse2.setAddress(addressList2);
        fse2.setStation("2");
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(fse));
        when(fireStationRepository.existsById(any())).thenReturn(true);
        when(fireStationRepository.findById(2L)).thenReturn(Optional.of(fse2));
        fireStationService.changeStationNumber(1L,"2");
        assertEquals(fse2.getAddress().size(),2);
        verify(fireStationRepository,times(1)).save(any());
        verify(fireStationRepository,times(1)).delete(any());
    }

    @Test
    void changeStationNumberWithNewIdTest() {
        FireStationEntity fse = new FireStationEntity();
        fse.setStation("1");
        List<String> addressList = new ArrayList<>();
        addressList.add("address1");
        fse.setAddress(addressList);
        when(fireStationRepository.existsById(any())).thenReturn(false);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(fse));
        fireStationService.changeStationNumber(1,"2");
        verify(fireStationRepository,times(1)).save(any());
        verify(fireStationRepository,times(1)).delete(any());
    }

    @Test
    void changeStationNumberWithNoStationFound(){
        when(fireStationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                ()-> fireStationService.changeStationNumber(1,"2"));
    }

    @Test
    void moveAddressStationWithExistingStationTest() {
        FireStationEntity oldFse = new FireStationEntity();
        oldFse.setStation("1");
        List<String> addressList1 = new ArrayList<>();
        addressList1.add("address1");
        oldFse.setAddress(addressList1);
        FireStationEntity newFse = new FireStationEntity();
        newFse.setStation("2");
        List<String> addressList2 = new ArrayList<>();
        addressList2.add("address2");
        newFse.setAddress(addressList2);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(oldFse));
        when(fireStationRepository.findById(2L)).thenReturn(Optional.of(newFse));
        when(fireStationRepository.existsById(any())).thenReturn(true);
        fireStationService.moveAddressStation(1,"address1",2);
        assertEquals(0, oldFse.getAddress().size());
        assertEquals(2,newFse.getAddress().size());
        verify(fireStationRepository,times(1)).save(any());
    }

    @Test
    void moveAddressStationWithNewStationTest() {
        FireStationEntity oldFse = new FireStationEntity();
        oldFse.setStation("1");
        List<String> addressList1 = new ArrayList<>();
        addressList1.add("address1");
        oldFse.setAddress(addressList1);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(oldFse));
        when(fireStationRepository.existsById(2L)).thenReturn(false);
        fireStationService.moveAddressStation(1,"address1",2);
        assertEquals(0,oldFse.getAddress().size());
        verify(fireStationRepository,times(1)).save(any());
    }

    @Test
    void moveAddressStationWithOldStationNoFoundTest() {
        when(fireStationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () ->fireStationService.moveAddressStation(1,"address1",2));
    }

    @Test
    void addFireStationTest() {
        fireStationService.addFireStation(new FireStationEntity());
        verify(fireStationRepository,times(1)).save(any());
    }

    @Test
    void addAddressTest() {
        FireStationEntity newFse1 = new FireStationEntity();
        newFse1.setStation("1");
        List<String> addressList = new ArrayList<>();
        newFse1.setAddress(addressList);
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(newFse1));
        fireStationService.addAddress("1address1", 1);
        assertEquals(1, newFse1.getAddress().size());
        verify(fireStationRepository,times(1)).save(any());
    }

    @Test
    void addAddressInNoStationFoundTest() {
        when(fireStationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.addAddress("1address1", 1));
    }

    @Test
    void deleteFireStationTest() {
        when(fireStationRepository.findById(any()))
                .thenReturn(Optional.of(new FireStationEntity()));
        fireStationService.deleteFireStation(1);
        verify(fireStationRepository,times(1)).delete(any());
    }

    @Test
    void deleteFireStationNoStationFoundTest() {
        when(fireStationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.deleteFireStation(1));
    }

    @Test
    void deleteAddressStationTest() {
        FireStationEntity newFse1 = new FireStationEntity();
        newFse1.setStation("1");
        List<String> addressList1 = new ArrayList<>();
        addressList1.add("1address1");
        newFse1.setAddress(addressList1);
        when(fireStationRepository.findById(any())).thenReturn(Optional.of(newFse1));
        fireStationService.deleteAddressStation(1,"1address1");
        assertFalse(newFse1.getAddress().contains("1address1"));
        verify(fireStationRepository,times(1)).save(any());
    }

    @Test
    void deleteAddressStationIdNotFoundTest() {
        when(fireStationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.deleteAddressStation(1,"1address1"));
    }

    @Test
    public void deleteAddressStationWithNoAddressFoundTest(){
        FireStationEntity newFse1 = new FireStationEntity();
        newFse1.setStation("1");
        List<String> addressList1 = new ArrayList<>();
        newFse1.setAddress(addressList1);
        when(fireStationRepository.findById(any())).thenReturn(Optional.of(newFse1));
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.deleteAddressStation(1,"essai"));
    }

    @Test
    void deleteAllAddressesStationTest() {
        FireStationEntity newFse1 = new FireStationEntity();
        newFse1.setStation("1");
        List<String> addressList1 = new ArrayList<>();
        addressList1.add("1address1");
        addressList1.add("1address2");
        newFse1.setAddress(addressList1);
        when(fireStationRepository.findById(any())).thenReturn(Optional.of(newFse1));
        fireStationService.deleteAllAddressesStation(1L);
        verify(fireStationRepository,times(1)).save(any());
        assertTrue(newFse1.getAddress().isEmpty());
    }

    @Test
    void deleteAllAddressesIdNotFoundStationTest() {
        when(fireStationRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> fireStationService.deleteAllAddressesStation(1L));
    }
}
