package com.example.IronCred.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationRequest {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid firstname")
    private String firstname;
    @Pattern(regexp = "^[a-zA-Z-]+$", message = "Invalid lastname")
    private String lastname;
    @Email
    private String email;
    private String password;

    private String username;

    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Invalid phone number")
    private String phone;
}
