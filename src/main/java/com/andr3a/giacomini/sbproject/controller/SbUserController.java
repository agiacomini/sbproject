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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@Controller
//@RequestMapping("/registration")
public class SbUserController {
    @Autowired
    private SbUserService sbUserService;
    @Autowired
    private SbGroupService sbGroupService;
    @Autowired
    private Logger log;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public String updateSbUserPassword(@ModelAttribute("userEmail") String userEmail,
                                     @ModelAttribute("getRequestURI") String requestedURI,
                                     @ModelAttribute("getQueryString") String queryString,
                                     @ModelAttribute("oldPassword") String oldPassword,
                                     @ModelAttribute("newPassword") String newPassword,
                                     @ModelAttribute("repeatNewPassword") String repeatNewPassword,
                                     Model model, RedirectAttributes ra){

        log.trace("START updateSbUserPassword() - sbUser: " + userEmail);
        StringBuilder sb = new StringBuilder("redirect:" + requestedURI);
        sb.append(Utils.isNotNullAndNotTrimmedEmpty(queryString) ? "?" + queryString : "");

        try{

            SbUser sbUser = sbUserService.findSbUserByEmail(userEmail);

            if( !(sbUser != null) ){
                log.error("User not found");
                model.addAttribute("passwordUpdate", "User not found");
                return sb.toString();
            }
            // Check Old Password
            if( !(Utils.checkUserPassword(oldPassword, sbUser.getUserPassword(), bCryptPasswordEncoder) )){
                log.error("Old Password not match");
                ra.addFlashAttribute("passwordUpdate", "errorOldPassword");
                return sb.toString();
            }

            // Match "New Password" with "Repeat New Password"
            if( !(newPassword.equals(repeatNewPassword)) ){
                log.error("New Password and Repeat New Password not match");
//                model.addAttribute("passwordUpdate", "New Password and Repeat New Password not match");
                ra.addFlashAttribute("passwordUpdate", "errorMismatch");
                return sb.toString();
            }

            sbUserService.updateSbUserPassword(sbUser, bCryptPasswordEncoder.encode(newPassword));
            log.info("sbUser: " + userEmail + " PASSWORD UPDATED SUCCESSFULLY");
            ra.addFlashAttribute("passwordUpdate", "success");

        } catch (Exception exception){
            log.error(exception.getMessage());
            log.error("sbUser: " + userEmail + " ERROR UPDATE");
            model.addAttribute("passwordUpdate", "error");
        }

        log.trace("END updateSbUserPassword() - sbUser: " + userEmail);
        return sb.toString();
    }
}