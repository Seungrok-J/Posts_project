package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.security.RSAKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.Base64;

@Service
public class AuthService {
    @Autowired
    private RSAKeyGenerator rsaKeyGenerator;

    public String decryptPassword(String encryptedPassword)  throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaKeyGenerator.getPrivateKey());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}
