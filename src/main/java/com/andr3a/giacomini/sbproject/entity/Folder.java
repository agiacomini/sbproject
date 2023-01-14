package com.andr3a.giacomini.sbproject.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "folder")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "namefolder")
    @NotNull
    @NotBlank(message = "Folder Name is required")
    private String nameFolder;

    private Date created;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "lastupdate")
    private Date lastUpdate;
    @Column(name = "lastupdateby")
    private String lastUpdateBy;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> notes;

    public Folder(){}

    public Folder(Long folderId, String nameFolder) {
        this.id = folderId;
        this.nameFolder = nameFolder;
    }

    public Folder(Long folderId) {
        this.id = folderId;
    }

    public Folder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNameFolder() {return nameFolder;}
    public void setNameFolder(String nameFolder) {this.nameFolder = nameFolder;}

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", nameFolder='" + nameFolder + '\'' +
                '}';
    }
}
