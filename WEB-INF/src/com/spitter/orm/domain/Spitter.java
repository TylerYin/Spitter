package com.spitter.orm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

/**
 * @author Tyler Yin
 */
@Entity
public class Spitter implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotNull
    @Size(min = 5, max = 16, message = "{username.size}")
    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 5, max = 255, message = "{password.size}")
    private String password;

    @Column(name = "firstname")
    @NotNull
    @Size(min = 2, max = 30, message = "{firstname.size}")
    private String firstname;

    @Column(name = "lastName")
    @NotNull
    @Size(min = 2, max = 30, message = "{lastname.size}")
    private String lastName;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "role")
    private String role;

    public Spitter() {
    }

    public Spitter(String username, String password, String firstname, String lastName, String email) {
        this(null, username, password, firstname, lastName, email, null);
    }

    public Spitter(Long id, String username, String password, String firstname, String lastName, String email,
                   String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "firstname", "lastName", "username", "password", "email");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "firstname", "lastName", "username", "password", "email");
    }

    @Override
    public String toString() {
        return "[firstname] = " + firstname + ", [lastName] = " + lastName + ", [email] = " + email;
    }
}
