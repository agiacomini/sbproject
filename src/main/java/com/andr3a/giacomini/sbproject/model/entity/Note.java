package com.andr3a.giacomini.sbproject.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "note")
public class Note extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String content;

    private Boolean done;

    @ManyToOne
    @JoinColumn
    @NotNull(message = "Folder field is mandatory")
    @Valid
    private Folder folder;

//    N.B: posso usare i getter/setter in alternativa a Lombok -> @Data
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
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
