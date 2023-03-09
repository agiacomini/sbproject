package com.andr3a.giacomini.sbproject.model.entity;

import lombok.Data;

@Data
public class ChangePasswordForm {

    private String userName;
    private String password;
    private String repeatPassword;
    private String newPassword;

    public ChangePasswordForm(){ }

}
