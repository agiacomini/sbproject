package com.andr3a.giacomini.sbproject.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController<E> {

//    private final Class<E> entityClass;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    protected BaseController(ModelMapper modelMapper){
//        this.modelMapper = modelMapper;
//        entityClass = getEntityClass();
//    }
//
//    @SuppressWarnings("unchecked")
//    private Class<E> getEntityClass() {
//        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
//                .getActualTypeArguments()[0];
//    }
//
//    // Generic Method
//    private <T extends BaseDto> BaseDto convertToDto(List<? extends BaseEntity> entity) {
//        BaseDto baseDto = modelMapper.map(entity, BaseDto.class);
//        return baseDto;
//    }
//
//    private SbUser convertToEntity(SbUserDto dto) {
//        SbUser sbUser = modelMapper.map(dto, SbUser.class);
//        sbUser.setUserPassword(bCryptPasswordEncoder.encode(dto.getUserPassword()));
//        return sbUser;
//    }
}
