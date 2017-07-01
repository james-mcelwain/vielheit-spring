package com.vielheit.security.utility;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class KeyReader {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;

    private static Logger log = Logger.getLogger(KeyReader.class);

    private KeyReader() {
    }

    public static PrivateKey readPrivateKey() {
        if (privateKey == null) {
            try(InputStream inputStream = new ClassPathResource("keys/vielheit.der").getInputStream()) {
                File privateKeyFile = File.createTempFile("privateKey", ".der");
                FileUtils.copyInputStreamToFile(inputStream, privateKeyFile);

                byte[] keyBytes = Files.readAllBytes(privateKeyFile.toPath());

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);

            } catch (Exception ex) {
                log.fatal("Could not read private key into memory", ex);
            }
        }
        return privateKey;
    }

    public static PublicKey readPublicKey() {
        if (publicKey == null) {
            try (InputStream inputStream = new ClassPathResource("keys/vielhet_pub.der").getInputStream()) {
                File publicKeyFile = File.createTempFile("publicKey", ".der");
                FileUtils.copyInputStreamToFile(inputStream, publicKeyFile);

                byte[] keyBytes = Files.readAllBytes(publicKeyFile.toPath());

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);

            } catch (Exception ex) {
                log.fatal("Could not read public key into memory", ex);
            }
        }
        return publicKey;
    }
}
