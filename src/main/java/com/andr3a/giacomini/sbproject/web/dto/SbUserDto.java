package com.andr3a.giacomini.sbproject.web.dto;

import lombok.Data;

@Data
public class SbUserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
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

    public SbUserDto(String firstName, String lastName, String email, String password, SbGroupDto sbGroupDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.sbGroupDto = sbGroupDto;
    }
}
