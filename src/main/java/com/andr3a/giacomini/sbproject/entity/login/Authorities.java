package com.andr3a.giacomini.sbproject.entity.login;

import lombok.Data;
import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@ToString
@Table(name = "authorities")
//public class Authorities implements GrantedAuthority {
public class Authorities{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    private String name;
    private String grants;

    private Date created;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "lastupdate")
    private Date lastUpdate;
    @Column(name = "lastupdateby")
    private String lastUpdateBy;

    @OneToOne(mappedBy = "authorities")
    @ToString.Exclude
    private SbGroup sbGroup;

//    @Override
//    public String toString() {
//        return "Authorities{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", grants='" + grants + '\'' +
//                ", sbGroup=" + sbGroup +
//                '}';
//    }
}