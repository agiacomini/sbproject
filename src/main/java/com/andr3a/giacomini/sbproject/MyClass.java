package com.andr3a.giacomini.sbproject;

import com.andr3a.giacomini.sbproject.service.SbUserService;
import org.springframework.boot.system.JavaVersion;
import org.springframework.boot.system.SystemProperties;
import org.springframework.core.SpringVersion;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("Spring Version: " + SpringVersion.getVersion() + "\n");
        System.out.println("Java Version 1: " + SystemProperties.get("java.version"));
        System.out.println("Java Version 2: " + System.getProperty("java.version"));
        System.out.println("Java Version 3: " + JavaVersion.getJavaVersion().toString());

        SbUserService sbUserService = new SbUserService();
//        System.out.println(sbUserService.findSbUserByEmail("qaz@qaz.com"));


    }
}
