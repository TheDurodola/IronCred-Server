package com.example.IronCred.data.models;





import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;


    @Indexed(unique = true)
    private String username;


    @Indexed(unique = true)
    private String email;

    private String password;

    private String lastname;

    private String firstname;

    private String phone;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}


