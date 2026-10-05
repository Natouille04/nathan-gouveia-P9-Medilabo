package com.medilabo.patientService.service;

import com.medilabo.patientService.dto.PatientInfoDTO;
import com.medilabo.patientService.dto.PatientModificationDTO;
import com.medilabo.patientService.exception.PatientAlreadyExistsException;
import com.medilabo.patientService.exception.PatientNotFoundException;
import com.medilabo.patientService.model.Patient;
import com.medilabo.patientService.repository.PatientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientInfoDTO> findAll() {
        List<PatientInfoDTO> result = new ArrayList<>();

        patientRepository.findAll().forEach(p ->
              result.add(new PatientInfoDTO(
                      p.getFirstName(),
                      p.getLastName(),
                      p.getBirthDate(),
                      p.getGenre(),
                      p.getAddress(),
                      p.getTelephone()
              ))
        );

        return result;
    }

    public PatientInfoDTO findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("The provided ID does not correspond to any patient in database"));

        return new PatientInfoDTO(
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                patient.getGenre(),
                patient.getAddress(),
                patient.getTelephone()
        );
    }

    public PatientInfoDTO findByNames(String firstName, String lastName) {
        Patient patient = patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new PatientNotFoundException("The provided ID does not correspond to any patient in database"));

        return new PatientInfoDTO(
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                patient.getGenre(),
                patient.getAddress(),
                patient.getTelephone()
        );
    }

    public void createPatient(PatientInfoDTO patientInfo) throws IllegalArgumentException, PatientAlreadyExistsException {
        if (patientInfo == null) {
            throw new IllegalArgumentException("Patient information must not be null");
        }

        if (patientRepository.existsByFirstNameAndLastNameAndBirthDate(patientInfo.firstName(), patientInfo.lastName(), patientInfo.birthDate())) {
            throw new PatientAlreadyExistsException("This patient already exists in database");
        }

        Patient patient = new Patient();
        patient.setFirstName(patientInfo.firstName());
        patient.setLastName(patientInfo.lastName());
        patient.setBirthDate(patientInfo.birthDate());
        patient.setGenre(patientInfo.genre());
        patient.setAddress(patientInfo.address());
        patient.setTelephone(patientInfo.telephone());

        patientRepository.save(patient);
    }

    @Transactional
    public Patient editPatient(Long id, PatientModificationDTO patientUpdatedInfo) throws IllegalArgumentException, PatientNotFoundException {
        if (patientUpdatedInfo == null) {
            throw new IllegalArgumentException("Modification data must not be null");
        }

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("No patient found with id " + id));

        if (patientUpdatedInfo.firstName() != null) patient.setFirstName(patientUpdatedInfo.firstName());
        if (patientUpdatedInfo.lastName() != null) patient.setLastName(patientUpdatedInfo.lastName());
        if (patientUpdatedInfo.birthDate() != null) patient.setBirthDate(patientUpdatedInfo.birthDate());
        if (patientUpdatedInfo.genre() != null) patient.setGenre(patientUpdatedInfo.genre());
        if (patientUpdatedInfo.address() != null) patient.setAddress(patientUpdatedInfo.address());
        if (patientUpdatedInfo.telephone() != null) patient.setTelephone(patientUpdatedInfo.telephone());

        return patientRepository.save(patient);
    }
}
