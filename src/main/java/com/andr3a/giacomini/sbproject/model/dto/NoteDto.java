package com.andr3a.giacomini.sbproject.model.dto;

import lombok.Data;

@Data
public class NoteDto {
    private Long id;
    private String title;
    private String content;
    private Boolean done;
    private FolderDto folderDto;

    public static class NoteDtoBuilder {

        private Long id;
        private String title;
        private String content;
        private Boolean done;
        private FolderDto folderDto;

        public NoteDtoBuilder(){
            this.id = null;
            this.title = "";
            this.content = "";
            this.done = false;
            this.folderDto = new FolderDto();
        }

        public NoteDtoBuilder id(Long id){
            this.id = id;
            return this;
        }

        public NoteDtoBuilder title(String title){
            this.title = title;
            return this;
        }

        public NoteDtoBuilder content(String content){
            this.content = content;
            return this;
        }

        public NoteDtoBuilder done(Boolean done){
            this.done = done;
            return this;
        }

        public NoteDtoBuilder folderDto(FolderDto folderDto){
            this.folderDto = folderDto;
            return this;
        }

        public NoteDto build(){
            return new NoteDto(this);
        }
    }

    // Il costruttore è privato perché si vuole che sia solo il Builder a poter accedere al costruttore della classe;
    private NoteDto(NoteDtoBuilder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.done = builder.done;
        this.folderDto = builder.folderDto;
    }
}
