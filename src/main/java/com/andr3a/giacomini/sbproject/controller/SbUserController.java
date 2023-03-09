package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.model.entity.SbUser;
import com.andr3a.giacomini.sbproject.service.SbGroupService;
import com.andr3a.giacomini.sbproject.service.SbUserService;
import com.andr3a.giacomini.sbproject.utils.Utils;
import com.andr3a.giacomini.sbproject.model.dto.SbUserDto;
import com.andr3a.giacomini.sbproject.utils.Email;
import org.modelmapper.ModelMapper;
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
public class SbUserController {
    @Autowired
    private SbUserService sbUserService;
    @Autowired
    private SbGroupService sbGroupService;
    @Autowired
    private Logger log;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @ModelAttribute("sbUserDto")
    public SbUserDto userRegistrationDto(){ return new SbUserDto.SbUserDtoBuilder().build(); }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("groups", sbGroupService.getAllGroups());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") SbUserDto sbUserDto){

        log.trace("START registerUserAccount()");
        try {
            if(!Email.emailPatternMatches(sbUserDto.getEmail())){
                log.error("Email Pattern not match");
                return "redirect:/registration?emailPatternNotMatch";
            }
            SbUser sbUser = sbUserService.saveSbUser(convertToEntity(sbUserDto));
            log.info("saveSbUser - " + sbUser);

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
        log.trace("END registerUserAccount()");
        return "redirect:/registration?success";
    }

    @PutMapping("/updatePassword")
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

    private SbUserDto convertToDto(SbUser sbUser) {
        SbUserDto sbUserDto = modelMapper.map(sbUser, SbUserDto.class);
        return sbUserDto;
    }

    private SbUser convertToEntity(SbUserDto sbUserDto) {
        SbUser sbUser = modelMapper.map(sbUserDto, SbUser.class);
        sbUser.setUserPassword(bCryptPasswordEncoder.encode(sbUserDto.getUserPassword()));
        return sbUser;
    }
}