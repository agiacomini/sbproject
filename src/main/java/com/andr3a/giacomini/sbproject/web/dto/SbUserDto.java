package com.andr3a.giacomini.sbproject.web.dto;

import lombok.Data;

@Data
public class SbUserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
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
