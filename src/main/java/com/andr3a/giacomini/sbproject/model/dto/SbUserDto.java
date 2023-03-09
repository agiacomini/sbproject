package com.andr3a.giacomini.sbproject.model.dto;

import lombok.Data;

@Data
public class SbUserDto extends BaseDto{

    private Long id;
    private String firstName;
    private String lastName;
    private String userPassword;
    private String email;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public SbGroupDto getSbGroupDto() { return sbGroupDto; }
    public void setSbGroupDto(SbGroupDto sbGroupDto) { this.sbGroupDto = sbGroupDto; }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    private SbGroupDto sbGroupDto;

    public SbUserDto(){}

    public static class SbUserDtoBuilder{
        private Long id;
        private String firstName;
        private String lastName;
        private String userPassword;
        private String email;

        public SbUserDtoBuilder(){
            this.id = null;
            this.firstName = "";
            this.lastName = "";
            this.email = "";
            this.userPassword = "";
        }

        public SbUserDtoBuilder id(Long id){
            this.id = id;
            return this;
        }

        public SbUserDtoBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public SbUserDtoBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public SbUserDtoBuilder email(String email){
            this.email = email;
            return this;
        }

        public SbUserDtoBuilder password(String userPassword){
            this.userPassword = userPassword;
            return this;
        }

        public SbUserDto build(){ return new SbUserDto(this); }
    }

    private SbUserDto(SbUserDtoBuilder builder){
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.userPassword = builder.userPassword;
    }
}