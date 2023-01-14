package com.andr3a.giacomini.sbproject.entity.login;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Data
@Entity
@ToString
@Table(name = "sbgroup")
public class SbGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
//    @NotBlank
    @Column(name = "groupname")
    private String groupName;

    @Column(name = "groupdescription")
    private String groupDescription;

    private Date created;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "lastupdate")
    private Date lastUpdate;
    @Column(name = "lastupdateby")
    private String lastUpdateBy;

    public SbGroup(){}

    public SbGroup(Long id) {
        this.id = id;
        this.groupName = "";
    }

    public SbGroup(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    @OneToMany(mappedBy = "sbGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<SbUser> sbUsers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorities", referencedColumnName = "id")
    private Authorities authorities;

//    @Override
//    public String toString() {
//        return "SbGroup{" +
//                "id=" + id +
//                ", groupName='" + groupName + '\'' +
//                ", groupDescription='" + groupDescription + '\'' +
//                ", sbUsers=" + sbUsers +
//                ", authorities=" + authorities +
//                '}';
//    }
}