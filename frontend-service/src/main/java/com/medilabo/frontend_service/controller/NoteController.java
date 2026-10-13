package com.medilabo.frontend_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilabo.frontend_service.dto.NoteCreationDTO;
import com.medilabo.frontend_service.service.NoteService;

@Controller
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes/add")
    public String showAddNoteForm(@RequestParam Long patientId, Model model) {
        model.addAttribute("patientId", patientId);
        return "noteCreation";
    }

    @PostMapping("/notes/add")
    public String submitAddNoteForm(NoteCreationDTO dto) {        
        noteService.createNote(dto);
        return "redirect:/patients/" + dto.patId();
    }
}