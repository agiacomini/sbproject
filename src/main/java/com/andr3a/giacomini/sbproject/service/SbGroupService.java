package com.andr3a.giacomini.sbproject.service;

import com.andr3a.giacomini.sbproject.entity.login.SbGroup;
import com.andr3a.giacomini.sbproject.repository.login.ISbGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SbGroupService {
    @Autowired
    private ISbGroupRepository sbGroupRepository;

    public Iterable<SbGroup> getAllGroups(){
        return sbGroupRepository.findAll();
    }

    public Optional<SbGroup> findSbGroupById(Long sbGroupId){
        return sbGroupRepository.findById(sbGroupId);
    }
}