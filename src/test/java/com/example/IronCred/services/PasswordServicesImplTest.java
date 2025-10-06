package com.example.IronCred.services;

import com.example.IronCred.data.models.Password;
import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PasswordServicesImplTest {

    @Autowired
    private PasswordServicesImpl passwordServices;

    @Autowired
    private PasswordVault passwordVault;


    @BeforeEach
    void setUp() {
        passwordVault.deleteAll();
    }

    @Test
    void addpassword_passwordVaultCountIsOne() {
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertEquals(1L,passwordVault.count());
    }

    @Test
    void addTwoPassword_PasswordVaultCountIsTwo(){
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertEquals(1L,passwordVault.count());


        password.setWebsite("example1.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        savedPassword = passwordServices.addPassword(password);
        assertEquals(2L,passwordVault.count());
    }

    @Test
    void PasswordWithSameWebsiteAndUserNameButDifferentPasswordIsAdded_PreviousPasswordIsUpdated() {
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertEquals(1L, passwordVault.count());

        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepass");
        savedPassword = passwordServices.addPassword(password);
        assertEquals(1L, passwordVault.count());
    }

    @Test
    void addPassword_ResponseIsGiven() {
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertNotNull(savedPassword);
        assertNotNull(savedPassword.getWebsite());
    }

}