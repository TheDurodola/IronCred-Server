package com.example.IronCred.utils;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.data.models.User;
import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.requests.RegistrationRequest;
import com.example.IronCred.dtos.responses.*;

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
        password.setUserId(request.getUserId());
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

    public static GetUserPasswordsResponse mapPasswords(Password password){
        GetUserPasswordsResponse response = new GetUserPasswordsResponse();
        response.setId(password.getId());
        response.setWebsite(password.getWebsite());
        response.setUsername(password.getUsername());
        response.setPassword(password.getPassword());
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        response.setCreatedAt(password.getCreatedAt().format(formatter));
        return response;
    }

    public static GetAPasswordResponse mapPasswordToGetAPassword(Password password) {
        GetAPasswordResponse response = new GetAPasswordResponse();
        response.setId(password.getId());
        response.setUsername(password.getUsername());
        response.setPassword(password.getPassword());
        response.setWebsite(password.getWebsite());
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        response.setCreatedAt(password.getCreatedAt().format(formatter));
        return response;
    }

    public static GetWebsitesResponse mapPasswordToWebsite(Password password){
        GetWebsitesResponse response = new GetWebsitesResponse();
        String website = password.getWebsite();
        website = standardizedTheWebsite(website);

        response.setWebsites(website);
        response.setId(password.getId());
        return response;
    }

    private static String standardizedTheWebsite(String website) {
        website = website.replace("http://www.", "");
        website = website.replace("https://www.", "");
        website = website.replace(".com", "");
        int stringLength = website.length();
        website = website.substring(0, 1).toUpperCase() + website.substring(1).toLowerCase();

        return website;
    }


    public static RegistrationResponse map(User user) {
        RegistrationResponse response = new RegistrationResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        return response;
    }

    public static User map(RegistrationRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        return user;
    }
}
