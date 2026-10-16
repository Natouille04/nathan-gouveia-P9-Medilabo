package com.medilabo.notes_service.controller;

import java.util.List;

import com.medilabo.notes_service.dto.NoteCreationDTO;
import com.medilabo.notes_service.dto.NoteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.notes_service.service.NoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<NoteDTO>> getNoteByPatId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.findNoteByPatId(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> createNote(@RequestBody NoteCreationDTO dto) {
        noteService.createNote(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}