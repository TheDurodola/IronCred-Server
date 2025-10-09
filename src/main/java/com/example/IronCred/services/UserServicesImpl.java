package com.example.IronCred.services;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.data.models.User;
import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.data.repositories.Users;
import com.example.IronCred.dtos.requests.*;
import com.example.IronCred.dtos.responses.*;
import com.example.IronCred.exceptions.AESDecryptionException;
import com.example.IronCred.exceptions.AESEncryptionException;
import com.example.IronCred.exceptions.UserDoesntExistException;
import com.example.IronCred.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.example.IronCred.utils.Mapper.map;
import static com.example.IronCred.utils.Mapper.mapPasswordToGetPasswordResponse;


@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private PasswordVault passwordVault;

    @Autowired
    private Users users;



    @Autowired
    private  AESUtil aesUtil;

    @Override
    public AddPasswordResponse addPassword(AddPasswordRequest request) {

        User user = users.findById(request.getUserId()).orElseThrow(()-> new UserDoesntExistException("User doesn't exist"));

        encryptRequest(request);

//        if(passwordVault.existsByUsername(request.getUsername()) && passwordVault.existsByWebsite(request.getWebsite())){
//            Password existingPassword = passwordVault.findAllByUsername(request.getUsername()).get().getFirst();
//            existingPassword.setPassword(request.getPassword());
//            Password password = passwordVault.save(existingPassword);
//            return new AddPasswordResponse();
//        }

        Password password = passwordVault.save(map(request));
        if(user.getPasswords()==null){
            user.setPasswords(new ArrayList<>());
        }
        user.getPasswords().add(password);

        users.save(user);

        return map(password);
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
        passwordVault.deleteById(request.getPasswordId());
        return new DeletePasswordResponse("Password deleted successfully");
    }

    @Override
    public List<Password> getUserPassword(GetUserPasswords request) {
        List<Password> passwords = passwordVault.findAllByUserId(request.getUserId()).get();
        List<Password> response = new ArrayList<>();
        for (Password password : passwords) {
            try {
                password.setPassword(aesUtil.decrypt(password.getPassword()));
                password.setUsername(aesUtil.decrypt(password.getUsername()));
            }catch (Exception e){
                throw new AESDecryptionException("Error while decrypting: " + e.getMessage());
            }
            response.add(password);
        }
        return response;
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
