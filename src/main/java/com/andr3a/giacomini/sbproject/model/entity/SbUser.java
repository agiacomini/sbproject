package com.andr3a.giacomini.sbproject.model.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@Table(name = "sbuser", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class SbUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
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

    @Column(name = "lastlogindate")
    @CreationTimestamp
    private LocalDateTime lastLoginDate;

    @Column(name = "email")
    @NotBlank
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "sbgroup")
    @NotNull(message = "SbGroup field is mandatory")
    @Valid
    private SbGroup sbGroup;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public SbGroup getSbGroup() { return sbGroup; }
    public void setSbGroup(SbGroup sbGroup) { this.sbGroup = sbGroup; }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public SbUser(){}

    public SbUser(String firstName, String lastName, String email, String userPassword, SbGroup sbGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userPassword = userPassword;
        this.sbGroup = sbGroup;
    }

    public static class SbUserBuilder {

        private String firstName;
        private String lastName;
        private String userPassword;
        private Boolean enabled = true;
        private String email;
        private SbGroup sbGroup;

        public SbUserBuilder(String email, SbGroup sbGroup){
            this.email = email;
            this.sbGroup = sbGroup;
        }

        public SbUserBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public SbUserBuilder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public SbUserBuilder userPassword(String userPassword){
            this.userPassword = userPassword;
            return this;
        }

        public SbUserBuilder enabled(Boolean enabled){
            this.enabled = enabled;
            return this;
        }

        public SbUser build(){
            return new SbUser(this);
        }
    }

    private SbUser(SbUserBuilder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userPassword = builder.userPassword;
        this.enabled = builder.enabled;
        this.email = builder.email;
        this.sbGroup = builder.sbGroup;
    }
}