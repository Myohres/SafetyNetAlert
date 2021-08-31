package com.safetynet.safetynetalert.repository;

import com.safetynet.safetynetalert.entity.MedicalRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEntity, Long> {
}
