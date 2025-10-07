package com.example.IronCred.services;


import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.dtos.requests.AddPasswordRequest;
import com.example.IronCred.dtos.requests.GetPasswordRequest;
import com.example.IronCred.dtos.requests.UpdatePasswordRequest;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.GetPasswordResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    void addPassword_passwordVaultCountIsOne() {
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
        assertNotNull(savedPassword.getId());
        assertNotNull(savedPassword.getCreatedAt());
        assertNotNull(savedPassword.getUpdatedAt());
        assertEquals("example.com", savedPassword.getWebsite());
    }

    @Test
    void passwordStoredInTheDB_IsEncrypted(){
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        passwordServices.addPassword(password);
        assertNotEquals("securepassword123", passwordVault.findAll().getFirst().getPassword());
    }


    @Test
    void passwordStoredInTheDB_IsDecrypted(){
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse response = passwordServices.addPassword(password);
        GetPasswordRequest request = new GetPasswordRequest();
        request.setId(response.getId());
        request.setWebsite(response.getWebsite());
        assertEquals("securepassword123", passwordServices.getPassword(request).getPassword());
    }


    @Test
    void testThatPasswordCanBeUpdated(){
        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse response = passwordServices.addPassword(password);

        String oldPassword = passwordVault.findAll().getFirst().getPassword();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setId(response.getId());
        updatePasswordRequest.setPassword("newpassword456");
        updatePasswordRequest.setUsername("tester");
        updatePasswordRequest.setWebsite("example.com");
        passwordServices.updatePassword(updatePasswordRequest);
        assertEquals(1, passwordVault.count());

        String newPassword = passwordVault.findAll().getFirst().getPassword();

        assertNotEquals(oldPassword, newPassword);

    }




}