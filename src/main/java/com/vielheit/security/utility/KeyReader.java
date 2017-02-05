package com.vielheit.security.utility;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyReader {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;

    private static Logger log = Logger.getLogger(KeyReader.class);

    private KeyReader() {
    }

    public static PrivateKey readPrivateKey()  {
        if (KeyReader.privateKey == null) {
            try {
                File privateKeyFile = new ClassPathResource("keys/vielheit.der").getFile();
                byte[] keyBytes = Files.readAllBytes(privateKeyFile.toPath());

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);

            } catch (Exception ex) {
                log.fatal("Could not read private key into memory", ex);
                System.exit(1);
            }
        }

        return privateKey;
    }

    public static PublicKey readPublicKey() {
        if (KeyReader.publicKey == null) {
            try {
            File publicKeyFile = new ClassPathResource("keys/vielheit_pub.der").getFile();
            byte[] keyBytes = Files.readAllBytes(publicKeyFile.toPath());

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);
            } catch (Exception ex) {
                log.fatal("Could not read public key into memory", ex);
                System.exit(1);
            }

        }

        return publicKey;
    }
}
