package com.ho.blogt.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESUtil {
    private static String gcm256algorithm = "AES/GCM/PKCS5Padding";

    public static String AEGCMDecrypt(String content, String keyStr) {
        try {
            if (StringUtils.isEmpty(content) || StringUtils.isEmpty(keyStr)) {
                throw new Exception("AESGCM256解密异常,检查文本或密钥");
            }
            Cipher cipher = Cipher.getInstance(gcm256algorithm);
            SecretKey key = new SecretKeySpec(Base64.decodeBase64(keyStr), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] message = Base64.decodeBase64(content);
            if (message.length < 12 + 16) {
                throw new IllegalArgumentException();
            }
            GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, key, params);
            byte[] decryptData = cipher.doFinal(message, 12, message.length - 12);
            String decript = new String(decryptData);
            return decript;
        } catch (Exception e) {
            System.out.println("AESGCM256解密文本处理失败,error:{" + e.toString() + "}");
        }
        return null;
    }

    public static String AEGCMEncrypt(String content, String keyStr) {
        try {
            if (StringUtils.isEmpty(content) || StringUtils.isEmpty(keyStr)) {
                throw new Exception("AESGCM256加密异常,检查文本或密钥");
            }

            SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(keyStr), "AES");
            Cipher cipher = Cipher.getInstance(gcm256algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            assert iv.length == 12;
            byte[] encryptData = cipher.doFinal(content.getBytes());
            assert encryptData.length == content.getBytes().length + 16;
            byte[] message = new byte[12 + content.getBytes().length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            return Base64.encodeBase64String(message);
        } catch (Exception e) {
            System.out.println("AESGCM256加密文本处理失败,error:{" + e.toString() + "}");
        }
        return null;
    }

    public static String KeyGenerator() {
        KeyGenerator generator = null;
        String gcmSecretKey = null;
        try {
            generator = KeyGenerator.getInstance("AES");
            //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
            generator.init(256);
            SecretKey secretKey = generator.generateKey();
            gcmSecretKey = Base64.encodeBase64String(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("密钥生成异常,error:{" + e.toString() + "}");
            return null;
        }
        return gcmSecretKey;
    }
}
