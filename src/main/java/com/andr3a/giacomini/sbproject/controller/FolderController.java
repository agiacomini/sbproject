package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.entity.Folder;
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
    public String createFolder(@Valid @RequestParam(name = "folderName") String folderName, Model model){

        log.trace("START createFolder()");
        log.info("Folder Name: " + folderName);

        Folder newFolder = new Folder(folderName);
        folderService.createFolder(newFolder);

        model.addAttribute("folders", folderService.findFolders());
        log.trace("END createFolder()");
        return "redirect:/home";
    }

    @PostMapping("/admin/folders/delete")
    public String deleteFolder(@RequestParam(name = "folderId") Long folderId, Model model){

        log.info("deleteFolder() - Folder id: " + folderId);
        folderService.deleteFolderByFolderId(folderId);
        model.addAttribute("folders", folderService.findFolders());
        return "redirect:/home";
    }
}