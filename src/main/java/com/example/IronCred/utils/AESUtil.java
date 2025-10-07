package com.example.IronCred.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AESUtil {

    private String ALGORITHM;
    private String KEY;

    public AESUtil(@Value("${aes.key}") String key, @Value("${aes.algorithm}") String algorithm) {
        ALGORITHM = algorithm;
        KEY = key;
    }

    private  SecretKey getSecretKey() {
        return new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    public  String encrypt(String input) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
        return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
    }

    public  String decrypt(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)), StandardCharsets.UTF_8);
    }
}

