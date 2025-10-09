package com.example.IronCred.services;


import com.example.IronCred.data.repositories.PasswordVault;
import com.example.IronCred.data.repositories.Users;
import com.example.IronCred.dtos.requests.*;
import com.example.IronCred.dtos.responses.AddPasswordResponse;
import com.example.IronCred.dtos.responses.RegistrationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServicesImplTest {


    @Autowired
    private UserServicesImpl passwordServices;

    @Autowired
    private Users  users;

    @Autowired
    private AuthServicesImpl authServices;

    @Autowired
    private PasswordVault passwordVault;


    @BeforeEach
    void setUp() {
        users.deleteAll();
        passwordVault.deleteAll();
    }



    @Test
    void addPassword_passwordVaultCountIsOne() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);


        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        password.setUserId(response.getId() );
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertEquals(1L,passwordVault.count());
    }

    @Test
    void addTwoPassword_PasswordVaultCountIsTwo(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        password.setUserId(response.getId() );
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertEquals(1L,passwordVault.count());


        password.setWebsite("example1.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        savedPassword = passwordServices.addPassword(password);
        assertEquals(2L,passwordVault.count());
    }



    @Test
    void addPassword_ResponseIsGiven() {

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        password.setUserId(response.getId() );
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);
        assertNotNull(savedPassword);
        assertNotNull(savedPassword.getId());
        assertNotNull(savedPassword.getCreatedAt());
        assertNotNull(savedPassword.getUpdatedAt());
        assertEquals("example.com", savedPassword.getWebsite());
    }

    @Test
    void passwordStoredInTheDB_IsEncrypted(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        password.setUserId(response.getId() );
        passwordServices.addPassword(password);
        assertNotEquals("securepassword123", passwordVault.findAll().getFirst().getPassword());
    }

    @Test
    void passwordStoredInTheDB_IsDecrypted(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        password.setUserId(response.getId() );
        AddPasswordResponse response1 = passwordServices.addPassword(password);
        GetPasswordRequest request1 = new GetPasswordRequest();
        request1.setId(response1.getId());
        request1.setWebsite(response1.getWebsite());
        assertEquals("securepassword123", passwordServices.getPassword(request1).getPassword());
    }

    @Test
    void testThatPasswordCanBeUpdated(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setWebsite("example.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        password.setUserId(response.getId() );
        AddPasswordResponse response1 = passwordServices.addPassword(password);

        String oldPassword = passwordVault.findAll().getFirst().getPassword();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setId(response1.getId());
        updatePasswordRequest.setPassword("newpassword456");
        updatePasswordRequest.setUsername("tester");
        updatePasswordRequest.setWebsite("example.com");
        passwordServices.updatePassword(updatePasswordRequest);
        assertEquals(1, passwordVault.count());

        String newPassword = passwordVault.findAll().getFirst().getPassword();

        assertNotEquals(oldPassword, newPassword);

    }


    @Test
    void getAllOfUsersPasswords(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");


        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setUserId(response.getId() );
        password.setWebsite("facebook.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);


        password = new AddPasswordRequest();
        password.setUserId(response.getId() );
        password.setWebsite("twitter.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        savedPassword = passwordServices.addPassword(password);
        assertEquals(2L,passwordVault.count());

        GetUserPasswordsRequest request1 = new GetUserPasswordsRequest();
        request1.setUserId(response.getId());

        assertEquals(2L, passwordServices.getUserPassword(request1).size());
        assertEquals("facebook.com", passwordServices.getUserPassword(request1).get(0).getWebsite());
        assertEquals("twitter.com", passwordServices.getUserPassword(request1).get(1).getWebsite());

    }


    @Test
    void deleteAUserPassword(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");

        RegistrationResponse response = authServices.registeredUser(request);

        AddPasswordRequest password = new AddPasswordRequest();
        password.setUserId(response.getId() );
        password.setWebsite("facebook.com");
        password.setUsername("tester");
        password.setPassword("securepassword123");
        AddPasswordResponse savedPassword = passwordServices.addPassword(password);


        DeletePasswordRequest deletePasswordRequest = new DeletePasswordRequest();
        deletePasswordRequest.setPasswordId(savedPassword.getId());

        passwordServices.deletePassword(deletePasswordRequest);
        assertEquals(0, passwordVault.count());
    }




}