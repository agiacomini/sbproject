package com.andr3a.giacomini.sbproject.controller.rest;

import com.andr3a.giacomini.sbproject.model.entity.Folder;
import com.andr3a.giacomini.sbproject.model.entity.Note;
import com.andr3a.giacomini.sbproject.service.NoteService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class NoteControllerRest {

    @Autowired
    private Logger log;

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    Iterable<Note> getNotes(){
        return noteService.getAllNotes();
    }

    @GetMapping("/notes/{noteId}")
    Note getNoteById(@PathVariable Long noteId){
        Optional<Note> note = noteService.getNoteById(noteId);
        return note.get();
    }

    @GetMapping("/notesByFolderId/{folderId}")
    List<Note> getNotesByFolderId(@PathVariable Long folderId){

        return noteService.findNotesByFolderId(folderId);
    }

    @PutMapping("/notes/update/{noteId}")
    Note updateNoteById(@PathVariable Long noteId, @RequestBody @NotNull Note note){

        log.trace("START updateNoteById()");
        Note noteToUpdate = null;
        try{
            noteToUpdate = noteService.getNoteById(noteId).get();
            log.info("BEFORE note :" + noteToUpdate.toString() );

            noteToUpdate.setTitle(note.getTitle() != null && !note.getTitle().isEmpty() ? note.getTitle() : "");
            noteToUpdate.setContent(note.getContent() != null && !note.getContent().isEmpty() ? note.getContent() : "");
            noteToUpdate.setDone(note.getDone() != null ? note.getDone() : false );
            if( note.getFolder() != null ){
                noteToUpdate.setFolder(new Folder(note.getFolder().getId()));
            }
            noteToUpdate = noteService.updateNoteByNote(noteToUpdate);
            log.info("AFTER note :" + noteToUpdate.toString() );

        } catch(Exception exception){
            log.error(exception.getMessage());
        }
        log.trace("END updateNoteById()");
        return noteToUpdate;
    }

    @PostMapping("/notes")
    Note createNote(@Valid @RequestBody Note newNote){

        log.trace("START createNote()");
        Note noteToSave = noteService.saveNoteByEntity(newNote);
        log.info("createNote() - " + noteToSave.toString() );
        log.trace("END createNote()");
        return noteToSave;
    }

    @DeleteMapping("/notes/delete/{noteId}")
    ResponseEntity deleteNoteById(@PathVariable("noteId") Long noteId){

        return noteService.deleteNoteById(noteId);
    }
}