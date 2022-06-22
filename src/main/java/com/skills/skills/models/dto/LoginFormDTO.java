package com.skills.skills.models.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormDTO {

    @NotNull
    @NotBlank
    @Size(min = 3, max= 20, message ="Please enter your username.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min= 6, max= 50, message = "Please enter your password.")
    private String password;

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
}


