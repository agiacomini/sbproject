package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.entity.Folder;
import com.andr3a.giacomini.sbproject.entity.Note;
import com.andr3a.giacomini.sbproject.service.FolderService;
import com.andr3a.giacomini.sbproject.service.NoteService;
import com.andr3a.giacomini.sbproject.utils.Constants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private FolderService folderService;
    @Autowired
    private Logger log;

    @GetMapping("/admin/notes/create")
    public String createNoteForm(@RequestParam (name = "status") String status,
                                 @RequestParam (name = "noteId", required = false) Long noteId,
                                 @RequestParam (name = "noteTitle", required = false) String noteTitle,
                                 @RequestParam (name = "noteContent", required = false) String noteContent,
                                 @RequestParam (name = "folderId", required = false) Long folderId,
                                 Model model){
        model.addAttribute("folders", folderService.findFolders());
        model.addAttribute("status", status);

        if(noteId != null) model.addAttribute("noteId", noteId);
        if(noteTitle != null && !noteTitle.isEmpty()) model.addAttribute("noteTitle", noteTitle);
        if(noteContent != null && !noteContent.isEmpty()) model.addAttribute("noteContent", noteContent);
        if(folderId != null) model.addAttribute("folderId", folderId);

        return "createNote";
    }

    @PostMapping("/admin/notes/create")
    public String saveOrUpdateNote(@RequestParam(value = "noteTitle", required = false) String noteTitle,
                                   @RequestParam(value = "noteContent", required = false) String noteContent,
                                   @RequestParam(value = "noteId", required = false) Long noteId,
                                   @RequestParam(value = "folderSelected", required = false) Long folderId,
                                   @RequestParam(value = "folderS", required = false) Long folderS,
                                   @RequestParam(value = "cancel", required = false) String cancel,
                                   @RequestParam(value = "submitStatus", required = false) String submitStatus){
        // Saving new Note or Updating existing Note
        if (noteTitle != null && !noteTitle.isEmpty() && noteContent!= null && !noteContent.isEmpty() && cancel == null){

            if(submitStatus != null && !submitStatus.isEmpty() && submitStatus.equals(Constants.STATUS_EDIT))
                noteService.updateNoteByNoteId(noteId, new Note(noteTitle, noteContent, new Folder(folderId)));
            else{
                Folder folder = folderService.findFolderById(folderId).orElseThrow();
                Note newNote = new Note(noteTitle, noteContent, folder);
                noteService.saveNote(newNote);
            }
        }

        if(folderId != null || folderS != null)
            return "redirect:/homeByFolder?folderId=" + (folderId != null ? folderId : folderS);
        return "redirect:/home";
    }

    @GetMapping("/admin/notes/delete")
    String deleteNoteById(@RequestParam("noteId") Long noteId,
                          @RequestParam("folderId") Long folderId,
                          Model model){
        log.trace("START deleteNoteById()");
        Note noteToDelete = noteService.getNoteById(noteId).orElseThrow();
        log.info("deleteNoteById() - " + noteToDelete.toString() );
        noteService.deleteNote(noteToDelete);

        model.addAttribute("folderId", folderId);
        model.addAttribute("notes", noteService.findNotesByFolderId(folderId));

        log.trace("END deleteNoteById()");
        return "redirect:/homeByFolder?folderId=" + folderId;
    }
}