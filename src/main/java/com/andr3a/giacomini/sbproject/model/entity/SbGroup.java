package com.andr3a.giacomini.sbproject.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@ToString
@Table(name = "sbgroup")
public class SbGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "groupname")
    private String groupName;

    @Column(name = "groupdescription")
    private String groupDescription;

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

    public Authorities getAuthorities() { return authorities; }
    public void setAuthorities(Authorities authorities) { this.authorities = authorities; }
}