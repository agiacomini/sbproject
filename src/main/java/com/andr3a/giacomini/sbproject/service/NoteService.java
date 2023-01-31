package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.entity.Note;
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

        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(Long noteId){

        Optional<Note> note;

        if(!noteRepository.findById(noteId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }
        note = Optional.of(noteRepository.findById(noteId).get());
        return note ;
    }

    public List<Note> findNotesByFolderId(Long folderId){

        return noteRepository.findNote2ByFolderId(folderId);
    }

    public Note saveNote(Note newNote){

        log.info("saveNote() - " + newNote.toString() );
        return noteRepository.save(newNote);
    }

    public Note updateNoteByNoteId(Long noteId, Note noteDto){

        log.trace("START updateNoteByNoteId()");
//        Note noteToUpdate = noteRepository.findById(noteId).orElseThrow();
        Note noteToUpdate = noteRepository.findById(noteId).orElseThrow(RuntimeException::new);
        log.info("Note BEFORE - " + noteToUpdate.toString() );

        noteToUpdate.setTitle(noteDto.getTitle() != null && !noteDto.getTitle().isEmpty() ? noteDto.getTitle() : "");
        noteToUpdate.setContent(noteDto.getContent() != null && !noteDto.getContent().isEmpty() ? noteDto.getContent() : "");
        noteToUpdate.setDone(noteDto.getDone() != null ? noteDto.getDone() : false );
        if( noteDto.getFolder() != null ){
            noteToUpdate.setFolder(noteDto.getFolder());
        }

        log.info("Note LATER - " + noteToUpdate.toString() );
        log.trace("END updateNoteByNoteId()");
        return noteRepository.save(noteToUpdate);
    }

    public ResponseEntity deleteNoteById(Long noteId){

//        log.info("deleteNoteById() - " + noteRepository.findById(noteId).orElseThrow().toString() );
        log.info("deleteNoteById() - " + noteRepository.findById(noteId).orElseThrow(RuntimeException::new).toString() );
        noteRepository.deleteById(noteId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteNote(Note note){

        log.info("deleteNote() - " + note.toString() );
        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
