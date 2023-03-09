package com.andr3a.giacomini.sbproject.entity.login;

import com.andr3a.giacomini.sbproject.model.entity.SbUser;
import com.andr3a.giacomini.sbproject.repository.ISbUserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SbUserTest {

    @Autowired
    private ISbUserRepository sbUserRepository;

    @Autowired
    private Logger log;

    @Test
    void sbUserToString(){
//        Optional<SbUser> sbUserOptional = sbUserRepository.findSbUser2ByEmail("luca.abondio@gmail.com");
        SbUser sbUser = sbUserRepository.findSbUser2ByEmail("luca.abondio@gmail.com");

        if(sbUser != null){
            log.info(sbUser.toString());
        }
    }
}
