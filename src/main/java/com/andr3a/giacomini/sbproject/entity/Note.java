package com.andr3a.giacomini.sbproject.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    private Boolean done;

    private Date created;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "lastupdate")
    private Date lastUpdate;
    @Column(name = "lastupdateby")
    private String lastUpdateBy;

    @ManyToOne
    @JoinColumn
    @NotNull(message = "Folder field is mandatory")
    @Valid
    private Folder folder;

//    N.B: posso usare i getter/setter in alternativa a Lombok -> @Data
//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//    public String getContent() {return content;}
//    public void setContent(String content) {this.content = content;}
//    public String getTitle() {return title;}
//    public void setTitle(String title) {this.title = title;}

    public Note(){}

    public Note(String title, String content, Folder folder) {
        this.title = title;
        this.content = content;
        this.folder = folder;
        this.done = false;
    }

    public Note(String title, String content, Long folderId) {
        this.title = title;
        this.content = content;
        this.folder.setId(folderId);
        this.done = false;
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(String title, String content, Boolean done) {
        this.title = title;
        this.content = content;
        this.done = done;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", done=" + done +
                ", folder=" + folder +
                '}';
    }
}
