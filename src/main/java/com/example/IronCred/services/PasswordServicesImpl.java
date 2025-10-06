package com.example.IronCred.services;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.requests.DeletePasswordRequest;
import com.example.IronCred.dtos.requests.GetPasswordsRequest;
import com.example.IronCred.dtos.requests.UpdatePasswordRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.DeletePasswordResponse;
import com.example.IronCred.dtos.responses.GetPasswordsResponse;
import com.example.IronCred.dtos.responses.UpdatePasswordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PasswordServicesImpl implements PasswordServices {

    @Autowired
    private PasswordVault passwordVault;

    @Override
    public AddPasswordResponse addPassword(AddPasswordRequest request) {
        if(passwordVault.existsByUsername(request.getUsername()) && passwordVault.existsByWebsite(request.getWebsite())){
            Password existingPassword = passwordVault.findAllByUsername(request.getUsername()).get().getFirst();
            existingPassword.setPassword(request.getPassword());
            Password password = passwordVault.save(existingPassword);
            return new AddPasswordResponse();
        }


        return map(passwordVault.save(map(request)));
    }

    public static AddPasswordResponse map(Password password) {
        AddPasswordResponse response = new AddPasswordResponse();
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

    @Override
    public GetPasswordsResponse getPasswords(GetPasswordsRequest request) {
        return null;
    }

    @Override
    public DeletePasswordResponse deletePassword(DeletePasswordRequest request) {
        return null;
    }

    @Override
    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
        return null;
    }
}
