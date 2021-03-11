package com.ho.blogt.utils;

import java.util.Base64;

/**
 * 异或(xor)算法加密/解密工具
 *
 * @author xietansheng
 */
public class XORUtils {

    /**
     * 异或算法加密
     *
     * @param data 数据（密文/明文）
     * @param key  密钥
     * @return 返回加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        Base64.Encoder encoder = Base64.getEncoder();
        data = encoder.encode(data);
        key = encoder.encode(key);
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return data;
        }
        byte[] result = new byte[data.length];
        // 使用密钥字节数组循环加密或解密
        for (int i = 0; i < data.length; i++) {
            // 数据与密钥异或, 再与循环变量的低8位异或（增加复杂度）
            result[i] = (byte) (data[i] ^ key[i % key.length] ^ (i & 0xFF));
        }
        return encoder.encode(result);
    }

    /**
     * 异或算法解密
     *
     * @param data 数据（密文/明文）
     * @param key  密钥
     * @return 返回解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        Base64.Decoder decoder = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        data = decoder.decode(data);
        key = encoder.encode(key);
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return data;
        }
        byte[] result = new byte[data.length];
        // 使用密钥字节数组循环加密或解密
        for (int i = 0; i < data.length; i++) {
            // 数据与密钥异或, 再与循环变量的低8位异或（增加复杂度）
            result[i] = (byte) (data[i] ^ key[i % key.length] ^ (i & 0xFF));
        }
        return decoder.decode(result);
    }

}
