package com.vielheit.core.utility;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyReader {

    private KeyReader() {
    }

    public static PrivateKey readPrivateKey() throws Exception {
        File privateKeyFile = new ClassPathResource("keys/vielheit.der").getFile();
        byte[] keyBytes = Files.readAllBytes(privateKeyFile.toPath());

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static PublicKey readPublicKey() throws Exception {
        File publicKeyFile = new ClassPathResource("keys/vielheit_pub.der").getFile();
        byte[] keyBytes = Files.readAllBytes(publicKeyFile.toPath());

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}
