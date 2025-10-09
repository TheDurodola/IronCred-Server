package com.example.IronCred.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UpdatePasswordRequest {
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String username;
    @URL
    @NotBlank
    private String website;
}
