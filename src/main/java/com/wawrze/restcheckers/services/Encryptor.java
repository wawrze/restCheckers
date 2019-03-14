package com.wawrze.restcheckers.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
public class Encryptor {

    private SecretKeySpec aesKey;
    private Cipher cipher;
    private IvParameterSpec iv;

    public Encryptor() {
        String ENCRYPTION_KEY = "WawrRestCheckers";
        String INIT_VECTOR = "RestCheckersAppW";
        try {
            iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            aesKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (Exception ignore) {
        }
    }

    public String encrypt(String text) {
        String encryptedText = "";
        try {
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            encryptedText = Base64.encodeBase64String(encrypted);
        } catch (Exception ignore) {
        }
        return encryptedText;
    }

    public String decrypt(String text) {
        String decryptedText = "";
        try {
            cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
            byte[] decrypted = cipher.doFinal(Base64.decodeBase64(text));
            decryptedText = new String(decrypted);
        } catch (Exception ignore) {
        }
        return decryptedText;
    }

}
