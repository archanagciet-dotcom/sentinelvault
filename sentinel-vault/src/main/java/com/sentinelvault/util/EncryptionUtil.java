package com.sentinelvault.util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

    // 16-character AES key
    private static final String SECRET_KEY = "SentinelVault1";

    private final Key key = new SecretKeySpec(
            SECRET_KEY.getBytes(),
            "AES"
    );

    // Encrypt data
    public byte[] encrypt(byte[] data) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    // Decrypt data
    public byte[] decrypt(byte[] encryptedData) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(encryptedData);
    }

    // Encode to Base64 (optional)
    public String encode(byte[] data) {

        return Base64.getEncoder().encodeToString(data);
    }

    // Decode Base64 (optional)
    public byte[] decode(String text) {

        return Base64.getDecoder().decode(text);
    }
}