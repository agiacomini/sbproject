package com.andr3a.giacomini.sbproject.web.dto;

import lombok.Data;

@Data
public class SbGroupDto {
    private Long id;
    private String groupName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public SbGroupDto(){}
    public SbGroupDto(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }
}