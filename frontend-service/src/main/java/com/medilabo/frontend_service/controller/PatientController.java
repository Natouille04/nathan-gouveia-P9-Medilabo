package com.medilabo.frontend_service.controller;

import com.medilabo.frontend_service.dto.PatientCreationDTO;
import com.medilabo.frontend_service.dto.PatientDataDTO;
import com.medilabo.frontend_service.dto.PatientEditDTO;
import com.medilabo.frontend_service.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public String patientsListPage(Model model) {
        System.out.println("Liste de patient");

        model.addAttribute("patients", patientService.getAllPatients());
        return "patientsList";
    }

    @GetMapping("/patients/new")
    public String creationForm() {
        return "patientCreation";
    }

    @PostMapping("/patients/new")
    public String create(@ModelAttribute("patient") PatientCreationDTO patient, Model model) {
        try {
            patientService.createPatient(patient);
            return "redirect:/patients";
        }

        catch (HttpClientErrorException.Conflict e) {
            model.addAttribute("error", "Ce patient existe déjà");
        }

        catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", "Données invalides");
        }

        return "patientCreation";
    }

    @GetMapping("/patients/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        try {
            PatientDataDTO patient = patientService.getPatientById(id);
            model.addAttribute("patientInfo", patient);
            return "patientEdit";
        }

        catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", "L'id demandée n'existe pas");
        }

        return "redirect:/patients";
    }

    @PostMapping("/patients/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("patientInfo") PatientEditDTO newPatientData, Model model) {
        try {
            patientService.updatePatient(id, newPatientData);
            return "redirect:/patients";
        }

        catch (HttpClientErrorException.NotFound e) {
            model.addAttribute("error", "Ce patient n'existe plus");
        }

        catch (HttpClientErrorException.BadRequest e) {
            model.addAttribute("error", "Données invalides");
        }

        return "patientEdit";
    }
}
