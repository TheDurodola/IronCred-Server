package com.example.IronCred.utils;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.GetPasswordResponse;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;



public class Mapper {
    public static AddPasswordResponse map(Password password) {
        AddPasswordResponse response = new AddPasswordResponse();
        response.setId(password.getId());
        response.setCreatedAt(password.getCreatedAt());
        response.setUpdatedAt(password.getUpdatedAt());
        response.setWebsite(password.getWebsite());
        return response;
    }

    public static Password map(AddPasswordRequest request) {
        Password password = new Password();
        password.setUsername(request.getUsername());
        password.setPassword(request.getPassword());
        password.setWebsite(request.getWebsite());
        return password;
    }

    public static GetPasswordResponse mapPasswordToGetPasswordResponse(Password password) {
        GetPasswordResponse response = new GetPasswordResponse();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);


        response.setCreatedAt(password.getCreatedAt().format(formatter));
        response.setUpdatedAt(password.getUpdatedAt().format(formatter));

        response.setWebsite(password.getWebsite());
        response.setUsername(password.getUsername());
        response.setPassword(password.getPassword());
        return response;
    }
}
