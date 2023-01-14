package com.andr3a.giacomini.sbproject.entity.login;

import lombok.Data;

@Data
public class ChangePasswordForm {

    private String userName;
    private String password;
    private String repeatPassword;
    private String newPassword;

    public ChangePasswordForm(){ }

}
