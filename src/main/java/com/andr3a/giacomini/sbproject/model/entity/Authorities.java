package com.andr3a.giacomini.sbproject.model.entity;

import lombok.Data;
import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ToString
@Table(name = "authorities")
//public class Authorities implements GrantedAuthority {
public class Authorities extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grants")
    private String grants;

    @OneToOne(mappedBy = "authorities")
    @ToString.Exclude
    private SbGroup sbGroup;

    public String getGrants() { return grants; }
    public void setGrants(String grants) { this.grants = grants; }
}