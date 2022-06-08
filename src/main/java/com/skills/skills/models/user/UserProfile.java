package com.skills.skills.models.user;

import com.skills.skills.models.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserProfile extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(min= 2, max=20, message = "Please enter 2 to 20 characters")
    private String firstName;

    @NotBlank(message = "Name is required")
    @Size(min= 2, max=20, message = "Please enter 2 to 20 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    private String email;

    @Column(length = 1000)
    private String phoneNumber;

    @Column(length = 5)
    @NotNull(message = "ZipCode is required")
    private String zipCode;

    public UserProfile(String firstName, String lastName, String email, String phoneNumber, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;

    }
    public UserProfile(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneName) {
        this.phoneNumber = phoneName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

