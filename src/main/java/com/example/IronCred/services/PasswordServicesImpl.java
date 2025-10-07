package com.example.IronCred.services;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.requests.DeletePasswordRequest;
import com.example.IronCred.dtos.requests.GetPasswordRequest;
import com.example.IronCred.dtos.requests.UpdatePasswordRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.DeletePasswordResponse;
import com.example.IronCred.dtos.responses.GetPasswordResponse;
import com.example.IronCred.dtos.responses.UpdatePasswordResponse;
import com.example.IronCred.exceptions.AESDecryptionException;
import com.example.IronCred.exceptions.AESEncryptionException;
import com.example.IronCred.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.example.IronCred.utils.Mapper.map;
import static com.example.IronCred.utils.Mapper.mapPasswordToGetPasswordResponse;


@Service
public class PasswordServicesImpl implements PasswordServices {

    @Autowired
    private PasswordVault passwordVault;

    @Autowired
    private  AESUtil aesUtil;

    @Override
    public AddPasswordResponse addPassword(AddPasswordRequest request) {

        encryptRequest(request);

        if(passwordVault.existsByUsername(request.getUsername()) && passwordVault.existsByWebsite(request.getWebsite())){
            Password existingPassword = passwordVault.findAllByUsername(request.getUsername()).get().getFirst();
            existingPassword.setPassword(request.getPassword());
            Password password = passwordVault.save(existingPassword);
            return new AddPasswordResponse();
        }


        return map(passwordVault.save(map(request)));
    }

    @Override
    public GetPasswordResponse getPassword(GetPasswordRequest request) {
        Password password = passwordVault.findById(request.getId()).get();
        try {
            password.setPassword(aesUtil.decrypt(password.getPassword()));
            password.setUsername(aesUtil.decrypt(password.getUsername()));
        }catch (Exception e){
            throw new AESDecryptionException("Error while decrypting: " + e.getMessage());
        }

        return mapPasswordToGetPasswordResponse(password);
    }

    @Override
    public DeletePasswordResponse deletePassword(DeletePasswordRequest request) {
        passwordVault.deleteById(request.getId());
        return new DeletePasswordResponse("Password deleted successfully");
    }

    @Override
    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
        Password password = passwordVault.findById(request.getId()).get();

        encryptRequest(request);
        password.setPassword(request.getPassword());
        password.setUsername(request.getUsername());
        passwordVault.save(password);
        return new UpdatePasswordResponse("Password updated successfully");
    }



    private  void encryptRequest(AddPasswordRequest request) {
        try {
            request.setPassword(aesUtil.encrypt(request.getPassword()));
            request.setUsername(aesUtil.encrypt(request.getUsername()));
        } catch (Exception e) {
            throw new AESEncryptionException("Error while encrypting: " + e.getMessage());
        }
    }

    private  void encryptRequest(UpdatePasswordRequest request) {
        try {
            request.setPassword(aesUtil.encrypt(request.getPassword()));
            request.setUsername(aesUtil.encrypt(request.getUsername()));
        } catch (Exception e) {
            throw new AESEncryptionException("Error while encrypting: " + e.getMessage());
        }
    }
}
