package com.example.IronCred.dtos.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UpdatePasswordRequest {
    private String id;
    private String password;

    private String username;
    @URL
    private String website;
}
