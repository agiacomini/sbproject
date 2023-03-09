package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.model.entity.SbGroup;
import com.andr3a.giacomini.sbproject.model.entity.SbUser;
import com.andr3a.giacomini.sbproject.service.SbGroupService;
import com.andr3a.giacomini.sbproject.service.SbUserService;
import com.andr3a.giacomini.sbproject.utils.Email;
import com.andr3a.giacomini.sbproject.utils.Utils;
import com.andr3a.giacomini.sbproject.model.dto.SbUserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/administration")
public class AdministrationController {
    @Autowired
    private SbUserService sbUserService;

    @Autowired
    private SbGroupService sbGroupService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Logger log;

    @ModelAttribute("sbUserDto")
    public SbUserDto sbUserDto(){ return new SbUserDto.SbUserDtoBuilder().build(); }

    @GetMapping
    public String showSbUserList(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 Model model){

        log.trace("START showSbUserList()");
        try{
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);

            Page<SbUser> sbUserPaginated = sbUserService.getAllSbUserPaginated(PageRequest.of(currentPage -1, pageSize));

            if(sbUserPaginated.getContent().isEmpty())
                return "redirect:/administration?page=" + (currentPage -1) + "&size=" + pageSize;

            model.addAttribute("sbUserPaginated", sbUserPaginated );

            log.info("sbUserPaginated: " + sbUserPaginated.getContent().toString());
            log.info("sbUserPaginated: " + sbUserPaginated.getNumberOfElements() + " elements of " + sbUserPaginated.getTotalElements());
            int totalPage = sbUserPaginated.getTotalPages();
            log.info("sbUserPaginated: page " + currentPage + " of " + totalPage);

            if(totalPage > 0){
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers );
                model.addAttribute("pNum", currentPage );
            }
        } catch(Exception exception){
            log.error(exception.getMessage());
            log.error("showSbUserList() ERROR");
        }
        model.addAttribute("groups", sbGroupService.getAllGroups());
        model.addAttribute("selectedSize", size.orElse(5));

        log.trace("END showSbUserList()");
        return "administration";
    }

    @DeleteMapping
    public String deleteSbUser(@ModelAttribute(value = "sbUser") SbUser sbUser,
                               RedirectAttributes ra,
                               HttpServletRequest request) {
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
//        return "redirect:/administration";
        return "redirect:" + request.getHeader("Referer");
    }

    @PutMapping
    public String enableDisableSbUser(@ModelAttribute("email") String userEmail,
                                      @ModelAttribute("enabled") boolean enabled,
                                      RedirectAttributes ra,
                                      HttpServletRequest request) {
        log.trace("START enableDisableSbUser() - sbUser: " + userEmail);
        try{
            sbUserService.enableDisableSbUserByEmail(userEmail, !enabled);
            ra.addFlashAttribute("enableDisableSbUser", "success");
            log.info("sbUser: " + userEmail + " was Successfully " + ( enabled == true ? "DISABLED" : "ENABLED" ) );
        } catch (Exception exception){
            log.error(exception.getMessage());
            log.error("sbUser: " + userEmail + " ENABLE/DISABLE ERROR");
            ra.addFlashAttribute("enableDisableSbUser", "error");
        }
        log.trace("END enableDisableSbUser() - sbUser: " + userEmail);
//        return "redirect:" + Utils.returnRequestUriWithQueryString(request);
        return "redirect:" + request.getHeader("Referer");
    }

    @PutMapping("/editSbUser")
    public String updateSbUser(@ModelAttribute(value = "sbUserDto") SbUserDto sbUserDto,
                               RedirectAttributes ra,
                               HttpServletRequest request){
        try{
            SbUser sbUserToUpdate = sbUserService.findSbUserById(sbUserDto.getId());
            log.trace("START updateSbUser() - sbUser: " + sbUserToUpdate.getEmail());

            log.info("BEFORE sbUser :" + sbUserToUpdate.toString() );
            sbUserToUpdate.setFirstName(sbUserDto.getFirstName());
            sbUserToUpdate.setLastName(sbUserDto.getLastName());
            sbUserToUpdate.setEmail(sbUserDto.getEmail());
            sbUserToUpdate.setSbGroup( new SbGroup( sbUserDto.getSbGroupDto().getId() ));

            sbUserService.updateSbUserBySbUser(sbUserToUpdate);
            ra.addFlashAttribute("updateSbUser", "success");
            log.info("AFTER sbUser :" + sbUserToUpdate.toString() );

        } catch(Exception exception){
            log.error(exception.getMessage());
            log.error("sbUser: " + sbUserDto.getEmail() + " UPDATE ERROR");
            ra.addFlashAttribute("updateSbUser", "error");
        }
        log.trace("END updateSbUser() - sbUser: " + sbUserDto.getEmail());
//        return "redirect:/administration";
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/createSbUser")
    public String createSbUser(@ModelAttribute(name = "sbUserDto") SbUserDto sbUserDto,
                               RedirectAttributes ra,
                               HttpServletRequest request){

        log.trace("START createSbUser()");
        try {
            if(!Email.emailPatternMatches(sbUserDto.getEmail())){
                log.error("Email Pattern not match");
                log.error("email user: " + sbUserDto.getEmail() + " not match pattern");
                ra.addFlashAttribute("createSbUser", "errorEmailPattern");
                return "redirect:/administration?emailPatternNotMatch";
            }
//            SbUser sbUser = sbUserService.saveSbUser(convertToEntity(sbUserDto));
//            sbUser = (SbUser) new GenericModelMapper().convertToEntity(sbUserDto, SbUser.class);
//            sbUser = sbUserService.saveSbUser(sbUser);

            SbUser sbUser = (SbUser) new Utils().convertToEntity(new SbUser(), sbUserDto);
            sbUser = sbUserService.saveSbUser(sbUser);

            ra.addFlashAttribute("createSbUser", "success");
            log.info("saveSbUser - " + sbUser);

        } catch (DataIntegrityViolationException exception){
            log.error(exception.getMessage());
            log.error("email user: " + sbUserDto.getEmail() + " already exist");
            ra.addFlashAttribute("createSbUser", "errorDuplicateEmail");
            return "redirect:/administration?duplicateEmail";
        }
        catch (SQLIntegrityConstraintViolationException exception){
            log.error(exception.getMessage());
            log.error("email user: " + sbUserDto.getEmail() + " already exist");
            ra.addFlashAttribute("createSbUser", "errorDuplicateEmail");
            return "redirect:/administration?duplicateEmail";
        } catch (ConstraintViolationException exception){
            log.error(exception.getMessage());
            ra.addFlashAttribute("createSbUser", "errorDuplicateEmail");
            return "redirect:/administration?duplicateEmail";
        }
        log.trace("END createSbUser()");
        return "redirect:" + request.getHeader("Referer");
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