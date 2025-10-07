package com.example.IronCred.utils;


    import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

    public class KeyUtil {
        public static SecretKey getFixedKey() {
            String keyString = "MySuperSecretKey123"; // must be 16, 24, or 32 chars
            return new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "AES");
        }
    }

