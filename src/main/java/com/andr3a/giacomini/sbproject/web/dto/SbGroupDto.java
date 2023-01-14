package com.andr3a.giacomini.sbproject.web.dto;

import lombok.Data;

@Data
public class SbGroupDto {
    private Long id;
    private String groupName;

    public SbGroupDto(){}
    public SbGroupDto(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }
}