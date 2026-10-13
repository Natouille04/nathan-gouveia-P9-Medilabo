package com.medilabo.notes_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.notes_service.dto.NoteCreationDTO;
import com.medilabo.notes_service.dto.NoteDTO;
import com.medilabo.notes_service.model.Note;
import com.medilabo.notes_service.repository.NoteRepository;

@Service
public class NoteService {
    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> findNoteByPatId(Long patId) {
        return noteRepository.findByPatId(patId)
                .stream()
                .map(note -> new NoteDTO(note.note()))
                .toList();
    }

    public void createNote(NoteCreationDTO dto) {
        Note newNote = new Note(null, dto.patId(), dto.note());
        noteRepository.save(newNote);
    }
}
