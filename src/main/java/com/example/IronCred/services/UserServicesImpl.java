package com.example.IronCred.services;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.data.models.User;
import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.data.repositories.Users;
import com.example.IronCred.dtos.requests.*;
import com.example.IronCred.dtos.responses.*;
import com.example.IronCred.exceptions.*;
import com.example.IronCred.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.example.IronCred.utils.Mapper.*;


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

        request.setWebsite(request.getWebsite().toLowerCase());

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

    public GetAPasswordResponse getPassword(GetAPasswordRequest request){
        Password password = passwordVault.findById(request.getPasswordId()).get();
        if(!password.getUserId().equals(request.getUserid())){
            throw new UserDoesntExistException("This entry doesn't belong to this user");
        }
        try {
            password.setPassword(aesUtil.decrypt(password.getPassword()));
            password.setUsername(aesUtil.decrypt(password.getUsername()));
        } catch (Exception e) {
            throw new AESDecryptionException(e.getMessage());
        }

        return mapPasswordToGetAPassword(password);
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
    public List<GetUserPasswordsResponse> getUserPassword(GetUserPasswordsRequest request) {
        List<Password> passwords = passwordVault.findAllByUserId(request.getUserId()).get();
        List<GetUserPasswordsResponse> response = new ArrayList<>();
        for (Password password : passwords) {
            try {
                password.setPassword(aesUtil.decrypt(password.getPassword()));
                password.setUsername(aesUtil.decrypt(password.getUsername()));
            }catch (Exception e){
                throw new AESDecryptionException("Error while decrypting: " + e.getMessage());
            }
            GetUserPasswordsResponse  processed =mapPasswords(password);
            response.add(processed);
        }

        if (response.isEmpty()) throw new NoEntryFoundException("No websites found for this user");
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

    public List<GetWebsitesResponse> getWebsites(GetWebsitesRequest request) {
        List<Password> passwords = passwordVault.findAllByUserId(request.getUserId()).get();
        List<GetWebsitesResponse> response = new ArrayList<>();
        for (Password password : passwords) {
            GetWebsitesResponse  processed = mapPasswordToWebsite(password);
            response.add(processed);
        }
        if (response.isEmpty()) throw new NoWebsitesFoundException("No websites found for this user");
        return response;
    }
}
