package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.model.entity.Authorities;
import com.andr3a.giacomini.sbproject.model.entity.SbUser;
import com.andr3a.giacomini.sbproject.repository.ISbGroupRepository;
import com.andr3a.giacomini.sbproject.repository.ISbUserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SbUserService implements UserDetailsService {

    @Autowired
    private ISbUserRepository sbUserRepository;

    @Autowired
    private ISbGroupRepository sbGroupRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Logger log;

    public Iterable<SbUser> getAllUsers(){ return sbUserRepository.findAll(); }

    public Page<SbUser> getAllSbUserPaginated(Pageable pageable){

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<SbUser> listSbUser;

        long countSbUser = sbUserRepository.count();

        if( countSbUser < startItem){
            listSbUser = Collections.emptyList();
        } else {
            int toIndex = (int) Math.min(startItem + pageSize, countSbUser);
            listSbUser = sbUserRepository.findAll(Sort.by("id").ascending()).subList(startItem, toIndex);
        }

        Page<SbUser> sbUserPage = new PageImpl<SbUser>(listSbUser, PageRequest.of(currentPage, pageSize), countSbUser);

        return sbUserPage;
    }

    public void deleteSbUserById(long sbUserId){
        sbUserRepository.deleteById(sbUserId);
    }

    public SbUser saveSbUser(SbUser sbUser) throws SQLIntegrityConstraintViolationException {
        return sbUserRepository.save(sbUser);
    }

    public SbUser findSbUserByEmail(String email){
        return sbUserRepository.findSbUser2ByEmail(email);
    }

    public SbUser findSbUserById(Long id){
        return sbUserRepository.findById(id).get();
    }

    public void updateSbUserPassword(SbUser sbUser, String newPassword){
        sbUserRepository.updateSbUsersPasswordByEmail(
                sbUser.getEmail(),
                newPassword);
    }

    public void updateSbUserLastLoginDate(String email, LocalDateTime localDateTime){
        sbUserRepository.updateSbUsersLastLoginDate(email, localDateTime);
    }

    public void updateSbUserBySbUser(SbUser sbUserToUpdate){
        sbUserRepository.save(sbUserToUpdate);
    }

    public void enableDisableSbUserByEmail(String email, boolean enabled){
        sbUserRepository.updateSbUsersEnabled(email, enabled);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        SbUser sbUser = sbUserRepository.findSbUser2ByEmail(userName);
        if(sbUser == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        List<Authorities> list = new ArrayList<Authorities>();
        list.add(sbUser.getSbGroup().getAuthorities());

        User user = new User(sbUser.getEmail(), sbUser.getUserPassword(), mapRolesToAuthority(list));

        // Update Last Login Date
        updateSbUserLastLoginDate(sbUser.getEmail(), LocalDateTime.now());

        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Authorities> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getGrants())).collect(Collectors.toList());
    }
}