package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
@Slf4j
public class PasswordService {

    private static final String KEY_HASH = "5W&1@7Thxn2dD&58";
    private static final SecretKey SECRET_KEY = new SecretKeySpec(KEY_HASH.getBytes(), "AES");

    public String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
            byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes());
            return new String(encryptedPasswordBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
