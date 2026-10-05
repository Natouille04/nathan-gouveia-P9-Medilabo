package com.medilabo.patientService.repository;

import com.medilabo.patientService.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}