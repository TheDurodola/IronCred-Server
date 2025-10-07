package com.example.IronCred.dtos.requests;

import lombok.Data;

@Data
public class GetPasswordRequest {
    private String id;
    private String website;
}
