package com.safetynet.safetynetalert.service;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import com.safetynet.safetynetalert.entity.PersonEntity;
import com.safetynet.safetynetalert.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MedicalRecordService {
    /** Repository link for MedicalRecordEntity.*/
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    /**
     * Person service layer.
     */
    @Autowired
    private PersonService personService;

    /**
     * Get all medical Records form BDD.
     * @return a list of all medical records
     */
    public List<MedicalRecordEntity> getMedicalRecords() {
        Iterable<MedicalRecordEntity> iteMre =
                medicalRecordRepository.findAll();
        List<MedicalRecordEntity> mreList = new ArrayList<>();
        iteMre.forEach(mreList::add);
        return mreList;
    }

    /**
     * Get medical Record by id.
     * @param id number unique of medical record
     * @return medical Record with id param
     */
    public MedicalRecordEntity getMedicalRecordById(final Long id)
            throws NoSuchElementException {
        Optional<MedicalRecordEntity> optMre =
                medicalRecordRepository.findById(id);
        return optMre.orElseThrow(()
                -> new NoSuchElementException(
                        "No MedicalRecord found with id " + id));
    }

    /**
     * Get persons List by Name.
     * Get medical Record for each persons list.
     * @param lastName lastname
     * @param firstName firstname
     * @return Medical Record List from name param
     */
    public List<MedicalRecordEntity> getMedicalRecordsByPerson(
            final String lastName,
            final String firstName) {
        List<PersonEntity> peList =
                personService.getPersonByName(lastName, firstName);
        List<MedicalRecordEntity> mrList = new ArrayList<>();
        peList.forEach(person -> mrList.add(person.getMedicalRecord()));
        return mrList;
    }

    /**
     * Get all medications from a MedicalRecord.
     * @param id number unique of medical record
     * @return Medications List
     */
    public List<String> getMedications(final Long id) {
        MedicalRecordEntity mre;
        try {
            mre = getMedicalRecordById(id);
            if (mre.getMedications().isEmpty()) {
                System.out.println(
                        "No medication found to medical record id " + id);
                return new ArrayList<>();
            }
            return mre.getMedications();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Get all allergies from a MedicalRecord.
     * @param id number unique of medical record
     * @return Allergies List
     */
    public List<String> getAllergies(final Long id) {
        MedicalRecordEntity mre;
        try {
            mre = getMedicalRecordById(id);
            if (mre.getAllergies().isEmpty()) {
                System.out.println(
                        "No allergies found to medical record id " + id);
                return new ArrayList<>();
            }
            return mre.getAllergies();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Add a new medical Record.
     * @param medicalRecordEntity list of medications and allergies
     * @return a new MedicalRecordEntity in DB.
     */
    public MedicalRecordEntity addMedicalRecord(
            final MedicalRecordEntity medicalRecordEntity) {
        return medicalRecordRepository.save(medicalRecordEntity);
    }

    /**
     * Add a medication to a medical Record.
     * @param id medical record id
     * @param medication new medication
     * @return updated Medical record
     */
    public MedicalRecordEntity addMedication(
            final long id,
            final String medication) {
        MedicalRecordEntity mre;
        mre = getMedicalRecordById(id);
        if (!mre.getMedications().contains(medication)) {
            mre.getMedications().add(medication);
            return medicalRecordRepository.save(mre);
        }
        System.out.println("Medication " + medication
                + " already exist in medical record with id : " + id);
        return mre;
    }

    /**
     * Add an allergy to a medical Record.
     * @param id medical record id
     * @param allergy new allergy
     * @return updated Medical record
     */
    public MedicalRecordEntity addAllergy(
            final long id,
            final String allergy) {
        MedicalRecordEntity mre;
        mre = getMedicalRecordById(id);
        if (!mre.getAllergies().contains(allergy)) {
            mre.getAllergies().add(allergy);
            return medicalRecordRepository.save(mre);
        }
        System.out.println("Allergy " + allergy
                + " already exist in medical record with id : " + id);
        return mre;
    }

    /**
     * Update Medical Record.
     * @param id medical record to change
     * @param medicalRecordEntity new information for medical record to change
     * @return Updated Medical Record
     */
    public MedicalRecordEntity replaceMedicalRecord(
            final long id,
            final MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        mre.setAllergies(medicalRecordEntity.getAllergies());
        mre.setMedications(medicalRecordEntity.getMedications());
        return medicalRecordRepository.save(mre);
    }

    /**
     * Update an allergy on a Medical Record.
     * @param id Medical Record to update
     * @param oldAllergy allergy to modified
     * @param newAllergy new allergy
     * @return Medical Record Updated
     */
    public MedicalRecordEntity changeAllergy(
            final long id,
            final String oldAllergy,
            final String newAllergy) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        mre.getAllergies().remove(oldAllergy);
        mre.getAllergies().add(newAllergy);
        return medicalRecordRepository.save(mre);
    }

    /**
     * Update a medication Medical Record.
     * @param id Medical Record to update
     * @param oldMedication Medication to modified
     * @param newMedication new medication
     * @return Medical Record Updated
     */
    public MedicalRecordEntity changeMedication(
            final long id,
            final String oldMedication,
            final String newMedication) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        mre.getMedications().remove(oldMedication);
        mre.getMedications().add(newMedication);
        return medicalRecordRepository.save(mre);
    }

    /**
     * Delete a medicalRecord by id.
     * @param id medicalRecord to delete
     */
    public void delMedicalRecordsById(final long id) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        PersonEntity person = personService.getPersonByMedicalRecord(mre);
        person.setMedicalRecord(null);
        personService.upDatePersonInfo(person);
        medicalRecordRepository.delete(mre);
    }

    /**
     * Delete a medical Record by person.
     * @param lastName lastname
     * @param firstName firstname
     */
    public void delMedicalRecordsByPerson(
            final String lastName,
            final String firstName) {
        List<PersonEntity> peList =
                personService.getPersonByName(lastName, firstName);
        peList.forEach(person ->
                medicalRecordRepository.delete(person.getMedicalRecord()));
    }

    /**
     * Delete all medications from a medical record.
     * @param id medical record to modified
     * @return MedicalRecord updated
     */
    public MedicalRecordEntity clearMedications(final long id) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        mre.getMedications().clear();
        return medicalRecordRepository.save(mre);

    }

    /**
     * Delete a medication from a medical record.
     * @param id medical record to modified
     * @param medication medication to delete
     * @return medicalRecord updated
     */
    public MedicalRecordEntity delOneMedication(
            final long id,
            final String medication) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        if (mre.getMedications().contains(medication)) {
            throw new NoSuchElementException(
                    "No medication " + medication + " in medical record " + id);
        }
        mre.getMedications().remove(medication);
        return medicalRecordRepository.save(mre);
    }

    /**
     * Delete all allergies form a medical record.
     * @param id medical record to modified
     * @return medicalRecord updated
     */
    public MedicalRecordEntity clearAllergies(final long id) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        mre.getAllergies().clear();
        return medicalRecordRepository.save(mre);
    }

    /**
     * Delete an allergy from a medical record.
     * @param id medical record to modified
     * @param allergy allergy to delete
     * @return medicalRecord updated
     */
    public MedicalRecordEntity delOneAllergy(
            final long id, final String allergy) {
        MedicalRecordEntity mre = getMedicalRecordById(id);
        if (mre.getAllergies().contains(allergy)) {
            throw new NoSuchElementException(
                    "No medication " + allergy + " in medical record " + id);
        }
        mre.getAllergies().remove(allergy);
        return medicalRecordRepository.save(mre);
    }
}
