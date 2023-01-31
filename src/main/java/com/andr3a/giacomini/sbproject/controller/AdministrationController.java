package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.entity.login.SbUser;
import com.andr3a.giacomini.sbproject.service.SbUserService;
import com.andr3a.giacomini.sbproject.web.dto.SbUserDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping
    public String deleteSbUser(@ModelAttribute(value = "sbUser") SbUser sbUser,
                               RedirectAttributes ra) {

        log.trace("START deleteSbUser() - sbUser: " + sbUser.getEmail());
        try {
            sbUserService.deleteSbUserById(sbUser.getId());
            log.info("sbUser: " + sbUser.getEmail() + " DELETED SUCCESSFULLY");
            ra.addFlashAttribute("sbUserDelete", "success");

        } catch (Exception exception) {
            log.error(exception.getMessage());
            log.error("sbUser: " + sbUser.getEmail() + " DELETED ERROR");
            ra.addFlashAttribute("sbUserDelete", "error");
        }

        log.trace("END deleteSbUser() - sbUser: " + sbUser.getEmail());
        return "redirect:/administration";
    }
}