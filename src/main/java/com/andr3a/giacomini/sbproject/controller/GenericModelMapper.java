package com.andr3a.giacomini.sbproject.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenericModelMapper<BaseEntity, BaseDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public GenericModelMapper(){}

//    private <BaseEntity, BaseDto> BaseDto convertToDto(BaseEntity baseEntity, Class<BaseDto> targetClass) {
//        BaseDto baseDto = modelMapper.map(baseEntity, targetClass);
//        return baseDto;
//    }
//
//    public <BaseEntity, BaseDto> BaseEntity convertToEntity(BaseDto dto, Class<BaseEntity> targetClass) {
//
//        BaseEntity baseEntity = modelMapper.map(dto, targetClass);
//        return baseEntity;
//    }

    public BaseDto convertToDto(Object obj, BaseDto mapper) {
        return (BaseDto) new ModelMapper().map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, BaseDto mapper) {
        return new ModelMapper().map(mapper, obj.getClass());
    }
}
