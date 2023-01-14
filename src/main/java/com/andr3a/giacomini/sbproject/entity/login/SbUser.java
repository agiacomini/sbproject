package com.andr3a.giacomini.sbproject.entity.login;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@ToString
@Table(name = "sbuser", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class SbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "userpassword")
    @NotBlank
    private String userPassword;
    @Column(name = "enabled")
    private Boolean enabled = true;
    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @Column(name = "created", updatable = false)
    @CreationTimestamp
    private Timestamp created;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "lastupdate")
    @CreationTimestamp
    private Timestamp lastUpdate;

    @Column(name = "lastupdateby")
    private String lastUpdateBy;

    @ManyToOne
    @JoinColumn(name = "sbgroup")
    @NotNull(message = "SbGroup field is mandatory")
    @Valid
    private SbGroup sbGroup;

    public SbUser(){}

    public SbUser(String firstName, String lastName, String email, String userPassword, SbGroup sbGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userPassword = userPassword;
        this.sbGroup = sbGroup;
    }

    public static class Builder{

        private String firstName;
        private String lastName;
        private String userPassword;
        private Boolean enabled = true;
        private String email;
        private SbGroup sbGroup;

        public Builder(String email, SbGroup sbGroup){
            this.email = email;
            this.sbGroup = sbGroup;
        }

        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder userPassword(String userPassword){
            this.userPassword = userPassword;
            return this;
        }

        public Builder enabled(Boolean enabled){
            this.enabled = enabled;
            return this;
        }

        public SbUser build(){
            return new SbUser(this);
        }
    }

    private SbUser(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userPassword = builder.userPassword;
        this.enabled = builder.enabled;
        this.email = builder.email;
        this.sbGroup = builder.sbGroup;
    }

//    @Override
//    public String toString() {
//        return "SbUser{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", userPassword='" + userPassword + '\'' +
//                ", enabled=" + enabled + '\'' +
//                ", email='" + email + '\'' +
//                ", sbGroup=" + sbGroup +
//                '}';
//    }
}