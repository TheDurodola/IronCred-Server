package com.example.IronCred.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddPasswordResponse {
    private String id;
    private String website;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
