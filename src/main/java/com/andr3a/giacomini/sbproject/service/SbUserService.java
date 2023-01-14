package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.entity.login.Authorities;
import com.andr3a.giacomini.sbproject.entity.login.SbGroup;
import com.andr3a.giacomini.sbproject.entity.login.SbUser;
import com.andr3a.giacomini.sbproject.repository.login.ISbGroupRepository;
import com.andr3a.giacomini.sbproject.repository.login.ISbUserRepository;
import com.andr3a.giacomini.sbproject.web.dto.SbUserDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    public SbUser saveSbUser(SbUserDto sbUserDto) throws SQLIntegrityConstraintViolationException {

        SbUser sbUser = new SbUser.Builder(sbUserDto.getEmail(), new SbGroup(sbUserDto.getSbGroupDto().getId()))
                                  .firstName(sbUserDto.getFirstName())
                                  .lastName(sbUserDto.getLastName())
                                  .userPassword(bCryptPasswordEncoder.encode(sbUserDto.getPassword()))
                                  .build();

        return sbUserRepository.save(sbUser);
    }

//    public Optional<SbUser> findSbUserByEmail(String email){
//        return sbUserRepository.findSbUser2ByEmail(email);
//    }
    public SbUser findSbUserByEmail(String email){
        return sbUserRepository.findSbUser2ByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

//        Optional<SbUser> sbUser = sbUserRepository.findSbUser2ByEmail(userName);
        SbUser sbUser = sbUserRepository.findSbUser2ByEmail(userName);
        if(sbUser == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        List<Authorities> list = new ArrayList<Authorities>();
//        list.add(sbUser.get().getSbGroup().getAuthorities());
        list.add(sbUser.getSbGroup().getAuthorities());

//        User user = new User(sbUser.get().getEmail(), sbUser.get().getUserPassword(), mapRolesToAuthority(list));
        User user = new User(sbUser.getEmail(), sbUser.getUserPassword(), mapRolesToAuthority(list));

        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Authorities> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getGrants())).collect(Collectors.toList());
    }
}