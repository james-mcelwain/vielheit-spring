package com.vielheit.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "\"User\"", schema = "vielheit")
public class User extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull(message = "{firstName.notnull}")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "{lastName.notnull}")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "{emailAddress.notnull}")
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "{password.notnull}")
    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private Set<UserRole> roles;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
