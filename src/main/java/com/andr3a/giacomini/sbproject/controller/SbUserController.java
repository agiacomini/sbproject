package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.entity.login.SbUser;
import com.andr3a.giacomini.sbproject.service.SbGroupService;
import com.andr3a.giacomini.sbproject.service.SbUserService;
import com.andr3a.giacomini.sbproject.utils.Utils;
import com.andr3a.giacomini.sbproject.web.dto.SbUserDto;
import com.andr3a.giacomini.sbproject.utils.Email;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import org.springframework.security.authentication.AbstractAuthenticationToken;

@Controller
//@RequestMapping("/registration")
public class SbUserController {
    @Autowired
    private SbUserService sbUserService;
    @Autowired
    private SbGroupService sbGroupService;

    @Autowired
    private Logger log;

    @ModelAttribute("sbUserDto")
    public SbUserDto userRegistrationDto(){
        return new SbUserDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
//        model.addAttribute("user", new SbUserRegistrationDto());
        model.addAttribute("groups", sbGroupService.getAllGroups());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") SbUserDto sbUserDto){

        try {
            if(!Email.emailPatternMatches(sbUserDto.getEmail())){
                log.error("Email Pattern not match");
                return "redirect:/registration?emailPatternNotMatch";
            }
            sbUserService.saveSbUser(sbUserDto);

        } catch (DataIntegrityViolationException exception){
            log.error(exception.getMessage());
            return "redirect:/registration?duplicateEmail";
        }
        catch (SQLIntegrityConstraintViolationException exception){
            log.error(exception.getMessage());
            return "redirect:/registration?duplicateEmail";
        } catch (ConstraintViolationException exception){
            log.error(exception.getMessage());
            return "redirect:/registration?duplicateEmail";
        }
        return "redirect:/registration?success";
    }

    @PostMapping("/updatePassword")
    public String updateUserPassword(@ModelAttribute("userEmail") String userEmail,
                                     @ModelAttribute("getRequestURI") String requestedURI,
                                     @ModelAttribute("getQueryString") String queryString,
                                     @ModelAttribute("oldPassword") String oldPassword,
                                     @ModelAttribute("newPassword") String newPassword,
                                     @ModelAttribute("repeatNewPassword") String repeatNewPassword,
                                     Model model,
                                     RedirectAttributes redirAttrs){

        StringBuilder sb = new StringBuilder("redirect:" + requestedURI);
        sb.append(Utils.isNotNullAndNotTrimmedEmpty(queryString) ? "?" + queryString : "");

        try{

            SbUser sbUser = sbUserService.findSbUserByEmail(userEmail);

            if( !(sbUser != null) ){
                if( !(sbUser.getUserPassword().equals(oldPassword)) ){
                    log.error("Old Password not match");
//                    return "Old Password not match";
//                    return new ResponseEntity(HttpStatus.NOT_FOUND);

                    redirAttrs.addFlashAttribute("message", "This is message from flash");
                    redirAttrs.addFlashAttribute("message", "message");
                    redirAttrs.addAttribute("message2", "message2");
                    model.addAttribute("messageAdd", "messageAdd");

//                    return "redirect:" + requestedURI + "?" + queryString;
                    return sb.toString();
                }
            }

            redirAttrs.addAttribute("message2", "message from message2");
            model.addAttribute("messageAdd", "messageAdd");

//            return "redirect:" + requestedURI + "?" + queryString;
            return sb.toString();



        } catch (Exception exception){
//            return "exception";
//            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }
        // Check Old Password

        // Match "New Password" with "Repeat New Password"
//        return "redirect:/registration?success";
        redirAttrs.addFlashAttribute("message", "This is message from flash");

//        return "redirect:" + requestedURI + "?" + queryString;
        return sb.toString();
    }
}