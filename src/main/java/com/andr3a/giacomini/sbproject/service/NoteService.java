package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.model.entity.Note;
import com.andr3a.giacomini.sbproject.repository.INoteRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private Logger log;
    @Autowired
    private INoteRepository noteRepository;

    public Iterable<Note> getAllNotes(){

        log.info("getAllNotes() -" );
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long noteId){

        log.info("getNoteById() - id: " +  noteId);
        Optional<Note> note;

        if(!noteRepository.findById(noteId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }
        note = Optional.of(noteRepository.findById(noteId).get());
        return note ;
    }

    public List<Note> findNotesByFolderId(Long folderId){

        log.info("findNotesByFolderId() - id: " + folderId );
        return noteRepository.findNote2ByFolderId(folderId);
    }

    public Note saveNoteByEntity(Note newNote){

        log.info("saveNoteByEntity() - " + newNote.toString() );
        return noteRepository.save(newNote);
    }

    public Note updateNoteByNote(Note noteToUpdate){
        return noteRepository.save(noteToUpdate);
    }

    public ResponseEntity deleteNoteById(Long noteId){

        log.info("deleteNoteById() - " + noteRepository.findById(noteId).orElseThrow(RuntimeException::new).toString() );
        noteRepository.deleteById(noteId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteNoteByEntity(Note note){

        log.info("deleteNoteByEntity() - " + note.toString() );
        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
