package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.model.entity.Folder;
import com.andr3a.giacomini.sbproject.service.FolderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class FolderController {
    @Autowired
    FolderService folderService;
    @Autowired
    private Logger log;

    @PostMapping("/admin/folder/create")
    public String createFolder(@Valid @RequestParam(name = "folderName") String folderName,
                               Model model){

        log.trace("START createFolder()");
        log.info("Folder Name: " + folderName);

        Folder newFolder = new Folder(folderName);
        try {
            folderService.createFolderByEntity(newFolder);
            model.addAttribute("folderCreate", "success");
        } catch (Exception e){
            model.addAttribute("folderCreate", "error");
            log.error(e.getMessage());
        }

        model.addAttribute("folders", folderService.findFolders());
        log.trace("END createFolder()");

        return "index";
    }

    @PostMapping("/admin/folders/delete")
    public String deleteFolder(@RequestParam(name = "folderId") Long folderId,
                               Model model){

        log.trace("START deleteFolder()");
        log.info("deleteFolder() - Folder id: " + folderId);
        try{
            folderService.deleteFolderByFolderId(folderId);
            model.addAttribute("folderDelete", "success");

        } catch (Exception e){
            model.addAttribute("folderDelete", "error");
            log.error(e.getMessage());
        }
        model.addAttribute("folders", folderService.findFolders());

        log.trace("END deleteFolder()");
//        return "redirect:/home";
        return "index";
    }
}