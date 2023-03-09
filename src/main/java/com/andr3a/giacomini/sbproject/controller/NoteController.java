package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.model.entity.Folder;
import com.andr3a.giacomini.sbproject.model.entity.Note;
import com.andr3a.giacomini.sbproject.repository.INoteRepository;
import com.andr3a.giacomini.sbproject.service.FolderService;
import com.andr3a.giacomini.sbproject.service.NoteService;
import com.andr3a.giacomini.sbproject.utils.Constants;
import com.andr3a.giacomini.sbproject.model.dto.FolderDto;
import com.andr3a.giacomini.sbproject.model.dto.NoteDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private INoteRepository noteRepository;
    @Autowired
    private FolderService folderService;
    @Autowired
    private Logger log;
    @Autowired
    private ModelMapper modelMapper;

    @ModelAttribute("noteDto")
    public NoteDto noteDto(){
        return new NoteDto.NoteDtoBuilder().build();
    }

    @GetMapping("/admin/notes/create")
    public String createNoteForm(@RequestParam (name = "status") String status,
                                 @RequestParam (name = "noteId", required = false) Long noteId,
                                 @RequestParam (name = "noteTitle", required = false) String noteTitle,
                                 @RequestParam (name = "noteContent", required = false) String noteContent,
                                 @RequestParam (name = "folderId", required = false) Long folderId,
                                 @ModelAttribute("noteDto") NoteDto noteDto,
                                 Model model){
        model.addAttribute("folders", folderService.findFolders());
        model.addAttribute("status", status);

        if(status != null && !status.isEmpty() && (status.equals(Constants.STATUS_EDIT) || status.equals(Constants.STATUS_VIEW) ))
            model.addAttribute("noteDto", new NoteDto.NoteDtoBuilder().
                    id(noteId).
                    title(noteTitle).
                    content(noteContent).
                    folderDto(new FolderDto(folderId)).build());

        return "createNote";
    }

    @PostMapping("/admin/notes/create")
    public String saveOrUpdateNote(@RequestParam(value = "cancel", required = false) String cancel,
                                   @RequestParam(value = "submitStatus", required = false) String submitStatus,
                                   @ModelAttribute("noteDto") NoteDto noteDto,
                                   RedirectAttributes ra){
        log.trace("START saveOrUpdateNote()");
        // Saving new Note or Updating existing one
        if (noteDto.getTitle() != null && !noteDto.getTitle().isEmpty() && noteDto.getContent() != null && !noteDto.getContent().isEmpty() && cancel == null){

            if(submitStatus != null && !submitStatus.isEmpty() && submitStatus.equals(Constants.STATUS_EDIT)) {
                try {
                    log.trace("START Update");
                    Note noteToUpdate = noteService.getNoteById(noteDto.getId()).get();
                    log.info("BEFORE note :" + noteToUpdate.toString() );

                    noteToUpdate.setTitle(noteDto.getTitle() != null && !noteDto.getTitle().isEmpty() ? noteDto.getTitle() : "");
                    noteToUpdate.setContent(noteDto.getContent() != null && !noteDto.getContent().isEmpty() ? noteDto.getContent() : "");
                    noteToUpdate.setDone(noteDto.getDone() != null ? noteDto.getDone() : false );
                    if( noteDto.getFolderDto() != null ){
                        noteToUpdate.setFolder(new Folder(noteDto.getFolderDto().getId()));
                    }
                    noteService.updateNoteByNote(noteToUpdate);
                    ra.addFlashAttribute("noteUpdate", "success");
                    log.info("AFTER note :" + noteToUpdate.toString() );
                    log.trace("END Update");

                } catch (Exception exception) {
                    log.error(exception.getMessage());
                    ra.addFlashAttribute("noteUpdate", "error");
                }
            }
            else{
                try{
                    log.trace("START Save");
                    Folder folder = folderService.findFolderById(noteDto.getFolderDto().getId()).orElseThrow(RuntimeException::new);
                    Note newNote = new Note(noteDto.getTitle(), noteDto.getContent(), folder);
                    noteService.saveNoteByEntity(newNote);
                    ra.addFlashAttribute("noteCreate", "success");
                    log.trace("END Save");

                } catch(Exception exception){
                    log.error(exception.getMessage());
                    ra.addFlashAttribute("noteCreate", "error");
                }
            }
        }
        log.trace("END saveOrUpdateNote()");
        return "redirect:/homeByFolder?folderId=" + noteDto.getFolderDto().getId();
    }

    @GetMapping("/admin/notes/delete")
    String deleteNoteById(@RequestParam("noteId") Long noteId,
                          @RequestParam("folderId") Long folderId,
                          RedirectAttributes ra){
        log.trace("START deleteNoteById()");
        String noteToDeleteName = "";
        try{
            // Note noteToDelete = noteService.getNoteById(noteId).orElseThrow();
            Note noteToDelete = noteService.getNoteById(noteId).orElseThrow(RuntimeException::new);
            noteToDeleteName = noteToDelete.getTitle();
            log.info("deleteNoteById() - " + noteToDelete.toString() );
            noteService.deleteNoteByEntity(noteToDelete);

            log.info("Note: " + noteToDeleteName + " DELETED SUCCESSFULLY");
            ra.addFlashAttribute("noteDelete", "success");

        } catch (Exception exception){
            log.error(exception.getMessage());
            log.error("Note: " + noteToDeleteName + " DELETED ERROR");
            ra.addFlashAttribute("noteDelete", "error");
        }

        log.trace("END deleteNoteById()");
        return "redirect:/homeByFolder?folderId=" + folderId;
    }

    private NoteDto convertToDto(Note note) {
        NoteDto noteDto = modelMapper.map(note, NoteDto.class);
        return noteDto;
    }

    private Note convertToEntity(NoteDto noteDto) {
        Note note = modelMapper.map(noteDto, Note.class);
        return note;
    }
}