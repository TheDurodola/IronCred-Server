package com.example.IronCred.data.models;


import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "passwords")
public class Password {

    @Id
    private String id;

    private String website;

    private String username;

    private String password;

    @DBRef
    private User user;
}
