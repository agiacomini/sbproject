package com.andr3a.giacomini.sbproject.model.dto;

import lombok.Data;

@Data
public class FolderDto {
    private Long id;
    private String nameFolder;

    public FolderDto(){}

    public FolderDto(Long id) {
        this.id = id;
    }

    public FolderDto(Long id, String nameFolder) {
        this.id = id;
        this.nameFolder = nameFolder;
    }
}
