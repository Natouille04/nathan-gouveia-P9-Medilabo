package com.medilabo.patientService.controller;

import com.medilabo.patientService.dto.PatientInfoDTO;
import com.medilabo.patientService.dto.PatientModificationDTO;
import com.medilabo.patientService.exception.PatientAlreadyExistsException;
import com.medilabo.patientService.exception.PatientNotFoundException;
import com.medilabo.patientService.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {
    PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PatientInfoDTO>> getAllPatient() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientInfoDTO> getPatientById(@PathVariable Long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'id du patient est invalide");
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(patientService.findById(id));
        }

        catch (PatientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/{lastName}/{firstName}")
    public ResponseEntity<PatientInfoDTO> getPatientByNames(@PathVariable String lastName, @PathVariable String firstName) {
        if (lastName.isBlank() || firstName.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom et le prénom sont obligatoires");
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(patientService.findByNames(firstName, lastName));
        }

        catch (PatientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> createPatient(@RequestBody @Valid PatientInfoDTO patientInfo) {
        try {
            patientService.createPatient(patientInfo);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        catch (PatientAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePatient(
            @PathVariable Long id,
            @RequestBody @Valid PatientModificationDTO pateintUpdatedInfo
    ) {
        if(id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'id du patient est invalide");
        }

        try {
            patientService.editPatient(id, pateintUpdatedInfo);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        catch(PatientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
