package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.model.entity.Folder;
import com.andr3a.giacomini.sbproject.repository.IFolderRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private Logger log;

    @Autowired
    private  IFolderRepository folderRepository;

    public List<Folder> findFolders(){

        log.info("findFolders() - ");
        return folderRepository.findAll();
    }

    public Optional<Folder> findFolderById(Long folderId){

        log.info("findFolderById() - " + folderId );
        return folderRepository.findById(folderId);
    }

    public Folder createFolderByEntity(Folder folder){

        log.info("createFolderByEntity() - " + folder.toString() );
        return folderRepository.save(folder);
    }

    public ResponseEntity deleteFolderByFolderId(Long folderId){

        log.info("deleteFolderByFolderId() - " + folderRepository.findById(folderId).toString() );
        folderRepository.deleteById(folderId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteFolderByEntity(Folder folder){

        log.info("deleteFolderByEntity() - " + folder.toString() );
        folderRepository.delete(folder);
        return ResponseEntity.ok().build();
    }
}
