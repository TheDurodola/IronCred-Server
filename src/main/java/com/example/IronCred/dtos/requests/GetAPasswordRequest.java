package com.example.IronCred.dtos.requests;

import lombok.Data;

@Data
public class GetAPasswordRequest {
    private String userid;
    private String passwordId;
}
