package com.example.IronCred.services;

import com.example.IronCred.data.models.User;
import com.example.IronCred.data.repositories.Users;
import com.example.IronCred.dtos.requests.DeleteUserRequest;
import com.example.IronCred.dtos.requests.LoginRequest;
import com.example.IronCred.dtos.requests.RegistrationRequest;
import com.example.IronCred.dtos.responses.LoginResponse;
import com.example.IronCred.dtos.responses.RegistrationResponse;
import com.example.IronCred.exceptions.InvalidEmailException;
import com.example.IronCred.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServicesImplTest {

    @Autowired
    private AuthServicesImpl authServices;

    @Autowired
    private Users users;


    @BeforeEach
    void setUp() {
        users.deleteAll();
    }


    @Test
    void testThatUserCanBeRegistered(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");

        RegistrationResponse response = authServices.registeredUser(request);
        assertNotNull(response.getId());
        assertEquals(1,users.count());
    }

    @Test
    void testThatWhileRegistrationEmailAndUsernameAreConvertedToLowercase(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("Tester");
        request.setPassword("password123");
        request.setEmail("bolajIduroDola@GmaIl.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");

        authServices.registeredUser(request);

        User user = users.findByUsername("tester").get();

        assertNotNull(user);
        user = users.findByEmail("bolajidurodola@gmail.com").get();
        assertNotNull(user);
    }




    @Test
    void testThatPasswordInTheDB_IsTheHashed(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");

        String unhashedPassword = request.getPassword();

        RegistrationResponse response = authServices.registeredUser(request);

        assertNotEquals(unhashedPassword,users.findById(response.getId()).get().getPassword());
    }

    @Test
    void RegisterTheSameAccountTwice_UserAlreadyExistsExceptionIsThrown(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");

        authServices.registeredUser(request);
        assertThrows(UserAlreadyExistException.class, () -> authServices.registeredUser(request) );
    }

    @Test
    void testThatUserCanBeFoundByUsername(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");
        authServices.registeredUser(request);


        LoginRequest request1 = new LoginRequest();
        request1.setUsername("tester");
        request1.setPassword("password123");

        LoginResponse response = authServices.login(request1);
        assertNotNull(response);

    }

    @Test
    void testThatUserCanBeDeleted(){
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("tester");
        request.setPassword("password123");
        request.setEmail("bolajidurodola@gmail.com");
        request.setFirstname("Bolaji");
        request.setLastname("Durodola");
        request.setPhone("08148260470");
        RegistrationResponse response = authServices.registeredUser(request);

        DeleteUserRequest request1 = new DeleteUserRequest();
        request1.setId(response.getId());
        request1.setUsername("tester");
        request1.setPassword("password123");
        request1.setEmail("bolajidurodola@gmail.com");
        authServices.deleteUser(request1);
        assertEquals(0,users.count());
    }


}