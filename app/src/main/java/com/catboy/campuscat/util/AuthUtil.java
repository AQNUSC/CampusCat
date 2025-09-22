package com.catboy.campuscat.util;

import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * 安庆师范大学统一登录工具类
 * <p>
 *     用于对学生密码进行加密
 * </p>
 */
public class AuthUtil {

    // RSA加密参数
    public static final BigInteger RSA_PUBLIC_EXPONENT = new BigInteger("010001", 16);
    public static final BigInteger RSA_MODULUS = new BigInteger("00b5eeb166e069920e80bebd1fea4829d3d1f3216f2aabe79b6c47a3c18dcee5fd22c2e7ac519cab59198ece036dcf289ea8201e2a0b9ded307f8fb704136eaeb670286f5ad44e691005ba9ea5af04ada5367cd724b5a26fdb5120cc95b6431604bd219c6b7d83a6f8f24b43918ea988a76f93c333aa5a20991493d4eb1117e7b1", 16);

    /**
     * 安庆师范大学统一身份认证密码加密算法
     * <p>
     *     该方法实现了安庆师范大学统一身份认证系统所使用的密码加密算法。<p>
     *     加密步骤：<p>
     *     1. 将密码字符串转换为字节数组<p>
     *     2. 反转字节数组的顺序<p>
     *     3. 将反转后的字节数组转换为大整数<p>
     *     4. 使用RSA公钥对大整数进行加密<p>
     *     5. 将加密后的大整数转换为十六进制字符串<p>
     *     6. 如果十六进制字符串的长度为奇数，则在前面补零<p>
     *     7. 返回最终的十六进制字符串作为加密后的密码<p>
     * </p>
     * @param plaintext 明文密码
     * @return {@link String} 加密后的密码（十六进制字符串）
     */
    public static String encryptPassword(String plaintext) {
        try {
            // 反转字节数组
            byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < plaintextBytes.length / 2; i++) {
                byte temp = plaintextBytes[i];
                plaintextBytes[i] = plaintextBytes[plaintextBytes.length - 1 - i];
                plaintextBytes[plaintextBytes.length - 1 - i] = temp;
            }

            // RSA加密
            BigInteger plaintextBigInteger = new BigInteger(1, plaintextBytes);
            BigInteger encrypted = plaintextBigInteger.modPow(RSA_PUBLIC_EXPONENT, RSA_MODULUS);

            // 转换为十六进制字符串
            String result = encrypted.toString(16);
            if (result.length() % 2 == 1) {
                result = "0" + result;
            }

            return result;
        } catch (Exception e) {
            Log.e("AuthUtil", "Base64 to Hex failed", e);
            return plaintext;
        }
    }
}
