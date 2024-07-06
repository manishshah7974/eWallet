package com.fintech.eWallet.utils.Crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private final String secretKey;
    private final String salt;   // for additional complexity

    public EncryptionUtil(@Value("${encryption.secret-key}") String secretKey,
                          @Value("${encryption.salt}") String salt) {
        this.secretKey = secretKey;
        this.salt = salt;
    }

    // Encrypt the given data using the provided secret key
    public String encrypt(String data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        String dataWithSalt = data + salt;
        byte[] encryptedBytes = cipher.doFinal(dataWithSalt.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the given data using the provided secret key
    public String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        // Remove the static salt from the decrypted data
        String decryptedDataWithSalt = new String(decryptedBytes);
        String data = decryptedDataWithSalt.substring(0, decryptedDataWithSalt.length() - salt.length());
        return data;
    }

    // Generate a new AES key (optional utility method)
    public static String generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256); // 256-bit AES
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
