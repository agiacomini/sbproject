package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.service.SbUserService;
import com.andr3a.giacomini.sbproject.web.dto.SbUserDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administration")
public class AdministrationController {
    @Autowired
    private SbUserService sbUserService;

    @Autowired
    private Logger log;

    @GetMapping
    public String administration(Model model){
        model.addAttribute("sbUsers", sbUserService.getAllUsers() );
        return "administration";
    }
}