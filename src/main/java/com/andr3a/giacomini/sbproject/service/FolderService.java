package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.entity.Folder;
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

        return folderRepository.findAll();
    }

    public Optional<Folder> findFolderById(Long folderId){

        return folderRepository.findById(folderId);
    }

    public Folder createFolder(Folder folder){

        return folderRepository.save(folder);
    }

    public ResponseEntity deleteFolderByFolderId(Long folderId){

//        log.info("deleteFolderByFolderId() - " + folderRepository.findById(folderId).orElseThrow().toString() );
        log.info("deleteFolderByFolderId() - " + folderRepository.findById(folderId).toString() );
        folderRepository.deleteById(folderId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity deleteFolder(Folder folder){

        log.info("deleteFolder() - " + folder.toString() );
        folderRepository.delete(folder);
        return ResponseEntity.ok().build();
    }
}
