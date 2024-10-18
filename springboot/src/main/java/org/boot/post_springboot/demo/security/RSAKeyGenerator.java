package org.boot.post_springboot.demo.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.*;

@Component
public class RSAKeyGenerator {
    private KeyPair keyPair;

    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        this.keyPair = keyGen.generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return this.keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.keyPair.getPrivate();
    }
}
