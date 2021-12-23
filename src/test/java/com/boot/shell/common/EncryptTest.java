package com.boot.shell.common;

import com.boot.shell.common.encrypt.AES256Cipher;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
public class EncryptTest {

    @Test
    public void encryptTest() throws NoSuchPaddingException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {

        assertNotNull(AES256Cipher.generateKey());
        assertNotNull(AES256Cipher.generateIV());

        String str = "가나다라마바사아자차카타파하1234567890";
        AES256Cipher.setSymmetricKey("Ks+CjPIx2G9PWI+eMpGXHH5b9cb22bg=");
        AES256Cipher.setIV("dk4abC+iUZt7Cw==");
        String encrypted = AES256Cipher.encrypt(str);

        System.err.println("encrypt result >> " + encrypted);

        assertEquals(str, AES256Cipher.decrypt(encrypted));
    }
}
