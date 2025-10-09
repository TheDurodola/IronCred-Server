package com.example.IronCred.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AddPasswordRequest {

    private String userId;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    @URL
    @NotBlank
    private String website;
}
