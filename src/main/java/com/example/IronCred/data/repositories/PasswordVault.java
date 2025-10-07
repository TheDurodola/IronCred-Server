package com.example.IronCred.data.repositories;

import com.example.IronCred.data.models.Password;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordVault extends MongoRepository<Password, String> {
    Optional<List<Password>> findAllByUsername(String username);
    Optional<Password> findById(String id);
    Boolean existsByUsername(String username);
    Boolean existsByWebsite(String website);
}
