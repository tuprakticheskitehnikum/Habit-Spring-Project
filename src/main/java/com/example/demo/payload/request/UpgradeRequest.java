package com.example.demo.payload.request;


import java.util.Set;

import com.example.demo.validation.constraint.ValidEmail;

import javax.validation.constraints.NotBlank;

public class UpgradeRequest {
    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @ValidEmail
    private String email;

    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
