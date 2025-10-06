package com.example.IronCred.services;

import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.requests.DeletePasswordRequest;
import com.example.IronCred.dtos.requests.GetPasswordsRequest;
import com.example.IronCred.dtos.requests.UpdatePasswordRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.DeletePasswordResponse;
import com.example.IronCred.dtos.responses.GetPasswordsResponse;
import com.example.IronCred.dtos.responses.UpdatePasswordResponse;

public interface PasswordServices {
    AddPasswordResponse addPassword(AddPasswordRequest request);
    GetPasswordsResponse getPasswords(GetPasswordsRequest request);
    DeletePasswordResponse deletePassword(DeletePasswordRequest request);
    UpdatePasswordResponse updatePassword(UpdatePasswordRequest request);
}
