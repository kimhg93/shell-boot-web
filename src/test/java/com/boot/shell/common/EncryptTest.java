package com.boot.shell.common;

import com.boot.shell.common.encrypt.AES256Cipher;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EncryptTest {

    @Test
    public void encryptTest() throws NoSuchPaddingException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {

        assertNotNull(AES256Cipher.generateKey());
        assertNotNull(AES256Cipher.generateIV());

        String str = "work.kimhg@gmail.com";
        String encrypted = AES256Cipher.encrypt(str);
        System.err.println("encrypt result >> " + encrypted);

        assertEquals(str, AES256Cipher.decrypt(encrypted));
    }

    @Test
    public void jasypt() throws Exception {
        String url = "my_db_url";
        String username = "test";
        String password = "test2";

        System.out.println(jasyptEncoding(url));
        System.out.println(jasyptEncoding(username));
        System.out.println(jasyptEncoding(password));
    }

    public String jasyptEncoding(String value) throws Exception {

        String key = AES256Cipher.decrypt("ywAbMvay7KDePxAYY0GDkg==");
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

}
