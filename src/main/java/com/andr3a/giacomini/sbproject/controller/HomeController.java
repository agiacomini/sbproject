package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.entity.Note;
import com.andr3a.giacomini.sbproject.service.FolderService;
import com.andr3a.giacomini.sbproject.service.NoteService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    FolderService folderService;
    @Autowired
    NoteService noteService;
    @Autowired
    private Logger log;

    @RequestMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/")
    public String index(Model model){

        model.addAttribute("folders", folderService.findFolders());
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model){

        model.addAttribute("folders", folderService.findFolders());
        return "index";
    }

    @GetMapping("/homeByFolder")
    public String homeByFolder(@RequestParam("folderId") Long folderId, Model model){

        List<Note> noteList = noteService.findNotesByFolderId(folderId);

        model.addAttribute("folders", folderService.findFolders());
        model.addAttribute("notes", noteList);
        model.addAttribute("folderName", !noteList.isEmpty() ? noteList.get(0).getFolder().getNameFolder() : "#");
        return "index";
    }

    @GetMapping("/homeNote")
    public String listNoteByFolderId(@RequestParam("folderId") Long folderId, Model model){
        log.trace("START listNoteByFolderId() - FolderID: " + folderId);
        if(folderId != null){
            List<Note> noteList = noteService.findNotesByFolderId(folderId);
            log.info(noteList.toString());
            model.addAttribute("notes", noteList);
//            model.addAttribute("folderName", noteList.get(0).getFolder().getNameFolder());
        }
        log.trace("END listNoteByFolderId()");
        return "redirect:/homeByFolder?folderId=" + folderId;
    }
}