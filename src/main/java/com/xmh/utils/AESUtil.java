package com.xmh.utils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


/**
 * aes加密解密
 *
 * @author 谢明辉
 * @date 2019-7-15 9:47
 */

public enum AESUtil {
    /** 单例 */
    INSTANCE;

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String encrypt(String content) {

        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] bytes = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, genKey());
            byte[] bytes1 = cipher.doFinal(bytes);
            return parseByte2HexStr(bytes1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 需要解密的内容
     * @return java.lang.String
     * @date 2019-7-15
     */
    public String decrypt(String content) {

        try {
            byte[] decryptFrom = parseHexStr2Byte(content);
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, genKey());// 初始化
            byte[] result = cipher.doFinal(decryptFrom);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据秘钥获得SecretKeySpec
     *
     * @return javax.crypto.spec.SecretKeySpec
     * @date 2019-7-15
     */
    private SecretKeySpec genKey() {

        String strKey = key;
        byte[] enCodeFormat = {0};

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            enCodeFormat = secretKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(enCodeFormat, "AES");
    }

    /**
     * 二进制转16进制
     *
     * @param buf 二进制
     * @return java.lang.String
     * @date 2019-7-15
     */
    private String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制转2进制
     *
     * @param hexStr 16进制串
     * @return byte[]
     * @date 2019-7-15
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
            16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}
