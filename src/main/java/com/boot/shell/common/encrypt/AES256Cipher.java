package com.boot.shell.common.encrypt;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES256Cipher {

    private static volatile AES256Cipher INSTANCE;
    private static final String ALGORITHM = "AES";
    private static final String PADDING = "AES/CBC/PKCS5Padding";
    static String symmetricKey = EncryptConst.SYMMETRIC_KEY;
    static String IV = EncryptConst.IV;

    public static AES256Cipher getInstance() {
        if(INSTANCE == null){
            synchronized (AES256Cipher.class){
                if(INSTANCE == null) INSTANCE = new AES256Cipher();
            }
        }
        return INSTANCE;
    }

    private AES256Cipher() {
        IV = symmetricKey.substring(0, 16);
    }

    public static String encrypt(String str)
            throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] keyData = symmetricKey.getBytes();

        SecretKey secretKey = new SecretKeySpec(keyData, ALGORITHM);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));

        byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));

        return enStr;
    }

    public static String decrypt(String str)
            throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {

        byte[] keyData = symmetricKey.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyData, ALGORITHM);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));

        byte[] byteStr = Base64.decodeBase64(str.getBytes());

        return new String(cipher.doFinal(byteStr), "UTF-8");
    }

    public static String generateKey() {
        byte[] keyByte = new byte[32];
        SecureRandom RANDOM = new SecureRandom();
        RANDOM.nextBytes(keyByte);
        SecretKey secretKey = new SecretKeySpec(keyByte, ALGORITHM);
        return new String (java.util.Base64.getEncoder().encode(secretKey.getEncoded()), Charset.forName("UTF-8"));
    }

    public static String generateIV() {
        byte[] IVByte = new byte[16];
        SecureRandom RANDOM = new SecureRandom();
        RANDOM.nextBytes(IVByte);
        IvParameterSpec iv = new IvParameterSpec(IVByte);
        return java.util.Base64.getEncoder().encodeToString(iv.getIV());
    }

    public static void setIV(String IV) {
        AES256Cipher.IV = IV;
    }
    public static void setSymmetricKey(String symmetricKey) {
        AES256Cipher.symmetricKey = symmetricKey;
    }
}
