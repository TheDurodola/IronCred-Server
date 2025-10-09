package com.example.IronCred.services;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.dtos.requests.*;
import com.example.IronCred.dtos.responses.*;

import java.util.List;

public interface UserServices {
    AddPasswordResponse addPassword(AddPasswordRequest request);
    GetPasswordResponse getPassword(GetPasswordRequest request);
    DeletePasswordResponse deletePassword(DeletePasswordRequest request);
    List<Password> getUserPassword (GetUserPasswords request);
    UpdatePasswordResponse updatePassword(UpdatePasswordRequest request);


}
